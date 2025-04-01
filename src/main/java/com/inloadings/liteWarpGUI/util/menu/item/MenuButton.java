package com.inloadings.liteWarpGUI.util.menu.item;

import lombok.Builder;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

@Getter
@Builder
public class MenuButton {

    private final ItemStack icon;
    private final Consumer<Player> onClick;

    public void onClick(Player player) {
        onClick.accept(player);
    }
}
