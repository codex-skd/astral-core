package com.skd.astralcore.research.net;

import com.skd.astralcore.research.ResearchAttachment;
import com.skd.astralcore.research.ResearchData;
import com.skd.astralcore.research.ResearchSyncPayload;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Client-side handler for research sync packets.
 */
public final class ResearchClientHandler {

    private ResearchClientHandler() {}

    public static void handle(ResearchSyncPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                ResearchData data = mc.player.getData(ResearchAttachment.RESEARCH);
                data.clear();
                for (var id : payload.unlockedIds()) {
                    data.unlock(id);
                }
                mc.player.setData(ResearchAttachment.RESEARCH, data);
            }
        });
    }
}
