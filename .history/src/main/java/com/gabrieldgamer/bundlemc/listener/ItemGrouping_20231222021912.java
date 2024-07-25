package com.gabrieldgamer.bundlemc.listener;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class ItemGrouping implements Listener {

    private final Map<String, Map<Material, GroupedItem>> groupedItems = new HashMap<>();
    private final double MAX_DISTANCE = 2.0; // Ajuste conforme necessário

    public ItemGrouping(JavaPlugin plugin) {
        plugin.getServer().getScheduler().runTaskTimer(plugin, this::updateItemGroups, 20L, 20L);
    }

    private void updateItemGroups() {
        for (Map<Material, GroupedItem> categoryMap : groupedItems.values()) {
            for (GroupedItem groupedItem : categoryMap.values()) {
                groupedItem.updateLocation();
            }
        }
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        Item newItem = event.getItem();
        Material itemType = newItem.getItemStack().getType();
        World world = newItem.getWorld();
        String category = "Default";

        Map<Material, GroupedItem> categoryMap = groupedItems.computeIfAbsent(category, k -> new HashMap<>());

        // Verificar itens existentes na categoria
        if (categoryMap.containsKey(itemType)) {
            GroupedItem groupedItem = categoryMap.get(itemType);

            // Verificar se o item está próximo
            if (groupedItem.isNear(newItem.getLocation().toVector(), MAX_DISTANCE)) {
                // Adicionar o item ao grupo existente
                groupedItem.addItem(newItem);
                event.getItem().remove();
                event.setCancelled(true);
                return;
            }
        }

        // Criar um novo grupo para o item
        GroupedItem newGroupedItem = new GroupedItem(world, itemType);
        newGroupedItem.addItem(newItem);
        categoryMap.put(itemType, newGroupedItem);
    }

    private static class GroupedItem {
        private final World world;
        private final Material itemType;
        private Vector totalPosition;
        private int itemCount;

        public GroupedItem(World world, Material itemType) {
            this.world = world;
            this.itemType = itemType;
            this.totalPosition = new Vector(0, 0, 0);
            this.itemCount = 0;
        }

        public void addItem(Item item) {
            itemCount++;
            totalPosition.add(item.getLocation().toVector());
        }

public void updateLocation() {
            if (itemCount > 0) {
                Vector averagePosition = totalPosition.clone().multiply(1.0 / itemCount);
                for (Entity entity : world.getNearbyEntities(averagePosition.toLoca
