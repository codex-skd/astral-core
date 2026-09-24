package com.skd.astralcore.cast;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * Immutable context passed to {@link Spell#cast(CastContext)}.
 *
 * @param caster     the entity casting the spell (typically a {@link Player})
 * @param level      the level the caster is in
 * @param hand       which hand initiated the cast
 * @param target     resolved target (nullable — depends on targeting mode)
 * @param powerLevel a scalar for scaling spell effects (1.0 = normal)
 */
public record CastContext(
        LivingEntity caster,
        Level level,
        InteractionHand hand,
        @Nullable LivingEntity target,
        double powerLevel
) {
    /**
     * Convenience constructor with powerLevel = 1.0.
     */
    public CastContext(LivingEntity caster, Level level, InteractionHand hand, @Nullable LivingEntity target) {
        this(caster, level, hand, target, 1.0);
    }

    public boolean isClientSide() {
        return level.isClientSide();
    }

    public Player getPlayer() {
        return caster instanceof Player player ? player : null;
    }
}
