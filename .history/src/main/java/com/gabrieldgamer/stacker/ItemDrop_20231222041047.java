package com.gabrieldgamer.stacker;

import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import com.bgsoftware.wildstacker.api.objects.StackedEntity;
import com.bgsoftware.wildstacker.api.WildStackerAPI;

public class ItemDrop implements Listener {

    private final JavaPlugin plugin; // Referência para o seu plugin

    public ItemDrop(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerEvents() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onItemDrop(ItemSpawnEvent event) {
        Item droppedItem = event.getEntity();
        StackedEntity stackedEntity = <link>WildStackerAPI</link>.getStackedEntity(droppedItem);

        if (stackedEntity != null) {
            stackedEntity.runStack(); // Stackar o item
        }
    }
}