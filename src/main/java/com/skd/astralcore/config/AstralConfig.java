package com.skd.astralcore.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public final class AstralConfig {

    private AstralConfig() {}

    public static final ModConfigSpec SERVER_SPEC;
    public static final Server SERVER;

    static {
        Pair<Server, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(Server::new);
        SERVER = pair.getLeft();
        SERVER_SPEC = pair.getRight();
    }

    public static final class Server {

        public final ModConfigSpec.DoubleValue essenceBaseCapacity;
        public final ModConfigSpec.DoubleValue essenceRegenPerSecond;
        public final ModConfigSpec.IntValue combatDamageThresholdTicks;
        public final ModConfigSpec.IntValue globalCooldownFloorTicks;

        Server(ModConfigSpec.Builder builder) {
            builder.push("essence");

            essenceBaseCapacity = builder
                    .comment("Base essence capacity for all players.")
                    .defineInRange("baseCapacity", 100.0, 1.0, 1_000_000.0);

            essenceRegenPerSecond = builder
                    .comment("Essence regenerated per second when out of combat.")
                    .defineInRange("regenPerSecond", 1.0, 0.0, 1000.0);

            combatDamageThresholdTicks = builder
                    .comment("Ticks since last damage before a player is considered out of combat.")
                    .defineInRange("combatDamageThresholdTicks", 100, 0, 72000);

            builder.pop();

            builder.push("casting");

            globalCooldownFloorTicks = builder
                    .comment("Minimum ticks between any two casts, regardless of spell cooldown.")
                    .defineInRange("globalCooldownFloorTicks", 5, 0, 600);

            builder.pop();
        }
    }
}
