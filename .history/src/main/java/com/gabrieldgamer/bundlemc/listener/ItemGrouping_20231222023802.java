package com.gabrieldgamer.bundlemc.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import java.util.HashMap;
import java.util.Map;

public class ItemGrouping implements Listener {

    private final Map<World, Map<Material, Map<Location, GroupedItem>>> groupedItems = new HashMap<>();

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        Item item = event.getItem();
        Material itemType = item.getItemStack().getType();
        World world = item.getWorld();
        Location itemLocation = item.getLocation();

        Map<Material, Map<Location, GroupedItem>> materialMap = groupedItems.computeIfAbsent(world,
                k -> new HashMap<>());
        Map<Location, GroupedItem> locationMap = materialMap.computeIfAbsent(itemType, k -> new HashMap<>());

        // Verifica itens próximos
        for (Location existingLocation : locationMap.keySet()) {
            if (isLocationNear(itemLocation, existingLocation)) {
                // Itens estão próximos, agrupe
                GroupedItem groupedItem = locationMap.get(existingLocation);
                groupedItem.addItem(item);
                item.remove(); // Remove o item do chão
                event.setCancelled(true); // Cancela o evento para evitar que o item seja pego normalmente
                return;
            }
        }

        // Nenhum item próximo encontrado, cria um novo GrupoItem e adiciona à lista
        GroupedItem newGroupedItem = new GroupedItem(itemType, item);
        locationMap.put(itemLocation, newGroupedItem);
    }

    private boolean isLocationNear(Location loc1, Location loc2) {
        // Ajuste a distância máxima conforme necessário
        double maxDistance = 1.0;

        double dx = Math.abs(loc1.getX() - loc2.getX());
        double dy = Math.abs(loc1.getY() - loc2.getY());
        double dz = Math.abs(loc1.getZ() - loc2.getZ());

        return dx < maxDistance && dy < maxDistance && dz < maxDistance;
    }

    private static class GroupedItem {
        private final Material itemType;
        private final Location groupLocation;

        public GroupedItem(Material itemType, Item referenceItem) {
            this.itemType = itemType;
            this.groupLocation = referenceItem.getLocation();
        }

        public void addItem(Item item) {
            // Atualiza a localização do grupo para a nova posição do item
            this.groupLocation.setX(item.getLocation().getX());
            this.groupLocation.setY(item.getLocation().getY());
            this.groupLocation.setZ(item.getLocation().getZ());
        }
    }
}
