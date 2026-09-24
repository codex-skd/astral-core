package com.skd.astralcore.research;

import com.skd.astralcore.event.ResearchTriggerEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForge;

/**
 * Static helper for consumer mods to report research progress triggers.
 * Consumers listen to {@link ResearchTriggerEvent} and call {@link ResearchApi#unlock} themselves.
 */
public final class ResearchTrigger {

    private ResearchTrigger() {}

    /**
     * Fire a research trigger event. Does NOT automatically unlock anything —
     * consumers (e.g. majestic) listen to this event and decide what to unlock.
     */
    public static void fire(Player player, ResourceLocation triggerId) {
        NeoForge.EVENT_BUS.post(new ResearchTriggerEvent(player, triggerId));
    }
}
