package com.gabrieldgamer.stacker;

import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import com.bgsoftware.wildstacker.WildStacker;
import com.bgsoftware.wildstacker.api.StackedItem;

public class ItemDrop implements Listener {

    @EventHandler
    public void onItemDrop(ItemSpawnEvent event) {
        Item droppedItem = event.getEntity();
        StackedItem stackedItem = <link>WildStacker.getStackedItem</link>(droppedItem);

        if (stackedItem != null) {
            stackedItem.stack(); // Stackar o item
        }
    }
}