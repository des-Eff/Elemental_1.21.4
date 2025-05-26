package net.deseff.elemental.item;

import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WaterChargeItem extends Item {

    public WaterChargeItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        float f;
        int tideTicks;
        if (user.isWet()) {
            f = 2.25F;
            tideTicks = 25;
        } else {
            f = 1.5F;
            tideTicks = 10;
        }
        float g = user.getYaw();
        float h = user.getPitch();
        float j = -MathHelper.sin(g * (float) (Math.PI / 180.0)) * MathHelper.cos(h * (float) (Math.PI / 180.0));
        float k = -MathHelper.sin(h * (float) (Math.PI / 180.0));
        float l = MathHelper.cos(g * (float) (Math.PI / 180.0)) * MathHelper.cos(h * (float) (Math.PI / 180.0));
        float m = MathHelper.sqrt(j * j + k * k + l * l);
        j *= f / m;
        k *= f / m;
        l *= f / m;

        user.addVelocity(j, k, l);
        user.useRiptide(tideTicks, 1.0F, itemStack);
            if (user.isOnGround()) {
                float n = 1.1999999F;
                user.move(MovementType.SELF, new Vec3d(0.0, n, 0.0));
            }
        world.playSound(
                null,
                user.getX(),
                user.getY(),
                user.getZ(),
                SoundEvents.BLOCK_BUBBLE_COLUMN_UPWARDS_INSIDE,
                SoundCategory.NEUTRAL,
                0.5F,
                1.0F / (world.getRandom().nextFloat() * 0.1F)
        );
        if (user.isInsideWaterOrBubbleColumn()) {
            user.setAir(user.getAir()+60);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        itemStack.decrementUnlessCreative(1, user);
        return ActionResult.SUCCESS;
    }
}