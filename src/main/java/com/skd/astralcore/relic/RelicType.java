package com.skd.astralcore.relic;

import net.minecraft.resources.ResourceLocation;

/**
 * A registered relic type. Created via {@link net.neoforged.neoforge.registries.DeferredRegister}
 * into {@link com.skd.astralcore.registry.AstralRegistries#RELIC_TYPES}.
 *
 * @param <T> the concrete {@link Relic} implementation
 */
public record RelicType<T extends Relic>(
        ResourceLocation id,
        RelicFactory<T> factory
) {
    @FunctionalInterface
    public interface RelicFactory<T extends Relic> {
        T create();
    }

    public T create() {
        return factory.create();
    }
}
