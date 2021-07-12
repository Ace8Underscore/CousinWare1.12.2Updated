package io.ace.nordclient.hacks.render;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.RainbowUtil;
import io.ace.nordclient.utilz.Setting;

/**
 * @author Ace________/Ace_#1233
 */

public class BlockHighlight extends Hack {

    public static Setting r;
    public static Setting g;
    public static Setting b;
    public static Setting a;
    public static Setting legit;
    Setting rainbow;

    public BlockHighlight() {

        super("BlockHighlight", Category.RENDER, 10819438);
        CousinWare.INSTANCE.settingsManager.rSetting(rainbow = new Setting("Rainbow", this, true, "BlockHighlightRainbow", true));
        CousinWare.INSTANCE.settingsManager.rSetting(r = new Setting("Red", this, 1, 0, 255, false, "BlockHighlightRed", !rainbow.getValBoolean()));
        CousinWare.INSTANCE.settingsManager.rSetting(g = new Setting("Green", this, 1, 0, 255, false, "BlockHighlightGreen", !rainbow.getValBoolean()));
        CousinWare.INSTANCE.settingsManager.rSetting(b = new Setting("Blue", this, 1, 0, 255, false, "BlockHighlightBlue", !rainbow.getValBoolean()));
        CousinWare.INSTANCE.settingsManager.rSetting(a = new Setting("alpha", this, 1, 0, 1, false, "BlockHighlightAlpha", true));
        CousinWare.INSTANCE.settingsManager.rSetting(legit = new Setting("MinecraftRender", this, false, "BlockHighlightLegit", true));

    }
    @Override
    public void onUpdate() {
        if (rainbow.getValBoolean()) {
            RainbowUtil.settingRainbow(r, g, b);
        }
    }
}
