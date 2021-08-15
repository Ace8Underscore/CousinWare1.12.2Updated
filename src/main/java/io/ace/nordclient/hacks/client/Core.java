package io.ace.nordclient.hacks.client;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;


public class Core extends Hack {

    public static Setting saturation;
    public static Setting brightness;
    public static Setting speed;
    public static Setting customFont;
    public static Setting antiAlias;
    public static Setting fractionalMetrics;
    public static Setting essentials;
    public static Setting capes;

    int i = 0;
    int j = 0;
    boolean switchingSlot = false;
    int delaySlot = 0;

    public Core() {
        super("Core", Category.CLIENT, 10989199);

        CousinWare.INSTANCE.settingsManager.rSetting(saturation = new Setting("Saturation", this, .8, 0, 1,false,  "ColorsSaturation", true));
        CousinWare.INSTANCE.settingsManager.rSetting(brightness = new Setting("Brightness", this, .8, 0, 1,false,  "ColorsBrightness", true));
        CousinWare.INSTANCE.settingsManager.rSetting(speed = new Setting("Speed", this, 1, 0, 5,false,  "ColorsSpeed", true));
        CousinWare.INSTANCE.settingsManager.rSetting(customFont = new Setting("CustomFont", this, true, "CoreCustomFont", true));
        CousinWare.INSTANCE.settingsManager.rSetting(antiAlias = new Setting("AntiAlias", this, true, "CoreAntiAlias", true));
        CousinWare.INSTANCE.settingsManager.rSetting(fractionalMetrics = new Setting("FractionalMetrics", this, true, "CoreFractionalMetrics", true));
        CousinWare.INSTANCE.settingsManager.rSetting(capes = new Setting("Capes", this, true, "CoreCapes", true));
        CousinWare.INSTANCE.settingsManager.rSetting(essentials = new Setting("Essentials", this, false, "CoreEssentials", true));

    }

}

