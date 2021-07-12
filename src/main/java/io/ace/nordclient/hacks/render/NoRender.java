package io.ace.nordclient.hacks.render;

import io.ace.nordclient.CousinWare;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.utilz.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;

public class NoRender extends Hack {

    public static Setting suffocateScreen;
    public static Setting minecart;
    public static Setting fallingAni;
    public static Setting eTable;
    public static Setting fire;
    public static Setting blockBreakEffect;
    public static Setting blockMineEffect;
    public static Setting map;
    public static Setting armor;
    public static Setting crystal;
    public static Setting totemSize;

    public NoRender() {
        super("NoRender", Category.RENDER, 13466128);

        CousinWare.INSTANCE.settingsManager.rSetting(suffocateScreen = new Setting("Suffocate", this, true, "NoRenderSuffocate", true));
        CousinWare.INSTANCE.settingsManager.rSetting(minecart = new Setting("Minecart", this, false, "NoRenderMinecart", true));
        CousinWare.INSTANCE.settingsManager.rSetting(fallingAni = new Setting("FallingBlocks", this, true, "NoRenderFallingAni", true));
        CousinWare.INSTANCE.settingsManager.rSetting(eTable = new Setting("ETable", this, false, "NoRenderETable", true));
        CousinWare.INSTANCE.settingsManager.rSetting(fire = new Setting("Fire", this, false, "NoRenderFire", true));
        CousinWare.INSTANCE.settingsManager.rSetting(blockBreakEffect = new Setting("BreakParticles", this, true, "NoRenderBreakParticles", true));
        CousinWare.INSTANCE.settingsManager.rSetting(blockMineEffect = new Setting("MineParticles", this, true, "NoRenderMineParticles", true));
        CousinWare.INSTANCE.settingsManager.rSetting(map = new Setting("Map", this, false, "NoRenderMap", true));
        CousinWare.INSTANCE.settingsManager.rSetting(armor = new Setting("Armor", this, false, "NoRenderArmor", true));
        CousinWare.INSTANCE.settingsManager.rSetting(crystal = new Setting("Crystal", this, false, "NoRenderCrystal", true));
        CousinWare.INSTANCE.settingsManager.rSetting(totemSize = new Setting("TotemSize", this, .75f, 0, 1, false, "NoRenderTotemSize", true));

    }

    public void onUpdate() {
        if (crystal.getValBoolean()) {
            for (Entity entity : mc.world.loadedEntityList) {
                if (entity instanceof EntityEnderCrystal) {
                    mc.world.removeEntity(entity);
                }
            }
        }
    }
}
