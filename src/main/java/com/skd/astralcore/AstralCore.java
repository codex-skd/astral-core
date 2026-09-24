package com.skd.astralcore;

import com.skd.astralcore.cast.cooldown.CooldownTracker;
import com.skd.astralcore.command.AstralCoreCommand;
import com.skd.astralcore.config.AstralConfig;
import com.skd.astralcore.essence.EssenceAttachment;
import com.skd.astralcore.net.AstralNetwork;
import com.skd.astralcore.research.ResearchAttachment;
import com.skd.astralcore.registry.AstralRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main entry point for Astral Core -- the magic-systems library of the Majestic ecosystem.
 */
@Mod(AstralCore.MOD_ID)
public final class AstralCore {

    public static final String MOD_ID = "astral_core";

    public static final Logger LOGGER = LoggerFactory.getLogger("Astral Core");

    public AstralCore(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.info("Astral Core v{} loading", modContainer.getModInfo().getVersion());

        // Config
        modContainer.registerConfig(net.neoforged.fml.config.ModConfig.Type.SERVER, AstralConfig.SERVER_SPEC);

        // Custom registries (spell types, essence modifiers)
        AstralRegistries.register(modEventBus);

        // Data attachment types
        EssenceAttachment.TYPES.register(modEventBus);
        ResearchAttachment.TYPES.register(modEventBus);

        // Network payloads
        modEventBus.addListener(AstralNetwork::onRegisterPayloads);

        // Server-side events
        NeoForge.EVENT_BUS.register(EssenceAttachment.class);
        NeoForge.EVENT_BUS.register(ResearchAttachment.class);

        // Cooldown tick handler
        NeoForge.EVENT_BUS.addListener(ServerTickEvent.Post.class, event -> {
            for (var player : event.getServer().getPlayerList().getPlayers()) {
                CooldownTracker.tick(player);
            }
        });

        // Command registration
        NeoForge.EVENT_BUS.addListener(RegisterCommandsEvent.class, event -> {
            AstralCoreCommand.register(event.getDispatcher());
        });

        LOGGER.info("Astral Core subsystems registered");
    }
}
