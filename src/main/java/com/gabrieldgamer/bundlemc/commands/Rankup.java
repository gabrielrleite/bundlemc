package com.gabrieldgamer.bundlemc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.gabrieldgamer.bundlemc.gui.RanksGUI;

public class Rankup implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando só pode ser executado por jogadores.");
            return true;
        }

        RanksGUI.rankGUI(sender, command, label, args);

        return true;
    }
}
