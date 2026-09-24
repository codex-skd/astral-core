package com.skd.astralcore.event;

import com.skd.astralcore.cast.CastContext;
import com.skd.astralcore.cast.SpellType;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

/**
 * Posted on the mod event bus before a spell is cast.
 * Cancel this event to prevent the cast entirely.
 */
public final class PreCastEvent extends Event implements ICancellableEvent {

    private final SpellType<?> spellType;
    private final CastContext context;

    public PreCastEvent(SpellType<?> spellType, CastContext context) {
        this.spellType = spellType;
        this.context = context;
    }

    public SpellType<?> getSpellType() {
        return spellType;
    }

    public CastContext getContext() {
        return context;
    }
}
