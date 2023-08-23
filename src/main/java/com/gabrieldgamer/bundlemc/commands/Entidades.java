package com.gabrieldgamer.bundlemc.commands;

import java.util.List;

import org.bukkit.entity.Entity;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Entidades implements CommandExecutor {
    Player player;
    Location loc;

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Location loc = player.getLocation();
            List<Entity> nearbyEntites = player.getNearbyEntities(loc.getX(), loc.getY(), loc.getZ());
            // String joinedString = String.join(", ", nearbyEntites);
            Bukkit.broadcastMessage("" + nearbyEntites.toString().replace("[", "").replace("]", ""));
        } else {
            sender.sendMessage("Olá Minecraft!");
        }
        return true;
    }
}
