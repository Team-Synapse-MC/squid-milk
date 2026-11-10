package com.synapse.squidmilk.items;

import com.synapse.squidmilk.SquidMilkMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SquidMilkMod.MODID);

    public static final RegistryObject<CreativeModeTab> SQUIDMILK_TAB = CREATIVE_MODE_TABS.register("squidmilk_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SQUID_MILK_BUCKET.get()))
                    .title(Component.translatable("creativetab.squidmilk_tab"))
                    .displayItems(((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.SQUID_MILK_BUCKET.get());
                        pOutput.accept(ModItems.SQUID_BOMB.get());
                    }))
                    .build()
    );

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
