package net.luke.vanillamagic.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.world.World;

import java.util.Random;

public class ParticleUtil {

    private final World world;
    private final LivingEntity user;

    public ParticleUtil(World world, LivingEntity user) {
        this.world = world;
        this.user = user;
    }

    public void spawnInRing(DefaultParticleType type, int number, float size, float yOffset, float yVel, float randomness, float rotation) {
        for(int i = 0; i < number; i++) {
            float randomX = 0;
            float randomZ = 0;
            Random rand = new Random();
            int randomAngle = rand.nextInt(360);
            if (randomness > 0) {
                randomX = (rand.nextFloat() - 0.5f) * randomness;
                randomZ = (rand.nextFloat() - 0.5f) * randomness;
            }
            world.addParticle(type,
                    user.getX() + Math.cos(randomAngle)*size,
                    user.getY() + yOffset,
                    user.getZ() + Math.sin(randomAngle)*size,
                    randomX + Math.cos(randomAngle + 90) * rotation,
                    yVel,
                    randomZ +  Math.sin(randomAngle + 90) * rotation);
        }
    }

    public void spawnInRing(DefaultParticleType type, int number, float size, float yOffset, float yVel, float randomness) {
        this.spawnInRing(type, number, size, yOffset, yVel, randomness, 0);
    }

}

