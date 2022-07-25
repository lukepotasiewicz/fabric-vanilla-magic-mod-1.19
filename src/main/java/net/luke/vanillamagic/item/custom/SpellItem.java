package net.luke.vanillamagic.item.custom;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.luke.vanillamagic.item.ModItemGroup;
import net.luke.vanillamagic.item.ModItems;
import net.luke.vanillamagic.particle.ModParticles;
import net.luke.vanillamagic.util.ParticleUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
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
//        user.sendMessage(Text.literal("Stopped using" + remainingUseTicks));
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        int castProgress = (int)(((float)(this.castTime - remainingUseTicks))/(float)(this.castTime) * 100f);
        if (remainingUseTicks > 0) {
            if (world.isClient) {
                ParticleUtil particles = new ParticleUtil(world, user);
                particles.spawnInRing(ModParticles.GLYPH_SMALL_PARTICLE, castProgress / 40 + 1,  1f, 0.5f, 0f, 0.01f);
                particles.spawnInRing(ModParticles.GLYPH_PARTICLE, castProgress / 80 + 1, 1.2f, 1f, 0f, 0.01f);
                particles.spawnInRing(ModParticles.GLYPH_SMALL_PARTICLE, castProgress / 40 + 1, 1f, 1.5f, 0f, 0.01f);
                switch (this.type) {
                    case RAIN -> {
                        particles.spawnInRing(ParticleTypes.BUBBLE, 2, 1.2f, 0f, 0.4f, 0.08f);
                        particles.spawnInRing(ParticleTypes.FALLING_DRIPSTONE_WATER, castProgress / 5, 1.2f, 0f, castProgress / 10f * 2.0f, 2f);
                    }
                    case SUN -> {
                        particles.spawnInRing(ParticleTypes.FLAME, castProgress / 10, 1.2f, 0f, castProgress / 10f * 0.013f, 0.04f, castProgress / 10f * 0.01f);
                        if (remainingUseTicks % 3 == 0) {
                            particles.spawnInRing(ParticleTypes.LAVA, 1, 1.2f, 0f, 0.1f, 0.08f);
                        }
                    }
                    case DAY -> {
                        particles.spawnInRing(ParticleTypes.DRIPPING_LAVA, 2, 1.2f, 0f, 0.4f, 0.08f);
                    }
                    case NIGHT -> {
                        particles.spawnInRing(ParticleTypes.END_ROD, 2, 1.2f, 0f, 0.4f, 0.08f);
                    }
                }
            }
        }
        else if (remainingUseTicks == 0) { // TODO: this may break on lagging servers
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
            } else {
                ParticleUtil particles = new ParticleUtil(world, user);
                particles.spawnInRing(ParticleTypes.ENCHANTED_HIT, 30, 1f, 0.5f, -0.2f, 3f);
                particles.spawnInRing(ParticleTypes.ENCHANTED_HIT, 30, 1.2f, 1f, 0f, 3f);
                particles.spawnInRing(ParticleTypes.ENCHANTED_HIT, 30, 1f, 1.5f, 0.4f, 3f);
                switch (this.type) {
                    case RAIN -> {
                        particles.spawnInRing(ParticleTypes.RAIN, 10, 1.2f, 0f, 0.8f, 2f);
                        particles.spawnInRing(ParticleTypes.RAIN, 10, 1.2f, 0.5f, 0.8f, 2f);
                        particles.spawnInRing(ParticleTypes.RAIN, 10, 1.2f, 1f, 0.8f, 2f);
                    }
                    case SUN -> {
                        particles.spawnInRing(ParticleTypes.FLAME, 100, 1.2f, 0f, 0.15f, 0.05f, -0.1f);
                    }
                    case DAY -> {
                        particles.spawnInRing(ParticleTypes.DRIPPING_LAVA, 2, 1.2f, 0f, 0.4f, 0.08f);
                    }
                    case NIGHT -> {
                        particles.spawnInRing(ParticleTypes.END_ROD, 2, 1.2f, 0f, 0.4f, 0.08f);
                    }
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
            this.currentUser = user;

            ItemStack staff = user.getStackInHand(Hand.OFF_HAND);
            String itemName = staff.getItem().getTranslationKey();
//            user.isHolding(ModItems.WOODEN_STAFF);
            if (itemName.equals("item.vanillamagic.wooden_staff") && staff.getMaxDamage() - staff.getDamage() >= this.cost) {
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
