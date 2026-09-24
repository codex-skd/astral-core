package com.skd.astralcore.ritual;

import net.minecraft.resources.ResourceLocation;

/**
 * A registered ritual type. Created via {@link net.neoforged.neoforge.registries.DeferredRegister}
 * into {@link com.skd.astralcore.registry.AstralRegistries#RITUAL_TYPES}.
 *
 * @param <T> the concrete {@link Ritual} implementation
 */
public record RitualType<T extends Ritual>(
        ResourceLocation id,
        RitualFactory<T> factory
) {
    @FunctionalInterface
    public interface RitualFactory<T extends Ritual> {
        T create();
    }

    public T create() {
        return factory.create();
    }
}
