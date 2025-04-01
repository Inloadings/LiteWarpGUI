package com.inloadings.liteWarpGUI.util.config;

import lombok.Getter;

public enum ConfigPath {
    WARP_INVENTORY("warp-menu.items", Object.class),
    WARP_SIZE("warp-menu.size", Integer.class),
    WARP_TITLE("warp-menu.title", String.class),
    TELEPORT_MOVE_MESSAGE("teleport.move-message", String.class),
    TELEPORT_DELAY_MESSAGE("teleport.delay-message", String.class);

    @Getter
    private final String path;
    private final Class<?> type;

    ConfigPath(String path, Class<?> type) {
        this.path = path;
        this.type = type;
    }

    public <T> T castToType(Object value) {
        return (T) type.cast(value);
    }
}

