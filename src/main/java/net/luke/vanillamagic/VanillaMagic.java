package net.luke.vanillamagic;

import net.fabricmc.api.ModInitializer;
import net.luke.vanillamagic.item.ModItems;
import net.luke.vanillamagic.util.ModLootTableModifiers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VanillaMagic implements ModInitializer {
	public static final String MOD_ID = "vanillamagic";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info("Hello Fabric world!");

		ModItems.registerModItems();
		ModLootTableModifiers.modifyLootTables();
	}
}
