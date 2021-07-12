package io.ace.nordclient.mixin.mixins;

import io.ace.nordclient.hacks.render.PopRender;
import io.ace.nordclient.managers.HackManager;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(value={RenderLivingBase.class})
public abstract class MixinRenderLivingBase<T extends EntityLivingBase>
extends Render<T> {
    //private static final ResourceLocation glint = new ResourceLocation("textures/shinePopRender.png");

    public MixinRenderLivingBase(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
        super(renderManagerIn);
    }

    @Redirect(method={"renderModel"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    private void renderModelHook(ModelBase modelBase, Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        Color visibleColor;
        boolean cancel = false;
        if (HackManager.getHackByName("PopRender").isEnabled() && entityIn instanceof EntityPlayer && PopRender.colored.getValBoolean() && !PopRender.textured.getValBoolean()) {
            if (!PopRender.textured.getValBoolean()) {
                GL11.glPushAttrib(1048575);
                GL11.glDisable(3008);
                GL11.glDisable(3553);
                GL11.glDisable(2896);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glLineWidth(1.5f);
                GL11.glEnable(2960);
                if (PopRender.xqz.getValBoolean()) {
                    Color visibleColor2 = new Color(PopRender.red.getValInt(), PopRender.green.getValInt(), PopRender.blue.getValInt(), PopRender.alpha.getValInt());
                    GL11.glDisable(2929);
                    GL11.glDepthMask(false);
                    GL11.glEnable(10754);
                    GL11.glColor4f((float)visibleColor2.getRed() / 255.0f, (float)visibleColor2.getGreen() / 255.0f, (float)visibleColor2.getBlue() / 255.0f, (float)PopRender.alpha.getValInt() / 255.0f);
                    modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                    GL11.glEnable(2929);
                    GL11.glDepthMask(true);
                    GL11.glColor4f((float)visibleColor2.getRed() / 255.0f, (float)visibleColor2.getGreen() / 255.0f, (float)visibleColor2.getBlue() / 255.0f, (float)PopRender.alpha.getValInt() / 255.0f);
                    modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                } else {
                    visibleColor = new Color(PopRender.red.getValInt(), PopRender.green.getValInt(), PopRender.blue.getValInt(), PopRender.alpha.getValInt());
                    GL11.glDisable(2929);
                    GL11.glDepthMask(false);
                    GL11.glEnable(10754);
                    GL11.glColor4f((float)visibleColor.getRed() / 255.0f, (float)visibleColor.getGreen() / 255.0f, (float)visibleColor.getBlue() / 255.0f, (float)PopRender.alpha.getValInt() / 255.0f);
                    modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                    GL11.glEnable(2929);
                    GL11.glDepthMask(true);
                }
                GL11.glEnable(3042);
                GL11.glEnable(2896);
                GL11.glEnable(3553);
                GL11.glEnable(3008);
                GL11.glPopAttrib();
            }
        } else if (PopRender.textured.getValBoolean()) {
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            visibleColor = new Color(PopRender.red.getValInt(), PopRender.green.getValInt(), PopRender.blue.getValInt(), PopRender.alpha.getValInt());
            GL11.glColor4f((float)visibleColor.getRed() / 255.0f, (float)visibleColor.getGreen() / 255.0f, (float)visibleColor.getBlue() / 255.0f, (float)PopRender.alpha.getValInt() / 255.0f);
            modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
        } else if (!cancel) {
            modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    @Inject(method={"doRender"}, at={@At(value="HEAD")})
    public void doRenderPre(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo info) {
        if (HackManager.getHackByName("PopRender").isEnabled() && !PopRender.colored.getValBoolean() && entity != null) {
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0f, -1100000.0f);
        }
    }

    @Inject(method={"doRender"}, at={@At(value="RETURN")})
    public void doRenderPost(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo info) {
        if (HackManager.getHackByName("PopRender").isEnabled() && !PopRender.colored.getValBoolean() && entity != null) {
            GL11.glPolygonOffset(1.0f, 1000000.0f);
            GL11.glDisable(32823);
        }
    }
}
