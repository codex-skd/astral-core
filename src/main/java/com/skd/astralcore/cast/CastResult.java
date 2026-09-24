package com.skd.astralcore.cast;

import org.jetbrains.annotations.Nullable;

/**
 * Sealed result type for a spell cast attempt.
 */
public sealed interface CastResult {

    record Success() implements CastResult {}

    record Failure(String reason) implements CastResult {}

    record InsufficientEssence() implements CastResult {}

    record OnCooldown(int remainingTicks) implements CastResult {}

    // --- Factory methods ---

    static CastResult success() {
        return new Success();
    }

    static CastResult failure(String reason) {
        return new Failure(reason);
    }

    static CastResult insufficientEssence() {
        return new InsufficientEssence();
    }

    static CastResult onCooldown(int remainingTicks) {
        return new OnCooldown(remainingTicks);
    }

    // --- Queries ---

    default boolean isSuccess() {
        return this instanceof Success;
    }

    default boolean isFailure() {
        return this instanceof Failure;
    }

    @Nullable
    default String failureReason() {
        return this instanceof Failure f ? f.reason() : null;
    }
}
