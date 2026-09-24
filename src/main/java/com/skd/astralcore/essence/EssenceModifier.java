package com.skd.astralcore.essence;

import net.minecraft.resources.ResourceLocation;

/**
 * A registrable source of essence capacity or regen modification.
 * Concrete sources (altar proximity, biome, relic, research node) are provided by consumer mods.
 *
 * <p>Registered into {@link com.skd.astralcore.registry.AstralRegistries#ESSENCE_MODIFIERS}.</p>
 */
public record EssenceModifier(
        ResourceLocation id,
        double capacityDelta,
        double regenDeltaPerSecond
) {}
