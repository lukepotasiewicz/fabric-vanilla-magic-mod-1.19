package net.luke.vanillamagic.util;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.luke.vanillamagic.item.ModItems;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;

public class ModLootTableModifiers {
    private static final Identifier GRASS_BLOCK_ID
            = new Identifier("minecraft", "blocks/grass");
    private static final Identifier SHIPWRECK_TREASURE_ID
            = new Identifier("minecraft", "chests/shipwreck_treasure");
    private static final Identifier WITCH_ID
            = new Identifier("minecraft", "entities/witch");

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (source.isBuiltin() && SHIPWRECK_TREASURE_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                    .with(ItemEntry.builder(ModItems.RAIN_SPELL));
                tableBuilder.pool(poolBuilder);
            }

            if (source.isBuiltin() && WITCH_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .with(ItemEntry.builder(ModItems.WOODEN_STAFF)).conditionally(RandomChanceLootCondition.builder(0.3f));
                tableBuilder.pool(poolBuilder);
            }
        });
    }
}