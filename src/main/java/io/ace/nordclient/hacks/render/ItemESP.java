package io.ace.nordclient.hacks.render;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.event.RenderEvent;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.hacks.client.Core;
import io.ace.nordclient.mixin.accessor.IMinecraft;
import io.ace.nordclient.mixin.accessor.IRenderManager;
import io.ace.nordclient.utilz.RainbowUtil;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;

/**
 * @author Ace________/Ace_#1233
 */

public class ItemESP extends Hack {

    final FontRenderer fontRenderer = mc.fontRenderer;

    Setting range;
    Setting scale;
    Setting renderEsp;
    Setting r;
    Setting g;
    Setting b;
    Setting rainbow;

    public ItemESP() {
        super("ItemESP", Category.RENDER, 10955851);
        CousinWare.INSTANCE.settingsManager.rSetting(range = new Setting("Range", this, 100, 0, 500, false, "ItemESPRange", true));
        CousinWare.INSTANCE.settingsManager.rSetting(scale = new Setting("Scale", this, 0.03, 0.01, 0.09, false, "ItemEspScale", true));
        CousinWare.INSTANCE.settingsManager.rSetting(renderEsp = new Setting("RenderEsp", this, true, "ItemEspRenderEsp", true));
        CousinWare.INSTANCE.settingsManager.rSetting(rainbow = new Setting("Rainbow", this, true, "ItemESPRainbow", true));
        CousinWare.INSTANCE.settingsManager.rSetting(r = new Setting("Red", this, 1, 0, 255, false, "ItemESPRed", !rainbow.getValBoolean()));
        CousinWare.INSTANCE.settingsManager.rSetting(g = new Setting("Green", this, 1, 0, 255, false, "ItemESPGreen", !rainbow.getValBoolean()));
        CousinWare.INSTANCE.settingsManager.rSetting(b = new Setting("Blue", this, 1, 0, 255, false, "ItemESPBlue", !rainbow.getValBoolean()));
    }

    @Override
    public void onWorldRender(RenderEvent event) {

        for (Entity e : mc.world.loadedEntityList) {
            if (mc.player.getDistance(e.posX, e.posY, e.posZ) <= range.getValDouble()) {
                if (e instanceof EntityItem) {
                    final double eX = e.lastTickPosX + (e.posX - e.lastTickPosX) * ((IMinecraft) mc).getTimer().renderPartialTicks - ((IRenderManager)mc.getRenderManager()).getRenderPosX();
                    final double eY = e.lastTickPosY + (e.posY - e.lastTickPosY) * ((IMinecraft) mc).getTimer().renderPartialTicks - ((IRenderManager)mc.getRenderManager()).getRenderPosY();
                    final double eZ = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * ((IMinecraft) mc).getTimer().renderPartialTicks - ((IRenderManager)mc.getRenderManager()).getRenderPosZ();
                    if (!e.getName().startsWith("Body #")) {
                        renderName((EntityItem) e, eX, eY, eZ);
                    }
                }

            }
        }
    }

    public void renderEsp(EntityItem entityItem, double x, double y, double z) {

    }

    public void renderName(EntityItem entityItem, double x, double y, double z) {
        GlStateManager.pushMatrix();
        String name = entityItem.getItem().getDisplayName() + " x" + entityItem.getItem().getCount();
        final float distance = mc.player.getDistance(entityItem);
        final float var14 = (float) (((distance / 5.0f <= 2.0f) ? 2.0f : (distance / 5.0f * (this.scale.getValDouble() * 10.0f + 1.0f))) * 2.5f * (this.scale.getValDouble() / 10.0f));
        final float var15 = (float) (this.scale.getValDouble() * this.getSize(entityItem));
        GL11.glTranslated((float)x, (float)y +
                // height
                .5
                + ((distance / 5.0f > 2.0f) ? (distance / 12.0f - 0.7) : 0.0), (float)z);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(mc.getRenderManager().playerViewX, (mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
        GL11.glScalef(-var14, -var14, var14);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GL11.glDisable(2929);
        int width;
        if (Core.customFont.getValBoolean()) {
            width = CousinWare.INSTANCE.fontRenderer.getStringWidth(name) / 2 + 1;
        }
        else {
            width = fontRenderer.getStringWidth(name) / 2;
        }

        if (Core.customFont.getValBoolean()) {
            CousinWare.INSTANCE.fontRenderer.drawStringWithShadow(name, -width, 10.0, -1);
        }
        else {
            fontRenderer.drawStringWithShadow(name, (float)(-width), 11.0f, -1);
        }
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL_BLEND);

        GlStateManager.resetColor();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }

    public float getSize(final EntityItem player) {
        final ScaledResolution scaledRes = new ScaledResolution(mc);
        final double twoDscale = scaledRes.getScaleFactor() / Math.pow(scaledRes.getScaleFactor(), 2.0);
        return (float)twoDscale + mc.player.getDistance(player) / 7.0f;
    }

    @Override
    public void onUpdate() {
        if (rainbow.getValBoolean()) {
            RainbowUtil.settingRainbow(r, g, b);
        }
    }
}
