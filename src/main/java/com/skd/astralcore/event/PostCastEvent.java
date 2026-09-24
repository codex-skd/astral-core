package com.skd.astralcore.event;

import com.skd.astralcore.cast.CastContext;
import com.skd.astralcore.cast.CastResult;
import com.skd.astralcore.cast.SpellType;
import net.neoforged.bus.api.Event;

/**
 * Posted on the mod event bus after a spell cast attempt (success or failure).
 */
public final class PostCastEvent extends Event {

    private final SpellType<?> spellType;
    private final CastContext context;
    private final CastResult result;

    public PostCastEvent(SpellType<?> spellType, CastContext context, CastResult result) {
        this.spellType = spellType;
        this.context = context;
        this.result = result;
    }

    public SpellType<?> getSpellType() {
        return spellType;
    }

    public CastContext getContext() {
        return context;
    }

    public CastResult getResult() {
        return result;
    }
}
