package com.gabrieldgamer.bundlemc.gui;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Ranks {

    public static Inventory criarPadrao(CommandSender sender, Command command, String label, String[] args) {
        // Cria um inventário (GUI) com base nos parâmetros fornecidos
        Inventory gui = Bukkit.createInventory(null, 9, "Minha GUI");

        // Adiciona itens ao inventário (GUI) com base nas variáveis fornecidas
        // Aqui, você pode usar sender, command, label, args para personalizar o conteúdo da GUI
        // Exemplo: Adiciona uma pedra
        gui.setItem(0, new ItemStack(org.bukkit.Material.STONE));

        return gui;
    }
}
