package com.gabrieldgamer.bundlemc.gui;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Ranks {

    public static Inventory criarPadrao(CommandSender sender, Command command, String label, String[] args) {
        // Cria um inventário (GUI) com base nos parâmetros fornecidos
        Inventory gui = Bukkit.createInventory(null, itens.length, titulo);

        // Adiciona itens ao inventário (GUI) com base nos parâmetros fornecidos
        for (int i = 0; i < itens.length; i++) {
            gui.setItem(i, itens[i]);
        }

        return gui;
    }
}
