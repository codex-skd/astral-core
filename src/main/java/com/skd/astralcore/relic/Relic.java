package com.skd.astralcore.relic;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.skd.astralcore.cast.CastContext;
import com.skd.astralcore.cast.CastResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Holder;

/**
 * A relic is a behaviour contract. Concrete implementations are provided by consumer mods.
 * The {@link RelicType} registry holds all registered relic types.
 */
public interface Relic {

    RelicType<?> getType();

    default void onEquip(ItemStack stack, LivingEntity wearer) {}

    default void onUnequip(ItemStack stack, LivingEntity wearer) {}

    /**
     * Cheap passive per-tick effect while equipped.
     * Astral Core ships no concrete relics — leave empty by default.
     */
    default void onTick(ItemStack stack, LivingEntity wearer) {}

    /**
     * Active ability triggered by the wearer. Reuses the same {@link CastResult}/{@link CastContext}
     * vocabulary as spell casting for unified result handling.
     */
    default CastResult activate(CastContext context) {
        return CastResult.failure("This relic has no active ability");
    }

    /**
     * Static passive attribute modifiers applied while this relic is equipped.
     */
    default Multimap<Holder<Attribute>, AttributeModifier> getPassiveAttributeModifiers() {
        return HashMultimap.create();
    }
}
