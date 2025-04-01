package com.inloadings.liteWarpGUI.util.config;

import com.inloadings.liteWarpGUI.LiteWarpGUI;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public class ConfigManager {

    public static void reloadConfig(ConfigurationFile configurationFile, JavaPlugin plugin) {
        configurationFile.load(plugin);
    }

    public static <T> T getValue(ConfigurationFile configurationFile, ConfigPath configPath) {
        Configuration config = configurationFile.getYamlConfiguration();
        return configPath.castToType(config.get(configPath.getPath()));
    }

    public static ConfigurationSection getSection(ConfigurationFile configurationFile, ConfigPath configPath) {
        if (configurationFile == null) {
            LiteWarpGUI.getInstance().getLogger().warning("ConfigurationFile is null when trying to get section: " + configPath.getPath());
            return null;
        }

        if (configPath == null) {
            LiteWarpGUI.getInstance().getLogger().warning("ConfigPath is null while retrieving a configuration section.");
            return null;
        }

        ConfigurationSection section = configurationFile.getYamlConfiguration().getConfigurationSection(configPath.getPath());

        if (section == null) {
            LiteWarpGUI.getInstance().getLogger().warning("Failed to find section: " + configPath.getPath() + " in " + configurationFile.name());
        }

        return section;
    }


    public static String getFormattedValue(ConfigurationFile configurationFile, ConfigPath configPath, Map<String, String> placeholders) {
        String value = getValue(configurationFile, configPath);
        return replacePlaceholders(value, placeholders);
    }

    private static String replacePlaceholders(String text, Map<String, String> placeholders) {
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            text = text.replace("%" + entry.getKey() + "%", entry.getValue());
        }
        return text;
    }
}
