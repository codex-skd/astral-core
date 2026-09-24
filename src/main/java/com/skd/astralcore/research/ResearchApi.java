package com.skd.astralcore.research;

import com.skd.astralcore.event.NodeUnlockedEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * Static, server-authoritative API for the research graph.
 */
public final class ResearchApi {

    private ResearchApi() {}

    /**
     * Attempt to unlock a research node for a player.
     * Checks all prerequisites via {@link ResearchGraph} first.
     *
     * @return {@code true} if the node was successfully unlocked, {@code false} if requirements not met or already unlocked
     */
    public static boolean unlock(Player player, ResourceLocation nodeId) {
        if (player.level().isClientSide()) return false;

        ResearchNode node = ResearchGraph.get(nodeId).orElse(null);
        if (node == null) return false;

        ResearchData data = player.getData(ResearchAttachment.RESEARCH);

        // Already unlocked
        if (data.has(nodeId)) return false;

        // Check all requirements
        for (ResourceLocation req : node.requirements()) {
            if (!data.has(req)) return false;
        }

        // Unlock
        data.unlock(nodeId);

        // Fire event
        NeoForge.EVENT_BUS.post(new NodeUnlockedEvent((ServerPlayer) player, nodeId));

        // Sync to client
        sync((ServerPlayer) player);

        return true;
    }

    /**
     * Check if a player has unlocked a given research node.
     * Safe to call on both client and server side.
     */
    public static boolean has(Player player, ResourceLocation nodeId) {
        return player.getData(ResearchAttachment.RESEARCH).has(nodeId);
    }

    public static void sync(ServerPlayer player) {
        ResearchData data = player.getData(ResearchAttachment.RESEARCH);
        PacketDistributor.sendToPlayer(player, new ResearchSyncPayload(data.getUnlocked().stream().toList()));
    }

    public static void syncFull(ServerPlayer player) {
        sync(player);
    }
}
