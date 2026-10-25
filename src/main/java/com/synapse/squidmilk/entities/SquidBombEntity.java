package com.synapse.squidmilk.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SquidBombEntity extends Entity {
    public SquidBombEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public SquidBombEntity(Level pLevel, Vec3 position) {
        super(ModEntities.SQUID_BOMB.get(), pLevel);
        this.setPos(position);
        this.setInvulnerable(true);
        this.setNoGravity(true);
    }

    @Override
    public boolean shouldRender(double pX, double pY, double pZ) {
        return false;
    }

    @Override
    public boolean canCollideWith(@NotNull Entity pEntity) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        // get entities in area
        List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, AABB.ofSize(this.position(), 10.0f, 10.0f, 10.0f));

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

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
    }
}
