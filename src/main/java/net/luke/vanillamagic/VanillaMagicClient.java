package net.luke.vanillamagic;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.luke.vanillamagic.particle.ModParticles;
import net.luke.vanillamagic.particle.custom.GlyphParticle;

public class VanillaMagicClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(ModParticles.GLYPH_PARTICLE, GlyphParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.GLYPH_SMALL_PARTICLE, GlyphParticle.SmallFactory::new);
    }
}
