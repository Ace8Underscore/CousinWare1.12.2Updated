package io.ace.nordclient.mixin.mixins;

import io.ace.nordclient.hacks.render.NoRender;
import io.ace.nordclient.managers.HackManager;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.inventory.EntityEquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerBipedArmor.class)
public abstract class MixinLayerBipedArmor {

    @Shadow protected abstract void setModelVisible(ModelBiped model);

    @Inject(method = "setModelSlotVisible", at = @At("HEAD"), cancellable = true)
    public void setArmorRender(ModelBiped p_188359_1_, EntityEquipmentSlot slotIn, CallbackInfo ci) {
        if (NoRender.armor.getValBoolean() && HackManager.getHackByName("NoRender").isEnabled()) {
            this.setModelVisible(p_188359_1_);
            switch (slotIn) {
            case HEAD:
                p_188359_1_.bipedHead.showModel = false;
                p_188359_1_.bipedHeadwear.showModel = false;
                break;
            case CHEST:
                p_188359_1_.bipedBody.showModel = false;
                p_188359_1_.bipedRightArm.showModel = false;
                p_188359_1_.bipedLeftArm.showModel = false;
                break;
            case LEGS:
                p_188359_1_.bipedBody.showModel = false;
                p_188359_1_.bipedRightLeg.showModel = false;
                p_188359_1_.bipedLeftLeg.showModel = false;
                break;
            case FEET:
                p_188359_1_.bipedRightLeg.showModel = false;
                p_188359_1_.bipedLeftLeg.showModel = false;
        }
        ci.cancel();
        }
    }
}
