package com.inloadings.liteWarpGUI.warp;

import com.inloadings.liteWarpGUI.LiteWarpGUI;
import com.inloadings.liteWarpGUI.runnable.DelayedTeleport;
import com.inloadings.liteWarpGUI.util.config.ConfigManager;
import com.inloadings.liteWarpGUI.util.config.ConfigPath;
import com.inloadings.liteWarpGUI.util.config.ConfigurationFile;
import com.inloadings.liteWarpGUI.util.menu.BaseMenu;
import com.inloadings.liteWarpGUI.util.menu.item.MenuButton;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.function.Consumer;

public class WarpMenu extends BaseMenu {

    public WarpMenu(LiteWarpGUI plugin, UUID playerUUID) {
        super(plugin, playerUUID);
    }

    @Override
    public int getSize() {
        return ConfigManager.getValue(ConfigurationFile.INVENTORIES, ConfigPath.WARP_SIZE);
    }

    @Override
    public String getTitle() {
        return ConfigManager.getValue(ConfigurationFile.INVENTORIES, ConfigPath.WARP_TITLE);
    }

    @Override
    public void decorateInventory() {
        ConfigurationSection configSection = ConfigManager.getSection(ConfigurationFile.INVENTORIES, ConfigPath.WARP_INVENTORY);

        if (configSection != null) {
            for (String key : configSection.getKeys(false)) {
                ConfigurationSection itemSection = configSection.getConfigurationSection(key);
                if (itemSection != null) {
                    int slot = itemSection.getInt("slot");
                    ItemStack item = createConfigurableItem(itemSection);
                    addButton(slot, MenuButton.builder()
                            .icon(item)
                            .onClick(warp(itemSection))
                            .build()
                    );
                    setItem(slot, item);
                }
            }
        }
    }

    private Consumer<Player> warp(ConfigurationSection section) {
        return player -> {
            World world = Bukkit.getWorld(section.getString("world"));
            double x = section.getDouble("x");
            double y = section.getDouble("y");
            double z = section.getDouble("z");
            float pitch = (float) section.getDouble("pitch");
            float yaw = (float) section.getDouble("yaw");
            Location location = new Location(world, x, y, z, pitch, yaw);
            new DelayedTeleport(player, location, 5, section.getString("teleport-successful-message"));
        };
    }
}
