package com.skd.astralcore.cast;

import com.skd.astralcore.essence.EssenceApi;
import com.skd.astralcore.event.PostCastEvent;
import com.skd.astralcore.event.PreCastEvent;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForge;

/**
 * A spell is a behaviour contract. Concrete implementations are provided by consumer mods.
 * The {@link SpellType} registry holds all registered spells.
 */
public interface Spell {

    SpellType<?> getType();

    default double getCost() {
        return 0.0;
    }

    default int getCooldownTicks() {
        return 0;
    }

    default String getCategory() {
        return "default";
    }

    default net.minecraft.resources.ResourceLocation getSchool() {
        return null;
    }

    CastResult cast(CastContext context);

    /**
     * Full cast pipeline: PreCast event → cost check → cast() → PostCast event.
     */
    default CastResult execute(CastContext context) {
        PreCastEvent preEvent = new PreCastEvent(getType(), context);
        NeoForge.EVENT_BUS.post(preEvent);
        if (preEvent.isCanceled()) {
            return CastResult.failure("Cancelled by PreCastEvent");
        }

        double cost = getCost();
        if (cost > 0 && context.caster() instanceof Player player) {
            if (!EssenceApi.consume(player, cost)) {
                CastResult insufficient = CastResult.insufficientEssence();
                NeoForge.EVENT_BUS.post(new PostCastEvent(getType(), context, insufficient));
                return insufficient;
            }
        }

        CastResult result = cast(context);
        NeoForge.EVENT_BUS.post(new PostCastEvent(getType(), context, result));
        return result;
    }
}
