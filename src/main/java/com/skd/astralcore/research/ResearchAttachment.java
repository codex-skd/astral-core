package com.skd.astralcore.research;

import com.skd.astralcore.AstralCore;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

/**
 * Registration and lifecycle for the per-player {@link ResearchData} attachment.
 */
public final class ResearchAttachment {

    private ResearchAttachment() {}

    public static final DeferredRegister<AttachmentType<?>> TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, AstralCore.MOD_ID);

    public static final Supplier<AttachmentType<ResearchData>> RESEARCH =
            TYPES.register("research", () -> AttachmentType.serializable(ResearchData::new)
                    .copyOnDeath()
                    .build());

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            ResearchApi.syncFull(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            ResearchApi.syncFull(serverPlayer);
        }
    }
}
