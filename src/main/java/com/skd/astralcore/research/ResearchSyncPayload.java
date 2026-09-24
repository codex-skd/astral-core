package com.skd.astralcore.research;

import com.skd.astralcore.AstralCore;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Server→Client payload that synchronises a player's full set of unlocked research nodes.
 */
public record ResearchSyncPayload(List<ResourceLocation> unlockedIds) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ResearchSyncPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AstralCore.MOD_ID, "research_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ResearchSyncPayload> CODEC =
            StreamCodec.of(
                    (buf, payload) -> {
                        buf.writeVarInt(payload.unlockedIds().size());
                        for (ResourceLocation id : payload.unlockedIds()) {
                            buf.writeResourceLocation(id);
                        }
                    },
                    buf -> {
                        int size = buf.readVarInt();
                        List<ResourceLocation> ids = new ArrayList<>(size);
                        for (int i = 0; i < size; i++) {
                            ids.add(buf.readResourceLocation());
                        }
                        return new ResearchSyncPayload(ids);
                    }
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
