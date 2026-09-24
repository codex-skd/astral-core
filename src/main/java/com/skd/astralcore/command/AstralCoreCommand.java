package com.skd.astralcore.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.skd.astralcore.essence.EssenceAttachment;
import com.skd.astralcore.essence.EssenceData;
import com.skd.astralcore.registry.AstralRegistries;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.stream.Collectors;

/**
 * Debug command: {@code /astralcore status}
 * Reports current essence / capacity and registered spell types for the executing player.
 */
public final class AstralCoreCommand {

    private AstralCoreCommand() {}

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("astralcore")
                .then(Commands.literal("status")
                        .executes(AstralCoreCommand::status)));
    }

    private static int status(CommandContext<CommandSourceStack> ctx) throws com.mojang.brigadier.exceptions.CommandSyntaxException {
        ServerPlayer player = ctx.getSource().getPlayerOrException();
        EssenceData data = player.getData(EssenceAttachment.ESSENCE);

        ctx.getSource().sendSuccess(() -> Component.literal(
                String.format("Essence: %.1f / %.1f", data.getValue(), data.getCapacity())
        ), false);

        var registry = AstralRegistries.SPELL_TYPES;
        if (registry != null && registry.size() > 0) {
            String spellList = registry.holders()
                    .map(holder -> holder.key().location().toString())
                    .collect(Collectors.joining(", "));
            ctx.getSource().sendSuccess(() -> Component.literal("Registered spells: " + spellList), false);
        } else {
            ctx.getSource().sendSuccess(() -> Component.literal("Registered spells: (none)"), false);
        }

        return 1;
    }
}
