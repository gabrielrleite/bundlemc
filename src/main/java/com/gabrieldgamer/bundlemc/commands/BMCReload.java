package com.gabrieldgamer.bundlemc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class BMCReload implements CommandExecutor {

    private final Plugin plugin;

    public BMCReload(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        plugin.reloadConfig();
        sender.sendMessage("Configurações do plugin recarregadas com sucesso!");
        return true;
    }
}
