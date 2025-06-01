//package net.deseff.elemental.item;
//
//import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityType;
//import net.minecraft.block.BlockState;
//import net.minecraft.component.type.AttributeModifierSlot;
//import net.minecraft.component.type.AttributeModifiersComponent;
//import net.minecraft.component.type.ToolComponent;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.LivingEntity;
//import net.minecraft.entity.attribute.EntityAttributeModifier;
//import net.minecraft.entity.attribute.EntityAttributes;
//import net.minecraft.entity.damage.DamageSource;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.server.world.ServerWorld;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.Vec3d;
//import net.minecraft.world.World;
//
//import java.util.List;
//
//public class LanceItem extends Item {
//
//    public LanceItem(Item.Settings settings) {super(settings);}
//
//    public static AttributeModifiersComponent createAttributeModifiers() {
//        return AttributeModifiersComponent.builder().add(EntityAttributes.ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, (double)5.0F, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).add(EntityAttributes.ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, (double)-2.4F, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).build();
//    }
//
//    public static ToolComponent createToolComponent() {
//        return new ToolComponent(List.of(), 1.0F, 2);
//    }
//
//    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
//        return !miner.isCreative();
//    }
//
//    public float getBonusAttackDamage(DamageSource damageSource) {
//        Entity entity = damageSource.getSource();
//        if (entity instanceof LivingEntity livingEntity) {
//            if (!shouldDealAdditionalDamage(livingEntity)) {
//                return 0.0F;
//            } else {
//                float h = (float)livingEntity.getVelocity().horizontalLength();
//                float i;
//
//                i = 50*h;
//
//                World world = livingEntity.getWorld();
//
//                System.out.println(i);
//
//                return 50;
//            }
//        } else {
//            return 0.0F;
//        }
//    }
//        public static boolean shouldDealAdditionalDamage(LivingEntity attacker) {
//        System.out.println((float)attacker.getVelocity().horizontalLength() > 0.15);
//        return (float)attacker.getVelocity().horizontalLength() > 0.15;
//    }
//
//    @Override
//    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
//
//        if (shouldDealAdditionalDamage(attacker)) {
//            target.damage(attacker.getServer().getWorld(), target.getDamageSources().playerAttack((PlayerEntity) attacker), (float)attacker.getVelocity().horizontalLength()*50);
//        }
//
//
//        return super.postHit(stack, target, attacker);
//    }
//}
