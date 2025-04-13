package com.inloadings.liteWarpGUI;

import com.inloadings.liteWarpGUI.command.WarpCommand;
import com.inloadings.liteWarpGUI.util.commandImpl.CommandManager;
import com.inloadings.liteWarpGUI.util.config.ConfigurationFile;
import com.inloadings.liteWarpGUI.util.menu.listeners.MenuListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class LiteWarpGUI extends JavaPlugin {
    @Getter
    private static LiteWarpGUI instance;
    private CommandManager commandManager;
    @Override
    public void onEnable() {
        instance = this;
        ConfigurationFile.MESSAGES.load(this);
        ConfigurationFile.INVENTORIES.load(this);
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new MenuListener(), this);
        commandManager = new CommandManager(this);
        commandManager.registerCommand(new WarpCommand("warp", this));
    }

    @Override
    public void onDisable() {
        commandManager.unregisterCommands();
    }
}
