package io.ace.nordclient.command.commands;

import io.ace.nordclient.command.Command;
import io.ace.nordclient.utilz.configz.ConfigUtils;
import io.ace.nordclient.utilz.configz.ShutDown;

public class SaveConfig extends Command {

    @Override
    public String[] getClientAlias() {
        return new String[]{"SaveConfig"};
    }

    @Override
    public String getClientSyntax() {
        return "SaveConfig";
    }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
        if (args[0].length() > 0) {
            ConfigUtils.saveConfigFolder(args[0].toLowerCase());
            ConfigUtils.loadConfigFolder();
            ShutDown.saveConfig();
            ConfigUtils.loadAll();

        }
    }
}
