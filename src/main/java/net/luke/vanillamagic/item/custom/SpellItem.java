package net.luke.vanillamagic.item.custom;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.luke.vanillamagic.item.ModItemGroup;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpellItem extends Item {
    private int cooldown;
    private int cost;
    private int castTime;
    private SpellType type;

    public ItemCooldownManager cooldownManager;
    public SpellItem(SpellSettings spellSettings, Rarity rarity) {
        super(new FabricItemSettings().group(ModItemGroup.VANILLA_MAGIC).maxCount(1).rarity(rarity));
        this.cooldown = spellSettings.cooldown;
        this.cost = spellSettings.cost;
        this.type = spellSettings.type;
        this.castTime = spellSettings.castTime;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
//        if (!world.isClient && hand == Hand.MAIN_HAND) {
//            user.getItemCooldownManager().set(this, cooldown);
//            switch (this.type) {
//                case RAIN:
//                    world.getLevelProperties().setRaining(true);
//                    world.playSound(null, user.getX(), user.getY(), user.getZ(),
//                            SoundEvents.ITEM_CROSSBOW_LOADING_END,
//                            SoundCategory.PLAYERS,
//                            1.0f, 1.0f / (world.getRandom().nextFloat() * 0.5f + 1.0f) + 0.2f);
//                    break;
//                case SUN:
//                    world.getLevelProperties().setRaining(false);
//                    break;
//                case DAY:
//                    user.sendMessage(Text.literal("Set time to day"));
//                    break;
//                case NIGHT:
//                    user.sendMessage(Text.literal("Set time to night"));
//                    break;
//            }
//        }
        user.sendMessage(Text.literal("Use"));
        this.cooldownManager = user.getItemCooldownManager();

        ItemStack spell = user.getStackInHand(Hand.MAIN_HAND);
        return TypedActionResult.success(spell);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (!world.isClient) {
            user.sendMessage(Text.literal("Charging, remainingUseTicks: " + remainingUseTicks));
        }
    }
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }
    @Override
    public int getMaxUseTime(ItemStack stack) {
        return this.castTime;
    }
    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        user.sendMessage(Text.literal("Stopped using" + remainingUseTicks));
        int chargeTime = this.getMaxUseTime(stack) - remainingUseTicks;
        if (chargeTime <= 0) {
            if (!world.isClient) {
                this.cooldownManager.set(this, this.cooldown);
                switch (this.type) {
                    case RAIN -> {
                        world.getLevelProperties().setRaining(true);
                        world.playSound(null, user.getX(), user.getY(), user.getZ(),
                                SoundEvents.ITEM_CROSSBOW_LOADING_END,
                                SoundCategory.PLAYERS,
                                1.0f, 1.0f / (world.getRandom().nextFloat() * 0.5f + 1.0f) + 0.2f);
                    }
                    case SUN -> world.getLevelProperties().setRaining(false);
                    case DAY -> user.sendMessage(Text.literal("Set time to day"));
                    case NIGHT -> user.sendMessage(Text.literal("Set time to night"));
                }
            }

        }
    }
    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        switch (this.type) {
            case RAIN -> tooltip.add(Text.literal("Make it rain.").formatted(Formatting.WHITE));
            case SUN -> tooltip.add(Text.literal("Stop it from raining.").formatted(Formatting.WHITE));
            case DAY -> tooltip.add(Text.literal("Make it day.").formatted(Formatting.WHITE));
            case NIGHT -> tooltip.add(Text.literal("Make it night.").formatted(Formatting.WHITE));
        }
        tooltip.add(Text.literal(this.cost + " Mana Cost").formatted(Formatting.BLUE));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
