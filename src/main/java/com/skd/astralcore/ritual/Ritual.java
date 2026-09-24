package com.skd.astralcore.ritual;

/**
 * A ritual is a behaviour contract executed at an altar.
 * Concrete implementations are provided by consumer mods.
 * The {@link RitualType} registry holds all registered rituals.
 */
public interface Ritual {

    RitualType<?> getType();

    Multiblock getMultiblock();

    default double getEssenceCost() {
        return 0.0;
    }

    default int getDurationTicks() {
        return 0;
    }

    default RitualRisk getRisk() {
        return RitualRisk.none();
    }

    /**
     * Execute the ritual's effect. Consumes input items from pedestals, produces outputs.
     */
    RitualResult run(RitualContext context);
}
