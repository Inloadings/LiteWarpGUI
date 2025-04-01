package com.inloadings.liteWarpGUI.util.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public enum ConfigurationFile {
    MESSAGES("messages.yml"),
    INVENTORIES("inventories.yml");
    private final String fileName;
    private YamlConfiguration yamlConfiguration;
    private File configFile;

    ConfigurationFile(String fileName) {
        this.fileName = fileName;
    }

    public void load(JavaPlugin plugin) {
        if (configFile == null) configFile = new File(plugin.getDataFolder(), fileName);
        if (!configFile.exists()) {
            plugin.saveResource(fileName, false);
        }
        this.yamlConfiguration = YamlConfiguration.loadConfiguration(configFile);
        this.yamlConfiguration.options().parseComments(true);
    }

    public YamlConfiguration getYamlConfiguration() {
        if (yamlConfiguration == null) {
            throw new IllegalStateException("Configuration not loaded for: " + fileName);
        }
        return yamlConfiguration;
    }

}