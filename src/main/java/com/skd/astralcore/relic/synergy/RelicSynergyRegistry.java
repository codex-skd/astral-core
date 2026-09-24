package com.skd.astralcore.relic.synergy;

import net.minecraft.resources.ResourceLocation;
import java.util.*;

/**
 * In-memory registry for {@link RelicSet} definitions.
 * <p>
 * Pure query-based — no side effects, no caching, no event firing.
 * The caller (e.g. majestic) is responsible for determining which relic types are currently
 * equipped by querying {@code regalia_slots_api}'s own inventory API; this class simply
 * evaluates set bonuses given a caller-supplied set of equipped type ids.
 */
public final class RelicSynergyRegistry {

    private RelicSynergyRegistry() {}

    private static final Map<ResourceLocation, RelicSet> SETS = new LinkedHashMap<>();

    public static void register(RelicSet set) {
        SETS.put(set.id(), set);
    }

    public static Collection<RelicSet> getAll() {
        return Collections.unmodifiableCollection(SETS.values());
    }

    /**
     * Returns every registered {@link RelicSet} whose member-count-present meets its threshold.
     *
     * @param equippedRelicTypeIds set of currently-equipped relic type ids (caller-supplied)
     * @return list of active set bonuses
     */
    public static List<RelicSet> getActiveSetBonuses(Set<ResourceLocation> equippedRelicTypeIds) {
        List<RelicSet> active = new ArrayList<>();
        for (RelicSet set : SETS.values()) {
            int count = 0;
            for (ResourceLocation memberId : set.members()) {
                if (equippedRelicTypeIds.contains(memberId)) {
                    count++;
                }
            }
            if (count >= set.threshold()) {
                active.add(set);
            }
        }
        return active;
    }
}
