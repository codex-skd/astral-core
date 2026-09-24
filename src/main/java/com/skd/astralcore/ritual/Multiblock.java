package com.skd.astralcore.ritual;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

/**
 * Describes a required arrangement of blocks relative to an altar's origin position.
 * Each entry maps a relative offset to a block tag that must match there.
 * <p>
 * Datapack-JSON-loadable override is deferred to a future milestone.
 */
public record Multiblock(Map<BlockPos, TagKey<Block>> requiredOffsets) {

    /**
     * Check whether the blocks around {@code origin} match all required offsets.
     */
    public boolean matches(Level level, BlockPos origin) {
        for (Map.Entry<BlockPos, TagKey<Block>> entry : requiredOffsets().entrySet()) {
            BlockPos worldPos = origin.offset(entry.getKey());
            BlockState state = level.getBlockState(worldPos);
            if (!state.is(entry.getValue())) {
                return false;
            }
        }
        return true;
    }
}
