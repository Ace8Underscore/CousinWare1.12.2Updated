package io.ace.nordclient.hacks.render;

import com.mojang.authlib.GameProfile;
import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.PacketEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.RainbowUtil;
import io.ace.nordclient.utilz.Setting;
import io.ace.nordclient.utilz.render.NordTessellator;
import io.ace.nordclient.utilz.render.TotemPopCham;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.awt.*;

public class PopCham extends Hack {

    public static Setting self;
    public static Setting rL;
    public static Setting gL;
    public static Setting bL;
    public static Setting aL;

    public static Setting rF;
    public static Setting gF;
    public static Setting bF;
    public static Setting aF;

    public static Setting fadestart;
    public static Setting fadetime;
    public static Setting onlyOneEsp;
    public static Setting rainbow;

    //
    public PopCham() {
        super("PopChams", Category.RENDER, 1954818);
        CousinWare.INSTANCE.settingsManager.rSetting(self = new Setting("Self", this, false, "PopChamsSelf", true));
        CousinWare.INSTANCE.settingsManager.rSetting(rL = new Setting("RedLine", this, 255, 0, 255, true, "PopChamsRedLine", true));
        CousinWare.INSTANCE.settingsManager.rSetting(gL = new Setting("GreenLine", this, 26, 0, 255, true, "PopChamsGreenLine", true));
        CousinWare.INSTANCE.settingsManager.rSetting(bL = new Setting("BlueLine", this, 42, 0, 255, true, "PopChamsBlueLine", true));
        CousinWare.INSTANCE.settingsManager.rSetting(aL = new Setting("AlphaLine", this, 42, 0, 255, true, "PopChamsAlphaLine", true));

        CousinWare.INSTANCE.settingsManager.rSetting(rF = new Setting("RedFill", this, 255, 0, 255, true, "PopChamsRedFill", true));
        CousinWare.INSTANCE.settingsManager.rSetting(gF = new Setting("BlueFill", this, 26, 0, 255, true, "PopChamsGreenFill", true));
        CousinWare.INSTANCE.settingsManager.rSetting(bF = new Setting("GreenFill", this, 42, 0, 255, true, "PopChamsBlueFill", true));
        CousinWare.INSTANCE.settingsManager.rSetting(aF = new Setting("AlphaFill", this, 42, 0, 255, true, "PopChamsAlphaFill", true));

        CousinWare.INSTANCE.settingsManager.rSetting(fadestart = new Setting("FadeStart", this, 200, 0, 3000, true, "PopChamsFadeStart", true));
        CousinWare.INSTANCE.settingsManager.rSetting(fadetime = new Setting("FadeSpeed", this, .5, 0, 2, false, "PopChamsFadeTime", true));
        CousinWare.INSTANCE.settingsManager.rSetting(onlyOneEsp = new Setting("OnlyOneEsp", this, true, "PopChamsOnlyOneEsp", true));
        //CousinWare.INSTANCE.settingsManager.rSetting(aLFadeTo = new Setting("LineFadeTo", this, 0, 100, 255, false, "PopChamsFadeTime", true)););
        CousinWare.INSTANCE.settingsManager.rSetting(rainbow = new Setting("Rainbow", this, false, "PopChamsRainbow", true));

    }

    EntityOtherPlayerMP player;
    ModelPlayer playerModel;
    Long startTime;
    double alphaFill;
    double alphaLine;
    //

    @Listener
    public void onUpdate(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketEntityStatus) {
            final SPacketEntityStatus packet = (SPacketEntityStatus) event.getPacket();
            if (packet.getOpCode() == 35 && packet.getEntity(PopCham.mc.world) != null && (self.getValBoolean() || packet.getEntity(PopCham.mc.world).getEntityId() != PopCham.mc.player.getEntityId())) {
                final GameProfile profile = new GameProfile(mc.player.getUniqueID(), "");
                (this.player = new EntityOtherPlayerMP(mc.world, profile)).copyLocationAndAnglesFrom(packet.getEntity(mc.world));
                this.playerModel = new ModelPlayer(0.0f, false);
                this.startTime = System.currentTimeMillis();
                playerModel.bipedHead.showModel = false;
                playerModel.bipedBody.showModel = false;
                playerModel.bipedLeftArmwear.showModel = false;
                playerModel.bipedLeftLegwear.showModel = false;
                playerModel.bipedRightArmwear.showModel = false;
                playerModel.bipedRightLegwear.showModel = false;

                alphaFill = aF.getValInt();
                alphaLine = aL.getValInt();
                if (!onlyOneEsp.getValBoolean()) {
                    TotemPopCham p = new TotemPopCham(player, playerModel, startTime, alphaFill, alphaLine);
                }


            }
        }
    }

    @Override
    public void onUpdate() {
        if (rainbow.getValBoolean()) {
            RainbowUtil.settingRainbow(rF, gF, bF);
            RainbowUtil.settingRainbow(rL, gL, bL);
            //
        }
    }


    @SubscribeEvent
    public void onRenderWorld(final RenderWorldLastEvent event) {
        if (onlyOneEsp.getValBoolean()) {
            if (player == null || mc.world == null || mc.player == null) {
                return;
            }
            GL11.glLineWidth(1.0f);
            Color lineColorS = new Color(rL.getValInt(), bL.getValInt(), gL.getValInt(), aL.getValInt());
            Color fillColorS = new Color(rF.getValInt(), bF.getValInt(), gF.getValInt(), aF.getValInt());
            int lineA = lineColorS.getAlpha();
            int fillA = (fillColorS).getAlpha();
            final long time = System.currentTimeMillis() - this.startTime - ((Number) fadestart.getValInt()).longValue();
            if (System.currentTimeMillis() - this.startTime > ((Number) fadestart.getValInt()).longValue()) {
                double normal = this.normalize((double) time, 0.0, ((Number) fadetime.getValDouble()).doubleValue());
                normal = MathHelper.clamp(normal, 0.0, 1.0);
                normal = -normal + 1.0;
                lineA *= (int) normal;
                fillA *= (int) normal;
            }
            Color lineColor = newAlpha(lineColorS, lineA);
            Color fillColor = newAlpha(fillColorS, fillA);
            if (this.player != null && this.playerModel != null) {
                NordTessellator.prepareGL();
                GL11.glPushAttrib(1048575);
                GL11.glEnable(2881);
                GL11.glEnable(2848);
                if (alphaFill > 1) alphaFill -= fadetime.getValDouble();
                Color fillFinal = new Color(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), (int) alphaFill);

                if (alphaLine > 1) alphaLine -= fadetime.getValDouble();
                Color outlineFinal = new Color(lineColor.getRed(), lineColor.getGreen(), lineColor.getBlue(), (int) alphaLine);
                glColor(fillFinal);
                GL11.glPolygonMode(1032, 6914);
                renderEntity(this.player, this.playerModel, this.player.limbSwing, this.player.limbSwingAmount, (float) this.player.ticksExisted, this.player.rotationYawHead, this.player.rotationPitch, 1);
                glColor(outlineFinal);
                GL11.glPolygonMode(1032, 6913);
                renderEntity(this.player, this.playerModel, this.player.limbSwing, this.player.limbSwingAmount, (float) this.player.ticksExisted, player.rotationYawHead, this.player.rotationPitch, 1);
                GL11.glPolygonMode(1032, 6914);
                GL11.glPopAttrib();
                NordTessellator.releaseGL();
            }
        }
    }

    double normalize(final double value, final double min, final double max) {
        return (value - min) / (max - min);
    }

    public static void renderEntity(final EntityLivingBase entity, final ModelBase modelBase, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        if (mc.getRenderManager() == null) {
            return;
        }
        final float partialTicks = mc.getRenderPartialTicks();
        final double x = entity.posX - mc.getRenderManager().viewerPosX;
        double y = entity.posY - mc.getRenderManager().viewerPosY;
        final double z = entity.posZ - mc.getRenderManager().viewerPosZ;
        GlStateManager.pushMatrix();
        if (entity.isSneaking()) {
            y -= 0.125;
        }
        final float interpolateRotation = interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
        final float interpolateRotation2 = interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
        final float rotationInterp = interpolateRotation2 - interpolateRotation;
        final float renderPitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
        renderLivingAt(x, y, z);
        final float f8 = handleRotationFloat(entity, partialTicks);
        prepareRotations(entity);
        final float f9 = prepareScale(entity, scale);
        GlStateManager.enableAlpha();
        modelBase.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);
        modelBase.setRotationAngles(limbSwing, limbSwingAmount, f8, entity.rotationYaw, entity.rotationPitch, f9, entity);
        modelBase.render(entity, limbSwing, limbSwingAmount, f8, entity.rotationYaw, entity.rotationPitch, f9);
        GlStateManager.popMatrix();
    }

    public static void prepareTranslate(final EntityLivingBase entityIn, final double x, final double y, final double z) {
        renderLivingAt(x - mc.getRenderManager().viewerPosX, y - mc.getRenderManager().viewerPosY, z - mc.getRenderManager().viewerPosZ);
    }

    public static void renderLivingAt(final double x, final double y, final double z) {
        GlStateManager.translate((float)x, (float)y, (float)z);
    }

    public static float prepareScale(final EntityLivingBase entity, final float scale) {
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0f, -1.0f, 1.0f);
        final double widthX = entity.getRenderBoundingBox().maxX - entity.getRenderBoundingBox().minX;
        final double widthZ = entity.getRenderBoundingBox().maxZ - entity.getRenderBoundingBox().minZ;
        GlStateManager.scale(scale + widthX, scale * entity.height, scale + widthZ);
        final float f = 0.0625f;
        GlStateManager.translate(0.0f, -1.501f, 0.0f);
        return f;
    }

    public static void prepareRotations(final EntityLivingBase entityLivingBase) {
        GlStateManager.rotate(180.0f - entityLivingBase.rotationYaw, 0.0f, 1.0f, 0.0f);
    }

    public static float interpolateRotation(final float prevYawOffset, final float yawOffset, final float partialTicks) {
        float f;
        for (f = yawOffset - prevYawOffset; f < -180.0f; f += 360.0f) {}
        while (f >= 180.0f) {
            f -= 360.0f;
        }
        return prevYawOffset + partialTicks * f;
    }

    public static Color newAlpha(final Color color, final int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    public static void glColor(final Color color) {
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }

    public static float handleRotationFloat(final EntityLivingBase livingBase, final float partialTicks) {
        return 0.0f;
    }



}
