package com.inloadings.liteWarpGUI.util.menu;

import com.inloadings.liteWarpGUI.LiteWarpGUI;
import com.inloadings.liteWarpGUI.util.ItemBuilder;
import com.inloadings.liteWarpGUI.util.menu.item.MenuButton;
import com.inloadings.liteWarpGUI.util.menu.trackers.PlayerMenuTracker;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

public abstract class BaseMenu implements IMenu {
    private final Map<Integer, MenuButton> buttons = new HashMap<>();

    protected final LiteWarpGUI plugin;
    protected final UUID playerUUID;
    @Getter
    protected Inventory inventory;

    public BaseMenu(LiteWarpGUI plugin, UUID playerUUID) {
        this.playerUUID = playerUUID;
        this.plugin = plugin;
    }

    public void openInventory() {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player == null) {
            return;
        }

        this.inventory = Bukkit.createInventory(null, getSize(), Component.text(getTitle()));
        decorateInventory();
        player.openInventory(inventory);
        PlayerMenuTracker.getInstance().addMenu(playerUUID, this);
    }

    public void onClose(InventoryCloseEvent event) {
    }

    public void onClick(InventoryClickEvent event) {
    }

    public void onDrag(InventoryDragEvent event) {
    }

    protected void setItem(int slot, ItemStack itemStack) {
        inventory.setItem(slot, itemStack);
    }

    protected void setItems(ItemStack itemStack, int... slots) {
        IntStream.of(slots).forEach(slot -> inventory.setItem(slot, itemStack));
    }

    protected void addButton(int slot, MenuButton button) {
        inventory.setItem(slot, button.getIcon());
        buttons.put(slot, button);
    }


    public MenuButton getButton(int slot) {
        return buttons.get(slot);
    }

    protected ItemStack createConfigurableItem(ConfigurationSection section) {
        System.out.println(section.getString("material"));
        Material material = Material.getMaterial(section.getString("material"));
        String displayName = section.getString("name");
        List<String> lore = (List<String>) section.getList("lore");
        return new ItemBuilder(material).setDisplayName(displayName).setLore(lore).build();
    }

    public abstract int getSize();

    public abstract String getTitle();

    public abstract void decorateInventory();
}
