package com.skd.astralcore.relic.synergy;

import net.minecraft.resources.ResourceLocation;
import java.util.List;

/**
 * Defines a set of relic types that grant a bonus when enough members are equipped simultaneously.
 *
 * @param id         unique identifier for this set
 * @param members    list of {@link com.skd.astralcore.relic.RelicType} ids that belong to this set
 * @param threshold  how many distinct members must be equipped to activate the bonus
 */
public record RelicSet(
        ResourceLocation id,
        List<ResourceLocation> members,
        int threshold
) {}
