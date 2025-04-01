package com.inloadings.liteWarpGUI.util.menu.trackers;

import com.inloadings.liteWarpGUI.util.menu.BaseMenu;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerMenuTracker {

    @Getter
    private static final PlayerMenuTracker instance = new PlayerMenuTracker();

    private final Map<UUID, BaseMenu> menus = new HashMap<>();

    public void addMenu(UUID playerUUID, BaseMenu menu) {
        this.menus.put(playerUUID, menu);
    }

    public void removeMenu(UUID playerUUID) {
        this.menus.remove(playerUUID);
    }

    public BaseMenu getMenu(UUID playerUUID) {
        return menus.get(playerUUID);
    }
}
