package net.luke.vanillamagic.item.custom;

import net.minecraft.util.Rarity;

public class SpellSettings {

    public SpellSettings(SpellType type) {
        this.type = type;
    }

    public SpellType type;
    public int cooldown;
    public int cost;
    public int castTime;
    public Rarity rarity;

    public SpellSettings cooldown(int cooldown) {
        this.cooldown = cooldown;
        return this;
    }

    public SpellSettings cost(int cost) {
        this.cost = cost;
        return this;
    }

    public SpellSettings castTime(int castTime) {
        this.castTime = castTime;
        return this;
    }

    public SpellSettings rarity(Rarity rarity) {
        this.rarity = rarity;
        return this;
    }
}
