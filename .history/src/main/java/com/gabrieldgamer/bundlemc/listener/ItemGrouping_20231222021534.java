package com.gabrieldgamer.bundlemc.listener;

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

    // Mapeia os itens por categoria
    private final Map<String, Map<Material, Integer>> categoryItems = new HashMap<>();

    public ItemGrouping(Main main) {
    }

    // Ouvinte para quando um jogador pega um item
    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        Item item = event.getItem();
        Material itemType = item.getItemStack().getType();
        World world = item.getWorld();

        // Obtém ou cria o mapa de itens para a categoria "Default"
        Map<Material, Integer> categoryMap = categoryItems.computeIfAbsent("Default", k -> new HashMap<>());

        // Adiciona o item à categoria "Default"
        categoryMap.put(itemType, categoryMap.getOrDefault(itemType, 0) + 1);

        // Exemplo: Adiciona o item à categoria do mundo
        Map<Material, Integer> worldCategoryMap = categoryItems.computeIfAbsent(world.getName(), k -> new HashMap<>());
        worldCategoryMap.put(itemType, worldCategoryMap.getOrDefault(itemType, 0) + 1);

        // Você pode adicionar mais lógica de agrupamento conforme necessário
    }
}
