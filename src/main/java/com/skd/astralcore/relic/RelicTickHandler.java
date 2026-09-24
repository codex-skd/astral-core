package com.skd.astralcore.relic;

import com.skd.astralcore.AstralCore;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

/**
 * Centralised safe dispatch for relic tick logic.
 * <p>
 * This class exists purely to wrap {@link Relic#onTick} in a try/catch so that a single
 * misbehaving relic's tick logic never crashes the curio-tick dispatch that
 * {@code regalia_slots_api} drives every tick for every equipped curio automatically.
 * <p>
 * <b>Do NOT build a separate periodic scanning/ticking system here</b> —
 * {@code regalia_slots_api} already calls {@code curioTick} once per tick per equipped curio.
 */
public final class RelicTickHandler {

    private RelicTickHandler() {}

    public static void tick(Relic relic, ItemStack stack, LivingEntity wearer) {
        try {
            relic.onTick(stack, wearer);
        } catch (Exception e) {
            AstralCore.LOGGER.error("Exception during relic tick for {}: {}", relic.getType().id(), e.getMessage(), e);
        }
    }
}
