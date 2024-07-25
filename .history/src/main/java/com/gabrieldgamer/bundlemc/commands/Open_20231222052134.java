package com.gabrieldgamer.bundlemc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Open implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Seu código aqui usando as informações passadas

        // Exemplo de uso:
        sender.sendMessage("Comando executado com sucesso!");

        return true;
    }
}
