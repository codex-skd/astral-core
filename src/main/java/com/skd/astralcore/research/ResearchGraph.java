package com.skd.astralcore.research;

import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * In-memory registry of {@link ResearchNode}s.
 * <p>
 * Not a NeoForge custom {@link net.minecraft.core.Registry} — nodes are plain data,
 * not something that needs {@code Holder} / {@code ResourceKey} support.
 */
public final class ResearchGraph {

    private ResearchGraph() {}

    private static final Map<ResourceLocation, ResearchNode> NODES = new LinkedHashMap<>();

    public static void register(ResearchNode node) {
        NODES.put(node.id(), node);
    }

    public static Optional<ResearchNode> get(ResourceLocation id) {
        return Optional.ofNullable(NODES.get(id));
    }

    public static Collection<ResearchNode> getAll() {
        return Collections.unmodifiableCollection(NODES.values());
    }

    public static void clear() {
        NODES.clear();
    }
}
