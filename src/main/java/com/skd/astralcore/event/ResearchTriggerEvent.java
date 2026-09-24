package com.skd.astralcore.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;

/**
 * Posted on the NeoForge event bus when a consumer mod reports a research trigger.
 * Consumers listen to this and call {@link com.skd.astralcore.research.ResearchApi#unlock} themselves.
 * Non-cancellable — this is purely informational.
 */
public final class ResearchTriggerEvent extends Event {

    private final Player player;
    private final ResourceLocation triggerId;

    public ResearchTriggerEvent(Player player, ResourceLocation triggerId) {
        this.player = player;
        this.triggerId = triggerId;
    }

    public Player getPlayer() {
        return player;
    }

    public ResourceLocation getTriggerId() {
        return triggerId;
    }
}
