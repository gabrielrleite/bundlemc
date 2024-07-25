package com.gabrieldgamer.bundlemc.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Open {

    public static void abrirGUI(CommandSender sender, Command command, String label, String[] args) {
        // Verifique se o remetente é um jogador
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando só pode ser executado por jogadores.");
            return;
        }

        Player player = (Player) sender;

        // Crie seu inventário (GUI) aqui
        Inventory inventario = Bukkit.createInventory(null, 9, "Meu GUI");

        // Adicione itens ao inventário, se necessário

        // Abra o inventário para o jogador
        player.openInventory(inventario);
    }
}
