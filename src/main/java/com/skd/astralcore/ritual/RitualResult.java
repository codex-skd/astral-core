package com.skd.astralcore.ritual;

import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 * Sealed result type for a ritual execution attempt.
 */
public sealed interface RitualResult {

    record Success(List<ItemStack> outputs) implements RitualResult {}

    record Failure(String reason) implements RitualResult {}

    record InsufficientEssence() implements RitualResult {}

    // --- Factory methods ---

    static RitualResult success(List<ItemStack> outputs) {
        return new Success(outputs);
    }

    static RitualResult failure(String reason) {
        return new Failure(reason);
    }

    static RitualResult insufficientEssence() {
        return new InsufficientEssence();
    }

    // --- Queries ---

    default boolean isSuccess() {
        return this instanceof Success;
    }

    default boolean isFailure() {
        return this instanceof Failure;
    }
}
