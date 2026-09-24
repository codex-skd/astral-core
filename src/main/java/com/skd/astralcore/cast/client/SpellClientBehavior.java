package com.skd.astralcore.cast.client;

import com.skd.astralcore.cast.CastContext;
import com.skd.astralcore.cast.SpellType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;

/**
 * Client-side presentational hooks for spells (particles, sound, arm animation).
 * No gameplay logic lives here — purely visual.
 *
 * <p>Register handlers per {@link SpellType} on the client. The handler is invoked
 * after a successful cast (on the client thread, after the server confirms).</p>
 */
public final class SpellClientBehavior {

    private SpellClientBehavior() {}

    private static final Map<SpellType<?>, Handler> HANDLERS = new HashMap<>();

    /**
     * Registers a client-side handler for a spell type.
     */
    public static <T extends com.skd.astralcore.cast.Spell> void register(SpellType<T> spellType, Handler handler) {
        HANDLERS.put(spellType, handler);
    }

    /**
     * Returns the handler for a spell type, or {@code null} if none is registered.
     */
    public static Handler getHandler(SpellType<?> spellType) {
        return HANDLERS.get(spellType);
    }

    /**
     * Client-side handler interface for spell VFX.
     */
    @FunctionalInterface
    public interface Handler {
        /**
         * Called on the client after a spell cast is confirmed.
         *
         * @param context the cast context (caster, level, hand, target)
         */
        void onCast(CastContext context);
    }
}
