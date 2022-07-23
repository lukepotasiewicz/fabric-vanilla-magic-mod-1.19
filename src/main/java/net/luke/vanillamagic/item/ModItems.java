package net.luke.vanillamagic.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.luke.vanillamagic.VanillaMagic;
import net.luke.vanillamagic.item.custom.*;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item WOODEN_STAFF = registerItem("wooden_staff", new StaffItem(
            new FabricItemSettings()
                    .group(ModItemGroup.VANILLA_MAGIC)
                    .maxCount(1)
                    .rarity(Rarity.COMMON)
                    .maxDamage(100))
            .power(0));

    public static final Item RAIN_SPELL = registerItem("rain_spell", new SpellItem(
            new SpellSettings(SpellType.RAIN)
                    .cooldown(80)
                    .cost(10)
                    .castTime(32),
            Rarity.UNCOMMON

    ));
    public static final Item SUN_SPELL = registerItem("sun_spell", new SpellItem(
            new SpellSettings(SpellType.SUN)
                    .cooldown(80)
                    .cost(10)
                    .castTime(32),
            Rarity.UNCOMMON
    ));
    public static final Item DAY_SPELL = registerItem("day_spell", new SpellItem(
            new SpellSettings(SpellType.DAY)
                    .cooldown(80)
                    .cost(10)
                    .castTime(32),
            Rarity.UNCOMMON
    ));
    public static final Item NIGHT_SPELL = registerItem("night_spell", new SpellItem(
            new SpellSettings(SpellType.NIGHT)
                    .cooldown(80)
                    .cost(10)
                    .castTime(32),
            Rarity.UNCOMMON
    ));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(VanillaMagic.MOD_ID, name), item);
    }

    public static void registerModItems() {
        VanillaMagic.LOGGER.debug("Registering Mod Items " + VanillaMagic.MOD_ID);
    }
}
