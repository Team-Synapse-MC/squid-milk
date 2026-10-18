package com.synapse.squidmilk.items;

import com.synapse.squidmilk.entities.SquidBombProjectile;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SquidBombItem extends TridentItem {
    private final int maxChargeTime = 40;

    public SquidBombItem() {
        super(new Item.Properties()
                .stacksTo(16)
                .rarity(Rarity.RARE)
        );
    }

    @Override
    public int getUseDuration(@NotNull ItemStack pStack) {
        return 72000;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
        pPlayer.startUsingItem(pHand);
        return InteractionResultHolder.consume(pPlayer.getItemInHand(pHand));
    }

    @Override
    public void releaseUsing(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity shooter, int pTimeLeft) {
        if (pLevel instanceof ServerLevel serverLevel) {
            int chargeTime = Mth.clamp(getUseDuration(pStack) - pTimeLeft, 0, maxChargeTime);

            SquidBombProjectile bomb = new SquidBombProjectile(serverLevel, shooter);
            bomb.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(), 0f, chargeTime / 15f, 0f);
            serverLevel.addFreshEntity(bomb);

            if (!(shooter instanceof Player player && player.getAbilities().instabuild)) {
                pStack.shrink(1);
            }
        }
    }
}
