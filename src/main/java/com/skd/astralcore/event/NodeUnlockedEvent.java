package com.skd.astralcore.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.Event;

/**
 * Posted on the NeoForge event bus when a research node is unlocked for a player.
 * Non-cancellable — the unlock has already happened.
 */
public final class NodeUnlockedEvent extends Event {

    private final ServerPlayer player;
    private final ResourceLocation nodeId;

    public NodeUnlockedEvent(ServerPlayer player, ResourceLocation nodeId) {
        this.player = player;
        this.nodeId = nodeId;
    }

    public ServerPlayer getPlayer() {
        return player;
    }

    public ResourceLocation getNodeId() {
        return nodeId;
    }
}
