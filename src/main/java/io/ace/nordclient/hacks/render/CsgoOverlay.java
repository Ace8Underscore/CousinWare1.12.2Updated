package io.ace.nordclient.hacks.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.hacks.client.ClickGuiHack;
import io.ace.nordclient.hacks.client.Core;
import io.ace.nordclient.utilz.Setting;
import io.ace.nordclient.utilz.TpsUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class CsgoOverlay extends Hack {

    Setting x;
    Setting y;
    Setting ping;
    Setting server;
    Setting fps;
    Setting tps;
    Setting name;
    Setting clientName;

    public CsgoOverlay() {
        super("CsgoOverlay", Category.RENDER, 12329109);
        CousinWare.INSTANCE.settingsManager.rSetting(x = new Setting("x", this, 959, 0, 2000, false, "CsgoOverlayX", true));
        CousinWare.INSTANCE.settingsManager.rSetting(y = new Setting("y", this, 530, 0, 2000, false, "CsgoOverlayY", true));
        CousinWare.INSTANCE.settingsManager.rSetting(ping = new Setting("Ping", this, true, "CsgoOverlayPing", true));
        CousinWare.INSTANCE.settingsManager.rSetting(fps = new Setting("Fps", this, true, "CsgoOverlayFps", true));
        CousinWare.INSTANCE.settingsManager.rSetting(tps = new Setting("Tps", this, true, "CsgoOverlayTps", true));
        CousinWare.INSTANCE.settingsManager.rSetting(name = new Setting("Name", this, true, "CsgoOverlayName", true));
        CousinWare.INSTANCE.settingsManager.rSetting(clientName = new Setting("ClientName", this, true, "CsgoOverlayClientName", true));
    }
    @SubscribeEvent
    public void onRenderWorld(RenderGameOverlayEvent.Text event) {
        Color c = new Color(ClickGuiHack.red.getValInt(), ClickGuiHack.green.getValInt(), ClickGuiHack.blue.getValInt(), 255);
        ScaledResolution sr = new ScaledResolution(mc);
        String message = "";
        if (clientName.getValBoolean()) message += ChatFormatting.WHITE + "CousinWare " + ChatFormatting.BLACK + "| ";
        if (name.getValBoolean()) message += ChatFormatting.WHITE + mc.player.getName() + " " + ChatFormatting.BLACK + "| ";
        if (!mc.isSingleplayer()) if (ping.getValBoolean()) message += ChatFormatting.WHITE + " " +((mc.getConnection() != null && mc.player != null && mc.getConnection().getPlayerInfo(mc.player.getUniqueID()) != null) ? Integer.valueOf(mc.getConnection().getPlayerInfo(mc.player.getUniqueID()).getResponseTime()) : "-1") + " ms" + " " + ChatFormatting.BLACK + "| ";
        if (fps.getValBoolean()) message += ChatFormatting.WHITE + "" + Minecraft.getDebugFPS() + " fps " + ChatFormatting.BLACK + "| ";
        if (tps.getValBoolean()) message += ChatFormatting.WHITE + " " + Math.round(TpsUtils.getTickRate() * 10) / 10.0 + " tps " + ChatFormatting.BLACK + "| ";
        drawRect(x.getValInt() - 1, sr.getScaledHeight() - y.getValInt() - 2, (float) (x.getValInt() + (message.length() * 2.84)), sr.getScaledHeight() - y.getValInt() + 10, c.getRGB());
        if (Core.customFont.getValBoolean()) CousinWare.INSTANCE.fontRenderer.drawStringWithShadow(message, x.getValInt(), sr.getScaledHeight() - y.getValInt(), c.getRGB());
    }

    public static void drawRect(float left, float top, float right, float bottom, int color) {

        float var5;

        if (left < right) {
            var5 = left;
            left = right;
            right = var5;
        }

        if (top < bottom) {
            var5 = top;
            top = bottom;
            bottom = var5;
        }

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glPushMatrix();
        glColor(color);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2d(left, bottom);
        GL11.glVertex2d(right, bottom);
        GL11.glVertex2d(right, top);
        GL11.glVertex2d(left, top);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }

    public static void glColor(int color) {

        GlStateManager.color((float) (color >> 16 & 255) / 255F, (float) (color >> 8 & 255) / 255F, (float) (color & 255) / 255F, 255F);
    }


}
