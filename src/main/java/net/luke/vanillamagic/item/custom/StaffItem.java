package net.luke.vanillamagic.item.custom;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.luke.vanillamagic.item.ModItemGroup;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StaffItem extends Item {
    private int power;

    public StaffItem(FabricItemSettings settings) {
        super(settings);
    }

    public StaffItem power(int power) {
        this.power = power;
        return this;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            ItemStack stack = user.getStackInHand(Hand.OFF_HAND);
            ItemStack spell = user.getStackInHand(Hand.MAIN_HAND);
            stack.damage(1, user, e -> e.sendEquipmentBreakStatus(EquipmentSlot.OFFHAND));
            spell.damage(1, user, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        }

        return super.use(world, user, hand);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 32;
    }
    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {

    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.literal("Used to cast spells.").formatted(Formatting.WHITE));
        tooltip.add(Text.literal("+" + this.power + " Power").formatted(Formatting.YELLOW));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
