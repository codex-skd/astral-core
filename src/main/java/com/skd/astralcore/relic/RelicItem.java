package com.skd.astralcore.relic;

import com.google.common.collect.Multimap;
import com.skd.regaliaslotsapi.api.SlotContext;
import com.skd.regaliaslotsapi.api.type.capability.ICurioItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Holder;

/**
 * Abstract base class for relic items. Mirrors how {@code AltarBlockEntity} is an abstract base
 * for majestic's concrete altar block — same pattern, one level up for items.
 * <p>
 * Concrete relic items extend this class, implement {@link Relic} via their
 * {@link RelicType} factory, and override the desired default behaviours.
 */
public abstract class RelicItem extends Item implements ICurioItem {

    protected RelicItem(Properties properties) {
        super(properties);
    }

    public abstract RelicType<?> getRelicType();

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        RelicTickHandler.tick(getRelicType().create(), stack, slotContext.entity());
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        getRelicType().create().onEquip(stack, slotContext.entity());
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        getRelicType().create().onUnequip(stack, slotContext.entity());
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        return getRelicType().create().getPassiveAttributeModifiers();
    }
}
