package com.skd.astralcore.essence;

import com.skd.astralcore.AstralCore;
import com.skd.astralcore.config.AstralConfig;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Server-authoritative essence state attached to each player via NeoForge Data Attachments.
 *
 * <p>Essence is a <b>single global resource</b> (not per-school). The value and capacity
 * are synced to the client automatically on change, login, and dimension change.</p>
 */
public final class EssenceData implements INBTSerializable<CompoundTag> {

    private double value;
    private double capacity;
    private int ticksSinceLastDamage;

    public EssenceData() {
        this.value = 0.0;
        this.capacity = AstralConfig.SERVER.essenceBaseCapacity.get();
        this.ticksSinceLastDamage = Integer.MAX_VALUE;
    }

    // --- Getters ---

    public double getValue() {
        return value;
    }

    public double getCapacity() {
        return capacity;
    }

    public int getTicksSinceLastDamage() {
        return ticksSinceLastDamage;
    }

    public boolean isOutOfCombat() {
        return ticksSinceLastDamage >= AstralConfig.SERVER.combatDamageThresholdTicks.get();
    }

    // --- Mutations (server only) ---

    public boolean consume(double amount) {
        if (amount <= 0) return true;
        if (value < amount) return false;
        value -= amount;
        return true;
    }

    public void add(double amount) {
        if (amount <= 0) return;
        value = Math.min(value + amount, capacity);
    }

    public void setValue(double value) {
        this.value = Math.clamp(value, 0.0, capacity);
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public void recordDamage() {
        this.ticksSinceLastDamage = 0;
    }

    public void tickRegen() {
        ticksSinceLastDamage++;
        if (isOutOfCombat() && value < capacity) {
            double regen = AstralConfig.SERVER.essenceRegenPerSecond.get();
            add(regen);
        }
    }

    // --- Serialisation (for data attachment) ---

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putDouble("Value", value);
        tag.putDouble("Capacity", capacity);
        tag.putInt("TicksSinceLastDamage", ticksSinceLastDamage);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        this.value = tag.getDouble("Value");
        this.capacity = tag.getDouble("Capacity");
        this.ticksSinceLastDamage = tag.getInt("TicksSinceLastDamage");
    }
}
