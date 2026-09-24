package com.skd.astralcore.essence;

import com.skd.astralcore.AstralCore;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * Server→Client payload that synchronises a player's current essence value and capacity.
 */
public record EssenceSyncPayload(double value, double capacity) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<EssenceSyncPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AstralCore.MOD_ID, "essence_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, EssenceSyncPayload> CODEC =
            StreamCodec.of(
                    (buf, payload) -> {
                        buf.writeDouble(payload.value());
                        buf.writeDouble(payload.capacity());
                    },
                    buf -> new EssenceSyncPayload(buf.readDouble(), buf.readDouble())
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
