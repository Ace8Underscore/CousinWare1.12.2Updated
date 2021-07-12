package io.ace.nordclient.hacks.client;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;
import io.ace.nordclient.utilz.configz.ConfigUtils;
import org.lwjgl.input.Keyboard;

public class ClickGuiHack extends Hack {
    public ClickGuiHack INSTANCE;

    public static Setting red;
    public static Setting green;
    public static Setting blue;
    public static Setting alpha;
    public static Setting descriptions;
    public static Setting noise;
    public static Setting rainbow;
    public static Setting gradiant;


    public ClickGuiHack() {
        super("ClickGUI", Category.CLIENT, "Opens the ClickGUI", 12126976);
        setBind(Keyboard.KEY_Y);
        INSTANCE = this;


        CousinWare.INSTANCE.settingsManager.rSetting(red = new Setting("Red", this, 165, 0, 255, true, "ClickGuiHackRed", true));
        CousinWare.INSTANCE.settingsManager.rSetting(green = new Setting("Green", this, 147, 0, 255, true, "ClickGuiHackGreen", true));
        CousinWare.INSTANCE.settingsManager.rSetting(blue = new Setting("Blue", this, 44, 0, 255, true, "ClickGuiHackBlue", true));
        CousinWare.INSTANCE.settingsManager.rSetting(alpha = new Setting("Alpha", this, 255, 0, 255, true, "ClickGuiHackAlpha", true));
        CousinWare.INSTANCE.settingsManager.rSetting(gradiant = new Setting("Gradiant", this, 50, 0, 255, true, "ClickGuiHackGradiant", true));
        CousinWare.INSTANCE.settingsManager.rSetting(descriptions = new Setting("Descriptions", this, true, "ClickGuiHackDescriptions", true));
        CousinWare.INSTANCE.settingsManager.rSetting(noise = new Setting("Sound", this, true, "ClickGuiHackSound", true));
        CousinWare.INSTANCE.settingsManager.rSetting(rainbow = new Setting("Rainbow", this, false, "ClickGuiHackRainbow", true));

    }

    public static Setting customFont;

    @Override
    public void onEnable() {
        mc.displayGuiScreen(CousinWare.INSTANCE.clickGui2);
        try {
            if (CousinWare.INSTANCE.fontRenderer.getFontName().equalsIgnoreCase("null")) {
                CousinWare.INSTANCE.fontRenderer.setFontName("Arial");
                CousinWare.INSTANCE.fontRenderer.setFontSize(18);
                ConfigUtils.saveFont();
                ConfigUtils.loadFont();
            }
        } catch (Exception ignored) {

//
        }
        disable();
    }

}

