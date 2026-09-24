package com.skd.astralcore.cast;

import net.minecraft.resources.ResourceLocation;

/**
 * A registered spell type. Created via {@link net.neoforged.neoforge.registries.DeferredRegister}
 * into {@link com.skd.astralcore.registry.AstralRegistries#SPELL_TYPES}.
 *
 * @param <T> the concrete {@link Spell} implementation
 */
public record SpellType<T extends Spell>(
        ResourceLocation id,
        SpellFactory<T> factory
) {
    @FunctionalInterface
    public interface SpellFactory<T extends Spell> {
        T create();
    }

    public T create() {
        return factory.create();
    }
}
