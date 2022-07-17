package net.luke.vanillamagic.item.custom;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.luke.vanillamagic.item.ModItemGroup;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Spell extends Item {
    private int coolodwn;
    private int cost;
    private SpellType type;
    public Spell(SpellSettings spellSettings, Rarity rarity) {
        super(new FabricItemSettings().group(ModItemGroup.VANILLA_MAGIC).maxCount(1).rarity(rarity));
        this.coolodwn = spellSettings.cooldown;
        this.cost = spellSettings.cost;
        this.type = spellSettings.type;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient() && hand == Hand.MAIN_HAND) {
            user.getItemCooldownManager().set(this, coolodwn);
            switch (this.type) {
                case RAIN:
                    world.getLevelProperties().setRaining(true);
                    break;
                case SUN:
                    world.getLevelProperties().setRaining(false);
                    break;
                case DAY:
                    user.sendMessage(Text.literal("Set time to day"));
                    break;
                case NIGHT:
                    user.sendMessage(Text.literal("Set time to night"));
                    break;
            }
        }

        return super.use(world, user, hand);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 32;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        switch (this.type) {
            case RAIN:
                tooltip.add(Text.literal("Make it rain.").formatted(Formatting.WHITE));
                break;
            case SUN:
                tooltip.add(Text.literal("Stop it from raining.").formatted(Formatting.WHITE));
                break;
            case DAY:
                tooltip.add(Text.literal("Make it day.").formatted(Formatting.WHITE));
                break;
            case NIGHT:
                tooltip.add(Text.literal("Make it night.").formatted(Formatting.WHITE));
                break;
        }
        tooltip.add(Text.literal(this.cost + " Mana Cost").formatted(Formatting.BLUE));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
