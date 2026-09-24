package com.skd.astralcore.ritual;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Immutable context passed to {@link Ritual#run(RitualContext)}.
 *
 * @param altarPos  world position of the altar block
 * @param level     server level (never client)
 * @param player    the player who triggered the ritual (may be null for auto-triggered rituals)
 * @param inputs    items found on pedestals (empty until pedestal scanning is implemented)
 */
public record RitualContext(
        BlockPos altarPos,
        ServerLevel level,
        @Nullable ServerPlayer player,
        List<ItemStack> inputs
) {}
