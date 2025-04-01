package com.inloadings.liteWarpGUI.util.commandImpl.sub;

import com.inloadings.liteWarpGUI.util.commandImpl.Super.AbstractSuperCommand;
import org.bukkit.command.CommandSender;

public abstract class AbstractSubCommand {
    ISubCommandInfo subCommandInfo = getClass().getAnnotation(ISubCommandInfo.class);
    String usage = subCommandInfo.usage();

    public String getRequiredPermission() {
        return subCommandInfo.perm();
    }
    public String[] getSubCommandNames() {
        return subCommandInfo.params();
    }

    public void overrideUsageMessage(String usageMessage) {
        usage = usageMessage;
    }

    public String getUsageMessage() {
        return usage;
    }

    public void attemptCommand(CommandSender sender, AbstractSuperCommand command, String[] args) {
        if (sender.hasPermission(getRequiredPermission())) onCommand(sender, command, args);
        else command.getPermissionMessage();

    }
    public abstract void onCommand(CommandSender sender, AbstractSuperCommand command, String[] args);

}
