package com.inloadings.liteWarpGUI.util.commandImpl;

import com.inloadings.liteWarpGUI.util.commandImpl.Super.AbstractSuperCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandManager {
    private static final List<AbstractSuperCommand> commands = new ArrayList<>();

    public static void registerCommand(AbstractSuperCommand... superCommands) {
        CommandMap commandMap = getCommandMap();

        for (AbstractSuperCommand superCommand : superCommands) {
            commandMap.register(superCommand.getName(), superCommand);
            commands.add(superCommand);
        }
    }

    public static void unregisterCommands() {
        CommandMap commandMap = getCommandMap();

        Map<String, Command> knownCommands = commandMap.getKnownCommands();

        for (AbstractSuperCommand superCommand : commands) {
            knownCommands.remove(superCommand.getName());
        }
        commands.clear();
    }

    private static CommandMap getCommandMap() {
        return Bukkit.getServer().getCommandMap();
    }
}