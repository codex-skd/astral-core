package com.skd.astralcore.essence;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Tracks which {@link EssenceModifier} instances are currently active for a given player.
 *
 * <p>This is the runtime state — modifier sources register/unregister themselves here.
 * In M1 there are no concrete sources; this exists so the API is complete.</p>
 */
public final class EssenceModifierRegistry {

    private EssenceModifierRegistry() {}

    private static final Map<Player, List<EssenceModifierInstance>> ACTIVE = new ConcurrentHashMap<>();

    public static List<EssenceModifierInstance> getActive() {
        return Collections.emptyList();
    }

    public static List<EssenceModifierInstance> getActive(Player player) {
        return ACTIVE.getOrDefault(player, Collections.emptyList());
    }

    public static void add(Player player, EssenceModifierInstance instance) {
        ACTIVE.computeIfAbsent(player, k -> new java.util.ArrayList<>()).add(instance);
    }

    public static void remove(Player player, ResourceLocation modifierId) {
        List<EssenceModifierInstance> list = ACTIVE.get(player);
        if (list != null) {
            list.removeIf(i -> i.modifier().id().equals(modifierId));
        }
    }

    public static void clearFor(Player player) {
        ACTIVE.remove(player);
    }
}
