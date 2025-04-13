package com.inloadings.liteWarpGUI.util.commandImpl;

import com.inloadings.liteWarpGUI.LiteWarpGUI;
import com.inloadings.liteWarpGUI.util.commandImpl.Super.AbstractSuperCommand;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandManager {
    private final List<AbstractSuperCommand> commands = new ArrayList<>();
    private final CommandMap commandMap;
    private final LiteWarpGUI plugin;
    public CommandManager(LiteWarpGUI plugin) {
        this.plugin = plugin;
        this.commandMap = getCommandMap();
    }

    public void registerCommand(AbstractSuperCommand... superCommands) {
        for (AbstractSuperCommand superCommand : superCommands) {
            commandMap.register(superCommand.getName(), superCommand);
            commands.add(superCommand);
        }
    }

    public void unregisterCommands() {
        Map<String, Command> knownCommands = commandMap.getKnownCommands();

        for (AbstractSuperCommand superCommand : commands) {
            knownCommands.remove(superCommand.getName());
        }
        commands.clear();
    }

    private CommandMap getCommandMap() {
        try {
            return Bukkit.getServer().getCommandMap();
        } catch (NoSuchMethodError e) {
            try {
                Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                field.setAccessible(true);
                return (CommandMap) field.get(Bukkit.getServer());
            } catch (Exception ex) {
                plugin.getLogger().warning(
                        "Unable to access command map. Please report this bug at https://discord.gg/nAbPw9Bp"
                );
                return null;
            }
        }
    }
}