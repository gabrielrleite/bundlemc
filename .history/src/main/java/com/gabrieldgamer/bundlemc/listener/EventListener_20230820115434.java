package com.gabrieldgamer.bundlemc.listener;

import java.io.File;
import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import com.gabrieldgamer.bundlemc.Main;

public class EventListener implements Listener {

    Main instance = Main.getMain();
    File messages = new File(instance.getDataFolder(), "messages.yml");
    YamlConfiguration mensagem = YamlConfiguration.loadConfiguration(messages);

    private Plugin plugin;

    public EventListener(Plugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent event) {

        if (instance.getConfig().getBoolean("messages.joinMessageOp") == true) {
            event.getPlayer().sendMessage(mensagem.getString("joinMessages.messageJoinOp"));
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDeath(EntityDeathEvent event) {
        Bukkit.broadcast((event.getEntity()).toString(), null);
        if (event.getEntity().getKiller() != null) {
            var User = event.getEntity().getKiller();
            OfflinePlayer op = (OfflinePlayer) User;
            var Valor = instance.getConfig();
            var Mob = event.getEntity().getName().toLowerCase();
            Double money = Double.valueOf(Valor.getInt("mobs_money." + Mob));
            Double random = (Math.random() * 100);
            String rand = new DecimalFormat("#.##").format(random);
            Double chance = Valor.getDouble("mobs_chance." + Mob);
            if (Valor.getBoolean("debug") == true) {
                User.sendMessage("Chance Sorteada: " + Double.valueOf(rand) + " Chance do Mob: " + chance);
            }
            if (money != 0) {
                if (Double.valueOf(rand) < chance) {
                    instance.economy.depositPlayer(op, money);
                    User.sendMessage("Você ganhou R$" + money);
                }
            }
        } else {
            return;
        }

    }
}
