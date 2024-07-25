package com.gabrieldgamer.bundlemc.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.gabrieldgamer.bundlemc.Main;

import java.util.HashMap;
import java.util.Map;

public class ItemGrouping implements Listener {

    private final Main plugin;
    private final Map<World, Map<Material, Map<Location, Item>>> groupedItems = new HashMap<>();

    public ItemGrouping(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        Item item = event.getItem();
        Material itemType = item.getItemStack().getType();
        World world = item.getWorld();
        Location itemLocation = item.getLocation();

        Map<Material, Map<Location, Item>> materialMap = groupedItems.computeIfAbsent(world, k -> new HashMap<>());
        Map<Location, Item> locationMap = materialMap.computeIfAbsent(itemType, k -> new HashMap<>());

        // Verifica itens próximos
        for (Location existingLocation : locationMap.keySet()) {
            if (isLocationNear(itemLocation, existingLocation)) {
                // Itens estão próximos, mantenha apenas um item no chão e agrupe
                Item existingItem = locationMap.get(existingLocation);
                existingItem.setItemStack(itemStackCombine(existingItem.getItemStack(), item.getItemStack()));
                item.remove();
                plugin.getLogger().info("Itens agrupados no chão.");
                return;
            }
        }

        // Nenhum item próximo encontrado, cria um novo GrupoItem e adiciona à lista
        locationMap.put(itemLocation, item);
        plugin.getLogger().info("Novo item adicionado ao chão.");
    }

    private boolean isLocationNear(Location loc1, Location loc2) {
        // Ajuste a distância máxima conforme necessário
        double maxDistance = 1.0;

        double dx = Math.abs(loc1.getX() - loc2.getX());
        double dy = Math.abs(loc1.getY() - loc2.getY());
        double dz = Math.abs(loc1.getZ() - loc2.getZ());

        return dx < maxDistance && dy < maxDistance && dz < maxDistance;
    }

    private ItemStack itemStackCombine(ItemStack stack1, ItemStack stack2) {
        // Lógica para combinar stacks de itens aqui
        // Por exemplo, você pode somar as quantidades dos dois stacks
        // e criar um novo ItemStack combinado
        int combinedAmount = stack1.getAmount() + stack2.getAmount();
        stack1.setAmount(Math.min(stack1.getMaxStackSize(), combinedAmount));
        return stack1;
    }
}
