package com.gabrieldgamer.bundlemc.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {

    private final ItemStack item;

    public ItemBuilder(Material material) {
        this.item = new ItemStack(material);
    }

    public ItemBuilder setName(String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addLore(String lore) {
        ItemMeta meta = item.getItemMeta();
        List<String> loreList = meta.hasLore() ? meta.getLore() : new ArrayList<>();
        loreList.add(lore);
        meta.setLore(loreList);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addLoresToItem(List<String> lores) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            List<String> currentLores = meta.getLore();
            if (currentLores == null) {
                currentLores = lores;
            } else {
                currentLores.addAll(lores);
            }
            meta.setLore(currentLores);
            item.setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder addLoreIfPresent(String lore, boolean condition) {
        if (condition) {
            addLore(lore);
        }
        return this;
    }

    public ItemStack build() {
        return item;
    }
}

