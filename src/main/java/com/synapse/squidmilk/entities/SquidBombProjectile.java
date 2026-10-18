package com.synapse.squidmilk.entities;

import com.synapse.squidmilk.items.ModItems;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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

        if (!this.level().isClientSide) {
            Vec3 pos = pResult.getLocation();

            spawnInk(pos);

            // get entities in area
            List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, AABB.ofSize(pResult.getLocation(), 10.0f, 10.0f, 10.0f));

            for (LivingEntity entity : entities) {
                // remove beneficial effects
                for (MobEffectInstance effect : new ArrayList<>(entity.getActiveEffects())) {
                    if (effect.getEffect().isBeneficial()) {
                        entity.removeEffect(effect.getEffect());
                    }
                }

                // inflict blindness
                entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100));
            }
        }
    }

    private void spawnInk(Vec3 pos) {
        this.playSound(this.getSquirtSound(), 1.0f, 1.0f);

        ((ServerLevel)this.level()).sendParticles(this.getInkParticle(), pos.x, pos.y, pos.z, 150, 5, 5, 5, 1);
        ((ServerLevel)this.level()).sendParticles(ParticleTypes.ASH, pos.x, pos.y, pos.z, 100, 5, 5, 5, 1);

    }

    protected ParticleOptions getInkParticle() {
        return ParticleTypes.SQUID_INK;
    }

    protected SoundEvent getSquirtSound() {
        return SoundEvents.SQUID_SQUIRT;
    }
}
