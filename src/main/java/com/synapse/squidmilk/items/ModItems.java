package com.synapse.squidmilk.items;

import com.synapse.squidmilk.SquidMilkMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SquidMilkMod.MODID);

    public static final RegistryObject<Item> SQUID_MILK_BUCKET = ITEMS.register("squid_milk_bucket",
            SquidMilkBucketItem::new);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
