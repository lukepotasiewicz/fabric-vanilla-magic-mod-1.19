package net.luke.vanillamagic.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.luke.vanillamagic.VanillaMagic;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModParticles {
    public static final DefaultParticleType GLYPH_PARTICLE = FabricParticleTypes.simple();
    public static final DefaultParticleType GLYPH_SMALL_PARTICLE = FabricParticleTypes.simple();

    public static void registerParticles() {
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(VanillaMagic.MOD_ID, "glyph_particle"), GLYPH_PARTICLE);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(VanillaMagic.MOD_ID, "glyph_small_particle"), GLYPH_SMALL_PARTICLE);
    }
}
