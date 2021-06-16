package io.ace.nordclient.command.commands;

import io.ace.nordclient.command.Command;
import io.ace.nordclient.utilz.configz.ConfigUtils;

public class ConfigFolder extends Command {

    @Override
    public String[] getClientAlias() {
        return new String[]{"ConfigFolder"};
    }

    @Override
    public String getClientSyntax() {
        return "ConfigFolder";
    }

    @Override
    public void onClientCommand(String command, String[] args) throws Exception {
    Command.sendClientSideMessage(ConfigUtils.configFolder);

    }
}
