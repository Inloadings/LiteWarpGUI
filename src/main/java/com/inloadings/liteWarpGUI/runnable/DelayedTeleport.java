package com.inloadings.liteWarpGUI.runnable;

import com.inloadings.liteWarpGUI.LiteWarpGUI;
import com.inloadings.liteWarpGUI.util.config.ConfigManager;
import com.inloadings.liteWarpGUI.util.config.ConfigPath;
import com.inloadings.liteWarpGUI.util.config.ConfigurationFile;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class DelayedTeleport extends BukkitRunnable {
    private final Player player;
    private final int IX, IY, IZ;
    private int delay;
    private final Location destination;
    private final String message;
    LiteWarpGUI plugin;
    Map<String, String> placeholders;
    private final ConfigManager configManager = ConfigManager.getInstance();
    public DelayedTeleport(Player player, Location destination, int delay, String message, LiteWarpGUI plugin) {
        this.player = player;
        Location initialLocation = player.getLocation().clone();
        IX = initialLocation.getBlockX();
        IY = initialLocation.getBlockY();
        IZ = initialLocation.getBlockZ();
        this.destination = destination;
        this.delay = delay;
        this.message = message;
        this.plugin = plugin;
        placeholders = new java.util.HashMap<>(Map.of("delay", String.valueOf(delay)));
        runTaskTimer(plugin, 0, 20);
    }

    @Override
    public void run() {
        Location location = player.getLocation().clone();
        if (location.getBlockX() != IX || location.getBlockY() != IY || location.getBlockZ() != IZ) {
            String moveMessage = configManager.getValue(ConfigurationFile.MESSAGES, ConfigPath.TELEPORT_MOVE_MESSAGE);
            player.sendMessage(moveMessage);
            cancel();
            return;
        }
        if (delay >= 1) {
            placeholders.put("delay", String.valueOf(delay));
            String delayMessage = configManager.getFormattedValue(ConfigurationFile.MESSAGES, ConfigPath.TELEPORT_DELAY_MESSAGE, placeholders);
            player.sendMessage(delayMessage);
            delay--;
            return;
        }

        player.teleportAsync(destination);
        player.sendMessage(message);
        cancel();
    }
}
