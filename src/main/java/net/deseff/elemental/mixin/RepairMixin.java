package net.deseff.elemental.mixin;

import net.deseff.elemental.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)

public abstract class RepairMixin {

    @Inject(method = {"canRepairWith"}, at = {@At("HEAD")}, cancellable = true)
    public void injectRepair(ItemStack ingredient, CallbackInfoReturnable<Boolean> cir) {
        ItemStack stack = (ItemStack)(Object)this;
        if (
                (
                        stack.isOf(Items.TRIDENT) &&
                        ingredient.isOf(ModItems.BRINE_ROD))
        ) {
            cir.setReturnValue(true);

        }
    }
}