package com.skd.astralcore.cast.cooldown;

import com.skd.astralcore.cast.SpellType;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.server.level.ServerPlayer;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Server-side, in-memory cooldown tracker. Per-player, per-{@link SpellType}.
 * Cooldowns are not persisted across server restarts.
 */
public final class CooldownTracker {

    private CooldownTracker() {}

    private static final Map<UUID, Object2IntMap<SpellType<?>>> COOLDOWNS = new ConcurrentHashMap<>();

    public static void set(ServerPlayer player, SpellType<?> spellType, int ticks) {
        COOLDOWNS.computeIfAbsent(player.getUUID(), k -> new Object2IntOpenHashMap<>())
                .put(spellType, ticks);
    }

    public static int get(ServerPlayer player, SpellType<?> spellType) {
        Object2IntMap<SpellType<?>> map = COOLDOWNS.get(player.getUUID());
        return map != null ? map.getInt(spellType) : 0;
    }

    public static boolean isOnCooldown(ServerPlayer player, SpellType<?> spellType) {
        return get(player, spellType) > 0;
    }

    public static void tick(ServerPlayer player) {
        Object2IntMap<SpellType<?>> map = COOLDOWNS.get(player.getUUID());
        if (map == null) return;
        map.replaceAll((spell, ticks) -> Math.max(0, ticks - 1));
    }

    public static void clear(ServerPlayer player) {
        COOLDOWNS.remove(player.getUUID());
    }

    public static void cleanup() {
        COOLDOWNS.values().removeIf(map -> map.isEmpty());
    }
}
