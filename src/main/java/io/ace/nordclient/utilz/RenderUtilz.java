package io.ace.nordclient.utilz;

import io.ace.nordclient.CousinWare;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

public class RenderUtilz {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void drawText(final float x, final float y, final float z, final String text) {
        GlStateManager.pushMatrix();
        glBillboardDistanceScaled(x + 0.5f, y + 0.5f, z + 0.5f, mc.player, 1.0f);
        GlStateManager.disableDepth();
        GlStateManager.translate(-(mc.fontRenderer.getStringWidth(text) / 2.0), 0.0, 0.0);
        mc.fontRenderer.drawStringWithShadow(text, 0.0f, 0.0f, -5592406);
        GlStateManager.popMatrix();
    } //


    public static void drawTextCustom(final float x, final float y, final float z, final String text) {
        GlStateManager.pushMatrix();
        glBillboardDistanceScaled(x + 0.5f, y + 0.5f, z + 0.5f, mc.player, 1.0f);
        GlStateManager.disableDepth();
        GlStateManager.translate(-(CousinWare.INSTANCE.fontRenderer.getStringWidth(text) / 2.0), 0.0, 0.0);
        CousinWare.INSTANCE.fontRenderer.drawStringWithShadow(text, 0.0f, 0.0f, -5592406);
        GlStateManager.popMatrix();
    }

    public static void glBillboardDistanceScaled(final float x, final float y, final float z, final EntityPlayer player, final float scale) {
        glBillboard(x, y, z);
        final int distance = (int) player.getDistance(x, y, z);
        float scaleDistance = distance / 2.0f / (2.0f + (2.0f - scale));
        if (scaleDistance < 1.0f) {
            scaleDistance = 1.0f;
        }
        GlStateManager.scale(scaleDistance, scaleDistance, scaleDistance);
    }

    public static void glBillboard(final float x, final float y, final float z) {
        final float scale = 0.02666667f;
        GlStateManager.translate(x - mc.getRenderManager().renderPosX, y - mc.getRenderManager().renderPosY, z - mc.getRenderManager().renderPosZ);
        GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-mc.player.rotationYaw, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(mc.player.rotationPitch, (mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-scale, -scale, scale);
    }

    public static void drawGradientRect(float left, float top, float right, float bottom, int startColor, int endColor, boolean hovered) {
        if (hovered) {
            startColor = shadeColour(startColor, -20);
            endColor = shadeColour(endColor, -20);
        }
        float c = (float) (startColor >> 24 & 255) / 255.0F;
        float c1 = (float) (startColor >> 16 & 255) / 255.0F;
        float c2 = (float) (startColor >> 8 & 255) / 255.0F;
        float c3 = (float) (startColor & 255) / 255.0F;
        float c4 = (float) (endColor >> 24 & 255) / 255.0F;
        float c5 = (float) (endColor >> 16 & 255) / 255.0F;
        float c6 = (float) (endColor >> 8 & 255) / 255.0F;
        float c7 = (float) (endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(right, top, 0).color(c1, c2, c3, c).endVertex();
        bufferbuilder.pos(left, top, 0).color(c1, c2, c3, c).endVertex();
        bufferbuilder.pos(left, bottom, 0).color(c5, c6, c7, c4).endVertex();
        bufferbuilder.pos(right, bottom, 0).color(c5, c6, c7, c4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawGlBackground(float left, float top, float right, float bottom, int startColor, int endColor, boolean hovered) {
        if (hovered) {
            startColor = shadeColour(startColor, -20);
            endColor = shadeColour(endColor, -20);
        }
        float c = (float) (startColor >> 24 & 255) / 255.0F;
        float c1 = (float) (startColor >> 16 & 255) / 255.0F;
        float c2 = (float) (startColor >> 8 & 255) / 255.0F;
        float c3 = (float) (startColor & 255) / 255.0F;
        float c4 = (float) (endColor >> 24 & 255) / 255.0F;
        float c5 = (float) (endColor >> 16 & 255) / 255.0F;
        float c6 = (float) (endColor >> 8 & 255) / 255.0F;
        float c7 = (float) (endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(right, top, 0).color(c1, c2, c3, c).endVertex();
        bufferbuilder.pos(left, top, 0).color(c1, c2, c3, c).endVertex();
        bufferbuilder.pos(left, bottom, 0).color(c5, c6, c7, c4).endVertex();
        bufferbuilder.pos(right, bottom, 0).color(c5, c6, c7, c4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static int shadeColour(int color, int precent) {
        int r = (((color & 0xFF0000) >> 16) * (100 + precent) / 100);
        int g = (((color & 0xFF00) >> 8) * (100 + precent) / 100);
        int b = ((color & 0xFF) * (100 + precent) / 100);
        return new Color(r,g,b).hashCode();
    }

}
