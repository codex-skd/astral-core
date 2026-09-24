package com.skd.astralcore.research;

import net.minecraft.resources.ResourceLocation;

import java.util.List;

/**
 * A single node in the research graph.
 *
 * @param id           unique id of this node
 * @param requirements ids of nodes that must already be unlocked before this one (empty = no prerequisite)
 * @param unlocks      free-form ids of things this node unlocks (opaque to astral_core)
 */
public record ResearchNode(
        ResourceLocation id,
        List<ResourceLocation> requirements,
        List<ResourceLocation> unlocks
) {
    public ResearchNode {
        requirements = List.copyOf(requirements);
        unlocks = List.copyOf(unlocks);
    }
}
