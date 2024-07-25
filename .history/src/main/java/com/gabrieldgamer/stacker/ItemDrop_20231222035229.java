package com.gabrieldgamer.stacker;

import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import com.bgsoftware.wildstacker.api.events.ItemStackEvent;
import com.bgsoftware.wildstacker.api.events.EntityStackEvent;

//import com.bgsoftware.wildstacker.api.WildStacker;

public class ItemDrop implements Listener {

    @EventHandler
    public void onItemDrop(ItemSpawnEvent event) {
        Item droppedItem = event.getEntity();
        StackedEntity stackedEntity = <link>WildStacker</link>.getStackedEntity(droppedItem);

        if (stackedEntity != null && stackedEntity instanceof StackedItem) {
            StackedItem stackedItem = (StackedItem) stackedEntity;

            // Verificar se o item já está stackado
            if (stackedItem.isStacked()) {
                // O item já está stackado, você pode manipular isso aqui
            } else {
                // O item não está stackado, você pode stacká-lo aqui
                stackedItem.stack(); // Stackar o item
            }
        }
    }
}