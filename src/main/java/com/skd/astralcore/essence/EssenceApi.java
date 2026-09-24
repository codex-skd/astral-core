package com.skd.astralcore.essence;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * Static, server-authoritative API for reading and modifying essence.
 * All mutating methods are server-only — the client never decides essence state.
 */
public final class EssenceApi {

    private EssenceApi() {}

    public static double get(net.minecraft.world.entity.player.Player player) {
        return player.getData(EssenceAttachment.ESSENCE).getValue();
    }

    public static boolean consume(net.minecraft.world.entity.player.Player player, double amount) {
        if (player.level().isClientSide()) return false;
        EssenceData data = player.getData(EssenceAttachment.ESSENCE);
        boolean ok = data.consume(amount);
        if (ok) {
            sync((ServerPlayer) player);
        }
        return ok;
    }

    public static void add(net.minecraft.world.entity.player.Player player, double amount) {
        if (player.level().isClientSide()) return;
        EssenceData data = player.getData(EssenceAttachment.ESSENCE);
        data.add(amount);
        sync((ServerPlayer) player);
    }

    public static double getCapacity(net.minecraft.world.entity.player.Player player) {
        return player.getData(EssenceAttachment.ESSENCE).getCapacity();
    }

    public static void sync(ServerPlayer player) {
        EssenceData data = player.getData(EssenceAttachment.ESSENCE);
        PacketDistributor.sendToPlayer(player, new EssenceSyncPayload(data.getValue(), data.getCapacity()));
    }

    public static void syncFull(ServerPlayer player) {
        sync(player);
    }
}
