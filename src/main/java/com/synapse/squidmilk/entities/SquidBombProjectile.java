package com.synapse.squidmilk.entities;

import com.synapse.squidmilk.items.ModItems;
import com.synapse.squidmilk.particles.ModParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class SquidBombProjectile extends ThrowableItemProjectile {
    public SquidBombProjectile(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public SquidBombProjectile(Level level, LivingEntity thrower) {
        super(ModEntities.SQUID_BOMB_PROJECTILE.get(), thrower, level);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ModItems.SQUID_BOMB.get();
    }

    @Override
    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);

        if (this.level() instanceof ServerLevel serverLevel) {
            Vec3 pos = pResult.getLocation();

            spawnInk(pos);

            SquidBombEntity bomb = new SquidBombEntity(serverLevel, pos);
            serverLevel.addFreshEntity(bomb);
        }
    }

    private void spawnInk(Vec3 pos) {
        this.playSound(this.getSquirtSound(), 1.0f, 1.0f);

        ((ServerLevel)this.level()).sendParticles(this.getInkParticle(), pos.x, pos.y, pos.z, 50, 1, 1, 1, 0);

        int count = 200;
        double radius = 10.0;

        for (int i = 0; i < count; i++) {
            double x = this.random.nextDouble() * 2 - 1;
            double y = this.random.nextDouble() * 2 - 1;
            double z = this.random.nextDouble() * 2 - 1;

            double length = Math.sqrt(x*x + y*y + z*z);

            if (length > 1 || length == 0) {
                i--;
                continue;
            }

            double px = pos.x + x*radius;
            double py = pos.y + y*radius;
            double pz = pos.z + z*radius;

            ((ServerLevel)this.level()).sendParticles(this.getInkParticle(), px, py, pz, 1, 0, 0, 0, 0);
        }
    }

    protected ParticleOptions getInkParticle() {
        return ModParticles.SQUID_BOMB.get();
    }

    protected SoundEvent getSquirtSound() {
        return SoundEvents.SQUID_SQUIRT;
    }
}
