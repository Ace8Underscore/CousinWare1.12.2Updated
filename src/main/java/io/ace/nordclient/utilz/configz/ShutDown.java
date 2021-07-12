package io.ace.nordclient.utilz.configz;

public class ShutDown extends Thread {

    /**
     * @author Finz0
     *
     **/

    @Override
    public void run(){
        saveConfig();
    }

    public static void saveConfig(){
        ConfigUtils.saveMods();
        ConfigUtils.saveBinds();
        ConfigUtils.saveDrawn();
        ConfigUtils.savePrefix();
        ConfigUtils.saveFriends();
        ConfigUtils.saveSettingsList();
        ConfigUtils.saveFont();
        ConfigUtils.saveHuds();
        ConfigUtils.saveHudPos();
       // ConfigUtils.saveXray();

    }
}