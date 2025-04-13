package com.inloadings.liteWarpGUI.util;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.inloadings.liteWarpGUI.LiteWarpGUI;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {
    private final ItemMeta itemMeta;
    private final ItemStack itemStack;

    public ItemBuilder(Material material) {
        itemStack = new ItemStack(material);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, Color color) {
        itemStack = new ItemStack(material);
        itemMeta = itemStack.getItemMeta();
        if (itemMeta instanceof LeatherArmorMeta leatherMeta) {
            leatherMeta.setColor(color);
            itemStack.setItemMeta(leatherMeta);
        }
    }

    public ItemBuilder(Material material, String hexColor) {
        itemStack = new ItemStack(material);
        itemMeta = itemStack.getItemMeta();
        if (itemMeta instanceof LeatherArmorMeta leatherMeta) {
            Color color = Color.fromRGB(
                    Integer.valueOf(hexColor.substring(0, 2), 16),  // Red
                    Integer.valueOf(hexColor.substring(2, 4), 16),  // Green
                    Integer.valueOf(hexColor.substring(4, 6), 16)   // Blue
            );
            leatherMeta.setColor(color);
            itemStack.setItemMeta(leatherMeta);
        }
    }

    public ItemBuilder setSkullTexture(String base64) {
        if (itemStack.getType() != Material.PLAYER_HEAD) {
            throw new IllegalStateException("The texture can only be installed for PLAYER_HEAD!");
        }

        SkullMeta skullMeta = (SkullMeta) itemMeta;
        PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
        profile.setProperty(new ProfileProperty("textures", base64));

        skullMeta.setPlayerProfile(profile);
        itemStack.setItemMeta(skullMeta);
        return this;
    }

    public ItemBuilder(Material material, int amount) {
        itemStack = new ItemStack(material, amount);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, int amount, byte durability) {
        itemStack = new ItemStack(material, amount, durability);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder setLore(String... lore) {
        List<Component> formattedLore = new LinkedList<>();
        for (String component : lore) {
            formattedLore.add(Component.text(component));
        }

        itemMeta.lore(formattedLore);
        return this;
    }

    public ItemBuilder addNamespacedKey(String key) {
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        //using getter for better usage
        container.set(new NamespacedKey(LiteWarpGUI.getInstance(), key), PersistentDataType.STRING, key);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        List<Component> formattedLore = new LinkedList<>();
        for (String component : lore) {
            formattedLore.add(Component.text(component));
        }

        itemMeta.lore(formattedLore);
        return this;
    }

    public ItemBuilder setEnchantmentGlint(boolean shouldSetGlint) {
        if (!shouldSetGlint) return this;
        itemMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder hideAttributes() {
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int enchantmentLevel) {
        itemMeta.addEnchant(enchantment, enchantmentLevel, true);
        return this;
    }

    public ItemBuilder setDisplayName(String name) {
        setDisplayName(Component.text(name));
        return this;
    }

    public void setDisplayName(Component name) {
        itemMeta.displayName(name);
    }

    public ItemStack cloneItemstack() {
        return build().clone();
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
