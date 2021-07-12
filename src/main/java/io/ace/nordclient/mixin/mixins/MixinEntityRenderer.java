package io.ace.nordclient.mixin.mixins;

import com.google.common.base.Predicate;
import io.ace.nordclient.hacks.misc.NoEntityTrace;
import io.ace.nordclient.hacks.render.NoRender;
import io.ace.nordclient.managers.HackManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = EntityRenderer.class, priority = 2147483647)
public class MixinEntityRenderer {

    @Shadow @Final public Minecraft mc;

    @Shadow public ItemStack itemActivationItem;

    @Shadow @Final public ItemRenderer itemRenderer;

    @Redirect(method = "getMouseOver", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getEntitiesInAABBexcluding(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;"))
    public List<Entity> getEntitiesInAABBexcluding(WorldClient worldClient, Entity entityIn, AxisAlignedBB boundingBox, Predicate predicate) {
        if (((NoEntityTrace) HackManager.getHackByName("NoEntityTrace")).enabled && mc.player.getHeldItemMainhand().getItem().equals(Items.DIAMOND_PICKAXE)) {
            return new ArrayList<>();
        } else {
            return worldClient.getEntitiesInAABBexcluding(entityIn, boundingBox, predicate);
        }
        //
    }

    @Redirect(method="renderItemActivation", at=@At(value="INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;scale(FFF)V"))
    public void renderItemActivationHook(float x, float y, float z) {
        if (this.itemActivationItem.getItem() == Items.TOTEM_OF_UNDYING) {
            if (HackManager.getHackByName("NoRender").isEnabled()) {
                GlStateManager.scale(x * NoRender.totemSize.getValDouble(), y * NoRender.totemSize.getValDouble(), z * NoRender.totemSize.getValDouble());
            } else {
                GlStateManager.scale(x, y, z);
            }
        }
    }

}
