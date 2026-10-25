package com.synapse.squidmilk.entities;

import com.synapse.squidmilk.SquidMilkMod;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = SquidMilkMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SquidMilkMod.MODID);

    public static final RegistryObject<EntityType<SquidBombProjectile>> SQUID_BOMB_PROJECTILE =
            ENTITIES.register("squid_bomb_projectile", () ->
                    EntityType.Builder.<SquidBombProjectile>of(SquidBombProjectile::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build("squid_bomb_projectile")
            );

    public static final RegistryObject<EntityType<SquidBombEntity>> SQUID_BOMB =
            ENTITIES.register("squid_bomb", () ->
                    EntityType.Builder.<SquidBombEntity>of(SquidBombEntity::new, MobCategory.MISC)
                            .sized(10F, 10F)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build("squid_bomb")
            );


    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntities.SQUID_BOMB_PROJECTILE.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.SQUID_BOMB.get(), SquidBombRenderer::new);
    }
}
