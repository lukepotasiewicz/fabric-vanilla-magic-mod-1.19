package net.luke.vanillamagic.item.custom;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.luke.vanillamagic.item.ModItemGroup;
import net.luke.vanillamagic.item.ModItems;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
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
    public static final int field_30855 = 20;
    private int cooldown;
    private int cost;
    private int castTime;
    private SpellType type;

    public PlayerEntity currentUser;
    public SpellItem(SpellSettings spellSettings, Rarity rarity) {
        super(new FabricItemSettings().group(ModItemGroup.VANILLA_MAGIC).maxCount(1).rarity(rarity));
        this.cooldown = spellSettings.cooldown;
        this.cost = spellSettings.cost;
        this.type = spellSettings.type;
        this.castTime = spellSettings.castTime;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        user.sendMessage(Text.literal("Stopped using" + remainingUseTicks));
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (remainingUseTicks == 0) { // TODO: this may break on lagging servers
            if (!world.isClient) {

                ItemStack staff = user.getStackInHand(Hand.OFF_HAND);
                staff.damage(this.cost, user, e -> e.sendEquipmentBreakStatus(EquipmentSlot.OFFHAND));

                this.currentUser.getItemCooldownManager().set(this, this.cooldown);

                world.playSound(null, user.getX(), user.getY(), user.getZ(),
                        SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME,
                        SoundCategory.PLAYERS,
                        3.0f, 1.0f / (world.getRandom().nextFloat() * 0.5f + 1.0f) + 0.2f);
                switch (this.type) {
                    case RAIN -> {
                        world.getLevelProperties().setRaining(true);
                    }
                    case SUN -> world.getLevelProperties().setRaining(false);
                    case DAY -> user.sendMessage(Text.literal("Set time to day"));
                    case NIGHT -> user.sendMessage(Text.literal("Set time to night"));
                }
            }
        }
    }
    @Override
    public int getMaxUseTime(ItemStack stack) {
        return this.castTime;
    }
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack item = user.getStackInHand(hand);
        if (!world.isClient && hand == Hand.MAIN_HAND) {
            user.isHolding(ModItems.WOODEN_STAFF);
            user.sendMessage(Text.literal("Spell use"));
            this.currentUser = user;

            ItemStack staff = user.getStackInHand(Hand.OFF_HAND);
            String itemName = staff.getItem().getTranslationKey();
            user.sendMessage(Text.literal( "Item name: " + itemName));
            user.sendMessage(Text.literal(staff.getMaxDamage() - staff.getDamage() + " :)"));
//            user.isHolding(ModItems.WOODEN_STAFF);
            if (itemName.equals("item.vanillamagic.wooden_staff") && staff.getMaxDamage() - staff.getDamage() >= this.cost) {
                user.sendMessage(Text.literal( "in cast if statement"));
                user.setCurrentHand(hand);
                return TypedActionResult.success(item);
            }
        }
        return TypedActionResult.fail(item);
    }
    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
    @Override
    public boolean isUsedOnRelease(ItemStack stack) {
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
