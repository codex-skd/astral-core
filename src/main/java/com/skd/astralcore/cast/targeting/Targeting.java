package com.skd.astralcore.cast.targeting;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Targeting helpers for spell resolution.
 */
public final class Targeting {

    private Targeting() {}

    /**
     * Performs a raycast from the caster's eye position in the look direction.
     *
     * @param maxDistance maximum raycast distance
     * @return the hit result (block or entity), or {@code null} if nothing was hit
     */
    @Nullable
    public static HitResult raycast(LivingEntity caster, double maxDistance) {
        Vec3 eyePos = caster.getEyePosition();
        Vec3 lookVec = caster.getViewVector(1.0F);
        Vec3 endPos = eyePos.add(lookVec.scale(maxDistance));

        ClipContext clipContext = new ClipContext(
                eyePos, endPos,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                caster
        );

        BlockHitResult blockHit = caster.level().clip(clipContext);

        // Check for entity hits along the ray
        AABB aabb = caster.getBoundingBox().expandTowards(lookVec.scale(maxDistance)).inflate(1.0);
        EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
                caster, eyePos, endPos, aabb,
                entity -> !entity.isSpectator() && entity.isPickable() && entity != caster,
                maxDistance * maxDistance
        );

        if (entityHit != null) {
            // If block hit is farther than entity hit, use entity hit
            if (blockHit.getType() == HitResult.Type.MISS ||
                caster.distanceTo(entityHit.getEntity()) <= eyePos.distanceTo(blockHit.getLocation())) {
                return entityHit;
            }
        }

        return blockHit.getType() == HitResult.Type.MISS ? null : blockHit;
    }

    /**
     * Collects all living entities within a sphere around a point.
     */
    public static List<LivingEntity> collectInSphere(net.minecraft.world.level.Level level, Vec3 center, double radius, @Nullable LivingEntity exclude) {
        AABB aabb = new AABB(center.add(radius, radius, radius), center.subtract(radius, radius, radius));
        return level.getEntitiesOfClass(LivingEntity.class, aabb,
                e -> e != exclude && e.distanceToSqr(center) <= radius * radius);
    }

    /**
     * Returns the caster itself as the target (self-targeting).
     */
    public static LivingEntity selfTarget(LivingEntity caster) {
        return caster;
    }

    /**
     * Computes a spawn position and velocity for a basic projectile.
     *
     * @param speed initial speed of the projectile
     * @return spawn info: position and velocity vector
     */
    public static ProjectileSpawnInfo computeProjectileSpawn(LivingEntity shooter, double speed) {
        Vec3 eyePos = shooter.getEyePosition();
        Vec3 lookVec = shooter.getViewVector(1.0F);
        Vec3 spawnPos = eyePos.add(lookVec);
        Vec3 velocity = lookVec.scale(speed);
        return new ProjectileSpawnInfo(spawnPos, velocity);
    }

    public record ProjectileSpawnInfo(Vec3 position, Vec3 velocity) {}
}
