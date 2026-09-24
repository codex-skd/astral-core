package com.skd.astralcore.net;

import com.skd.astralcore.AstralCore;
import com.skd.astralcore.essence.EssenceSyncPayload;
import com.skd.astralcore.essence.net.EssenceClientHandler;
import com.skd.astralcore.research.ResearchSyncPayload;
import com.skd.astralcore.research.net.ResearchClientHandler;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

/**
 * Registers all custom packet payloads on the mod event bus.
 */
public final class AstralNetwork {

    private AstralNetwork() {}

    @SubscribeEvent
    public static void onRegisterPayloads(RegisterPayloadHandlersEvent event) {
        event.registrar(AstralCore.MOD_ID)
                .versioned("1")
                .playToClient(
                        EssenceSyncPayload.TYPE,
                        EssenceSyncPayload.CODEC,
                        EssenceClientHandler::handle
                )
                .playToClient(
                        ResearchSyncPayload.TYPE,
                        ResearchSyncPayload.CODEC,
                        ResearchClientHandler::handle
                );
    }
}
