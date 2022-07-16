package net.luke.vanillamagic.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.luke.vanillamagic.VanillaMagic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item WOODEN_STAFF = registerItem("wooden_staff", new Item(new FabricItemSettings().group(ModItemGroup.VANILLA_MAGIC)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(VanillaMagic.MOD_ID, name), item);
    }

    public static void registerModItems() {
        VanillaMagic.LOGGER.debug("Registering Mod Items " + VanillaMagic.MOD_ID);
    }
}
