package com.skd.astralcore.registry;

import com.skd.astralcore.AstralCore;
import com.skd.astralcore.cast.SpellType;
import com.skd.astralcore.essence.EssenceModifier;
import com.skd.astralcore.relic.RelicType;
import com.skd.astralcore.ritual.RitualType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

/**
 * Centralised registry holders for Astral Core custom types.
 */
public final class AstralRegistries {

    private AstralRegistries() {}

    public static final ResourceKey<Registry<SpellType<?>>> SPELL_TYPE_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(AstralCore.MOD_ID, "spell_type"));

    public static final ResourceKey<Registry<EssenceModifier>> ESSENCE_MODIFIER_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(AstralCore.MOD_ID, "essence_modifier"));

    public static final ResourceKey<Registry<RitualType<?>>> RITUAL_TYPE_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(AstralCore.MOD_ID, "ritual_type"));

    public static final ResourceKey<Registry<RelicType<?>>> RELIC_TYPE_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(AstralCore.MOD_ID, "relic_type"));

    public static Registry<SpellType<?>> SPELL_TYPES;
    public static Registry<EssenceModifier> ESSENCE_MODIFIERS;
    public static Registry<RitualType<?>> RITUAL_TYPES;
    public static Registry<RelicType<?>> RELIC_TYPES;

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(AstralRegistries::onNewRegistries);
    }

    private static void onNewRegistries(NewRegistryEvent event) {
        SPELL_TYPES = event.create(new RegistryBuilder<>(SPELL_TYPE_KEY));
        ESSENCE_MODIFIERS = event.create(new RegistryBuilder<>(ESSENCE_MODIFIER_KEY));
        RITUAL_TYPES = event.create(new RegistryBuilder<>(RITUAL_TYPE_KEY));
        RELIC_TYPES = event.create(new RegistryBuilder<>(RELIC_TYPE_KEY));
    }
}
