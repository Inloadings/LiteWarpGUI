package com.inloadings.liteWarpGUI.util.menu.listeners;

import com.inloadings.liteWarpGUI.util.menu.BaseMenu;
import com.inloadings.liteWarpGUI.util.menu.item.MenuButton;
import com.inloadings.liteWarpGUI.util.menu.trackers.PlayerMenuTracker;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class MenuListener implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        UUID playerUUID = player.getUniqueId();
        BaseMenu menu = PlayerMenuTracker.getInstance().getMenu(playerUUID);
        if (menu != null) {
            PlayerMenuTracker.getInstance().removeMenu(playerUUID);
            menu.onClose(event);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory == null) return;

        Inventory inventory = event.getInventory();
        if (!clickedInventory.equals(inventory)) return;

        HumanEntity whoClicked = event.getWhoClicked();
        if (!whoClicked.getOpenInventory().getTopInventory().equals(inventory)) return;
        if (!(whoClicked instanceof Player player)) return;

        BaseMenu menu = PlayerMenuTracker.getInstance().getMenu(player.getUniqueId());
        if (menu == null) return;

        int slot = event.getSlot();
        event.setCancelled(true);

        MenuButton button = menu.getButton(slot);
        if (button != null) {
            button.onClick(player);
            return;
        }

        menu.onClick(event);
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        Player player = (Player) event.getWhoClicked();
        BaseMenu menu = PlayerMenuTracker.getInstance().getMenu(player.getUniqueId());
        if (menu != null) {
            event.setCancelled(true);
            menu.onDrag(event);
        }
    }
}
