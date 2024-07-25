package com.gabrieldgamer.bundlemc.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Backpack implements CommandExecutor {

    public static Inventory BACKPACK;

    @Override

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (!(p.hasPermission("bundlemc.bp.default"))) {
            p.sendMessage("PERMISSÃO");
            return true;
        }
        BACKPACK = Bukkit.createInventory(p, 9, "§7§l" + p.getName() + "'s §4Backpack");
        ((Player) p).openInventory(BACKPACK);
        return false;
    };

}
