package com.skd.astralcore.relic;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Static metadata registry mapping {@link RelicType} instances to their desired curio slot id.
 * <p>
 * This is pure bookkeeping (e.g. for a future tooltip/HUD to know "this relic type wants slot X").
 * It does <b>not</b> perform any actual slot assignment — slot assignment is tag-driven per
 * {@code regalia_slots_api}'s own design: an item goes into a given curio slot by having the item
 * tags {@code #regalia_slots_api:<slot>} AND {@code #curios:<slot>}.
 */
public final class RelicSlots {

    private RelicSlots() {}

    private static final Map<RelicType<?>, String> SLOTS = new HashMap<>();

    public static void bind(RelicType<?> type, String slotId) {
        SLOTS.put(type, slotId);
    }

    public static Optional<String> getSlot(RelicType<?> type) {
        return Optional.ofNullable(SLOTS.get(type));
    }
}
