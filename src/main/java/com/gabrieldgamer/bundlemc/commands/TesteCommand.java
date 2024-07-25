package com.gabrieldgamer.bundlemc.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TesteCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {

        if (sender instanceof Player)
            sender.sendMessage("Olá " + sender.getName() + " esse é o comando Teste!");
        else
            sender.sendMessage("Olá Minecraft!");
        return true;
    }
}
