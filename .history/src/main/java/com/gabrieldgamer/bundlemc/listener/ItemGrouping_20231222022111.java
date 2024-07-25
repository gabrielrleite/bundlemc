package com.gabrieldgamer.bundlemc.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.gabrieldgamer.bundlemc.Main;

import java.util.HashMap;
import java.util.Map;

public class ItemGrouping implements Listener {

    private final Map<World, Map<Material, Map<Location, Integer>>> groupedItems = new HashMap<>();

    public ItemGrouping(Main main) {
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        Item item = event.getItem();
        Material itemType = item.getItemStack().getType();
        World world = item.getWorld();
        Location itemLocation = item.getLocation();

        Map<Material, Map<Location, Integer>> materialMap = groupedItems.computeIfAbsent(world, k -> new HashMap<>());
        Map<Location, Integer> locationMap = materialMap.computeIfAbsent(itemType, k -> new HashMap<>());

        // Verifica itens próximos
        for (Location existingLocation : locationMap.keySet()) {
            if (isLocationNear(itemLocation, existingLocation)) {
                // Itens estão próximos, agrupe e remova o item anterior
                int existingAmount = locationMap.get(existingLocation);
                locationMap.put(existingLocation, existingAmount + 1);
                item.remove();
                return;
            }
        }

        // Nenhum item próximo encontrado, adiciona à lista
        locationMap.put(itemLocation, 1);
    }

    private boolean isLocationNear(Location loc1, Location loc2) {
        // Ajuste a distância máxima conforme necessário
        double maxDistance = 1.0;

        double dx = Math.abs(loc1.getX() - loc2.getX());
        double dy = Math.abs(loc1.getY() - loc2.getY());
        double dz = Math.abs(loc1.getZ() - loc2.getZ());

        return dx < maxDistance && dy < maxDistance && dz < maxDistance;
    }
}
