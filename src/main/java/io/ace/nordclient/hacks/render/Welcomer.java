package io.ace.nordclient.hacks.render;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.hacks.client.Core;
import io.ace.nordclient.hwid.UID;
import io.ace.nordclient.utilz.RainbowUtil;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;


public class Welcomer extends Hack {

    Setting mode;
    Setting r;
    Setting g;
    Setting b;
    Setting rainbow;

    public Welcomer() {
        super("Welcomer", Category.RENDER,3093151);
        ArrayList<String> modes = new ArrayList();
        modes.add("Name");
        modes.add("UID");
        CousinWare.INSTANCE.settingsManager.rSetting(mode = new Setting("Mode", this, "UID", modes, "WelcomerModes", true));
        CousinWare.INSTANCE.settingsManager.rSetting(rainbow = new Setting("Rainbow", this, false, "WelcomerRainbow", true));
        CousinWare.INSTANCE.settingsManager.rSetting(r = new Setting("Red", this, 255, 0, 255, true, "WelcomerRed", !rainbow.getValBoolean()));
        CousinWare.INSTANCE.settingsManager.rSetting(g = new Setting("Green", this, 26, 0, 255, true, "WelcomerGreen", !rainbow.getValBoolean()));
        CousinWare.INSTANCE.settingsManager.rSetting(b = new Setting("Blue", this, 42, 0, 255, true, "WelcomerBlue", !rainbow.getValBoolean()));

    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        ScaledResolution sr = new ScaledResolution(mc);
        String timeMessage = "";
        long time = Calendar.getInstance().getTime().getHours();
        Color c = new Color(r.getValInt(), g.getValInt(), b.getValInt(), 255);
        if (time >= 0 && time <= 11) timeMessage = "Good Morning ";
        if (time > 11 && time <= 18) timeMessage = "Good Afternoon ";
        if (time > 18 && time < 24) timeMessage = "Good Night ";
        if (rainbow.getValBoolean()) RainbowUtil.settingRainbow(r, g, b);
        //


        if (!Core.customFont.getValBoolean()) mc.fontRenderer.drawStringWithShadow(timeMessage + (mode.getValString().equalsIgnoreCase("name") ? mc.player.getName() : UID.getUID()), (sr.getScaledWidth() - mc.fontRenderer.getStringWidth(timeMessage + (mode.getValString().equalsIgnoreCase("name") ? mc.player.getName() : UID.getUID()))) / 2, 1, c.getRGB());
        else CousinWare.INSTANCE.fontRenderer.drawStringWithShadow(timeMessage + (mode.getValString().equalsIgnoreCase("name") ? mc.player.getName() : UID.getUID()), (sr.getScaledWidth() - CousinWare.INSTANCE.fontRenderer.getStringWidth(timeMessage + (mode.getValString().equalsIgnoreCase("name") ? mc.player.getName() : UID.getUID()))) / 2, 1, c.getRGB());
    }
}
