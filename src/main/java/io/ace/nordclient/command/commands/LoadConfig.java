package io.ace.nordclient.command.commands;

import io.ace.nordclient.command.Command;
import io.ace.nordclient.hacks.Hack;
import io.ace.nordclient.managers.HackManager;
import io.ace.nordclient.utilz.configz.ConfigUtils;

public class LoadConfig extends Command {

    @Override
    public String[] getClientAlias() {
        return new String[]{"LoadConfig"};
    }

    @Override
    public String getClientSyntax() {
        return "LoadConfig";
    }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        ConfigUtils configUtils = new ConfigUtils();
        if (args[0].length() > 0) {
            for (Hack hack : HackManager.getHacks()) {
                if (hack.isEnabled()) hack.disable();
            }
            ConfigUtils.saveConfigFolder(args[0].toLowerCase());
            ConfigUtils.loadConfigFolder();
            ConfigUtils.loadAll();

        }
    }
}
