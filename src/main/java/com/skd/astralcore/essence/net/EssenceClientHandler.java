package com.skd.astralcore.essence.net;

import com.skd.astralcore.essence.EssenceAttachment;
import com.skd.astralcore.essence.EssenceData;
import com.skd.astralcore.essence.EssenceSyncPayload;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Client-side handler for essence sync packets.
 */
public final class EssenceClientHandler {

    private EssenceClientHandler() {}

    public static void handle(EssenceSyncPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                EssenceData data = mc.player.getData(EssenceAttachment.ESSENCE);
                data.setValue(payload.value());
                data.setCapacity(payload.capacity());
                mc.player.setData(EssenceAttachment.ESSENCE, data);
            }
        });
    }
}
