package com.skd.astralcore.essence;

/**
 * An active instance of an {@link EssenceModifier} for a specific player,
 * with remaining duration (in ticks). Use {@link Integer#MAX_VALUE} for permanent modifiers.
 */
public record EssenceModifierInstance(
        EssenceModifier modifier,
        int remainingTicks
) {
    public boolean isExpired() {
        return remainingTicks <= 0;
    }

    public EssenceModifierInstance tick() {
        return new EssenceModifierInstance(modifier, remainingTicks - 1);
    }
}
