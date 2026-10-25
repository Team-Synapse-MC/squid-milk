package com.synapse.squidmilk.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.FastColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class SquidBombParticle extends TextureSheetParticle {
    private final SpriteSet sprites;


    protected SquidBombParticle(ClientLevel pLevel, double pX, double pY, double pZ, int pPackedColor, SpriteSet sprites) {
        super(pLevel, pX, pY, pZ);
        this.sprites = sprites;
        this.quadSize = 0.5F;
        this.setAlpha(1.0F);
        this.lifetime = 240;
        this.setColor((float) FastColor.ARGB32.red(pPackedColor), (float)FastColor.ARGB32.green(pPackedColor), (float)FastColor.ARGB32.blue(pPackedColor));
        this.setSpriteFromAge(sprites);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.removed) return;

        this.setSpriteFromAge(this.sprites);

        // fade away
        float interval = (float) (7 * this.lifetime) / 8;
        if (this.age > interval) {
            this.setAlpha(1.0F - ((float)this.age - lifetime) / ((float) this.lifetime / 8));
        }

        this.xd += (this.random.nextDouble() - 0.5) * 0.01;
        this.yd += (this.random.nextDouble() - 0.5) * 0.01;
        this.zd += (this.random.nextDouble() - 0.5) * 0.01;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Factory(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public @Nullable Particle createParticle(@NotNull SimpleParticleType pType, @NotNull ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new SquidBombParticle(pLevel, pX, pY, pZ, FastColor.ARGB32.color(255, 255, 255, 255), this.sprites);
        }
    }
}
