import com.bgsoftware.wildstacker.api.WildStackerAPI;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemDrop extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Registra o evento de spawn de itens
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        Item item = event.getEntity();

        // Verifica se o item pode ser empilhado
        if (WildStackerAPI.canStack(item.getItemStack())) {
            // Empilha o item
            WildStackerAPI.stackEntities(item);
        }
    }
}
