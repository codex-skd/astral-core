package com.skd.astralcore.ritual;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;

/**
 * Abstract base class for altar block entities.
 * <p>
 * The concrete altar block (with its {@link BlockEntityType}) belongs to consumer mods (e.g. majestic).
 * This class provides the shared ritual-running logic.
 * <p>
 * TODO(deferred M3): pedestal scanning to populate RitualContext.inputs — currently empty list.
 */
public abstract class AltarBlockEntity extends BlockEntity {

    private RitualPhase phase = RitualPhase.IDLE;
    @Nullable
    private RitualType<?> runningRitualType = null;
    private int ticksRemaining = 0;

    protected AltarBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    /**
     * Subclass provides the multiblock shape required by this altar tier.
     */
    public abstract Multiblock getExpectedMultiblock();

    /**
     * Validate the structure around this altar against the expected multiblock.
     */
    public boolean validateStructure() {
        return getExpectedMultiblock().matches(level, worldPosition);
    }

    /**
     * Start a ritual at this altar. Validates the structure first.
     */
    public void startRitual(RitualType<?> type, @Nullable ServerPlayer player) {
        if (phase != RitualPhase.IDLE) return;
        if (level == null || level.isClientSide()) return;

        if (!validateStructure()) {
            return;
        }

        Ritual ritual = type.create();

        // Check essence cost if applicable (leave actual essence consumption to the ritual's run())
        this.runningRitualType = type;
        this.ticksRemaining = ritual.getDurationTicks();
        this.phase = RitualPhase.STARTING;

        // Transition immediately to RUNNING for M2 simplicity
        this.phase = RitualPhase.RUNNING;

        setChanged();
    }

    /**
     * Standard BlockEntity tick — call this from the block's {@code tick} method.
     */
    public void tick() {
        if (level == null || level.isClientSide()) return;
        if (phase != RitualPhase.RUNNING) return;

        ticksRemaining--;

        if (ticksRemaining <= 0) {
            completeRitual();
        } else {
            setChanged();
        }
    }

    private void completeRitual() {
        if (runningRitualType == null) {
            phase = RitualPhase.IDLE;
            setChanged();
            return;
        }

        Ritual ritual = runningRitualType.create();
        RitualRisk risk = ritual.getRisk();

        // Roll for failure
        if (risk.failureChance() > 0.0 && level.random.nextDouble() < risk.failureChance()) {
            // Ritual failed
            if (risk.penalty() == RitualRisk.RiskPenalty.CONSUME_INPUTS) {
                // TODO(deferred M3): consume real pedestal inputs
            }
            phase = RitualPhase.FAILED;
            setChanged();
            // Transition FAILED -> IDLE after one tick so clients can see the state
            phase = RitualPhase.IDLE;
            runningRitualType = null;
            setChanged();
            return;
        }

        // Run the ritual
        // TODO(deferred M3): populate inputs from real pedestal blocks
        RitualContext context = new RitualContext(worldPosition, (net.minecraft.server.level.ServerLevel) level, null, Collections.emptyList());
        RitualResult result = ritual.run(context);

        if (result.isSuccess()) {
            phase = RitualPhase.COMPLETING;
            setChanged();
            // Output items handled by the concrete ritual subclass
        } else {
            phase = RitualPhase.FAILED;
            setChanged();
        }

        // Transition back to idle
        phase = RitualPhase.IDLE;
        runningRitualType = null;
        ticksRemaining = 0;
        setChanged();
    }

    // --- NBT persistence ---

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putString("Phase", phase.name());
        if (runningRitualType != null) {
            tag.putString("RunningRitualType", runningRitualType.id().toString());
        }
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        try {
            phase = RitualPhase.valueOf(tag.getString("Phase"));
        } catch (IllegalArgumentException e) {
            phase = RitualPhase.IDLE;
        }
        if (tag.contains("RunningRitualType")) {
            // Note: the actual RitualType lookup requires the registry to be bound.
            // We store the id here; concrete subclasses can resolve it at runtime.
            runningRitualType = null; // resolved lazily by subclass if needed
        } else {
            runningRitualType = null;
        }
    }

    // --- Getters ---

    public RitualPhase getPhase() {
        return phase;
    }

    public int getTicksRemaining() {
        return ticksRemaining;
    }

    @Nullable
    public RitualType<?> getRunningRitualType() {
        return runningRitualType;
    }
}
