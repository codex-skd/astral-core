package com.skd.astralcore.research;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.util.INBTSerializable;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Server-authoritative per-player research state attached via NeoForge Data Attachments.
 */
public final class ResearchData implements INBTSerializable<CompoundTag> {

    private final Set<ResourceLocation> unlocked = new LinkedHashSet<>();

    public ResearchData() {}

    // --- Queries ---

    public boolean has(ResourceLocation nodeId) {
        return unlocked.contains(nodeId);
    }

    public Set<ResourceLocation> getUnlocked() {
        return Collections.unmodifiableSet(unlocked);
    }

    // --- Mutations (server only, also used by client sync) ---

    public boolean unlock(ResourceLocation nodeId) {
        return unlocked.add(nodeId);
    }

    public void clear() {
        unlocked.clear();
    }

    // --- Serialisation ---

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        ListTag list = new ListTag();
        for (ResourceLocation id : unlocked) {
            list.add(StringTag.valueOf(id.toString()));
        }
        tag.put("Unlocked", list);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        unlocked.clear();
        ListTag list = tag.getList("Unlocked", 8 /* string tag type */);
        for (int i = 0; i < list.size(); i++) {
            unlocked.add(ResourceLocation.parse(list.getString(i)));
        }
    }
}
