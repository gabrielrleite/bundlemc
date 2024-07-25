<<<<<<< HEAD
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
=======
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
>>>>>>> 05206fe2bfb6a09718986d2bb6ed16ae3e8125a7
