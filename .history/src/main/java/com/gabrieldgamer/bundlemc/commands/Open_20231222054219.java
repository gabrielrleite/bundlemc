package com.gabrieldgamer.bundlemc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import com.gabrieldgamer.bundlemc.gui.Ranks; // Certifique-se de ajustar o caminho do pacote

public class Open implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Verifique se o remetente é um jogador
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando só pode ser executado por jogadores.");
            return true; // Retorna true para indicar que o comando foi processado
        }

        Player player = (Player) sender;

        // Crie seu inventário (GUI) utilizando a classe GuiPadrao
        Ranks.criarPadrao(sender, command, label, args);

        // Abra o inventário para o jogador

        return true; // Retorna true para indicar que o comando foi processado
    }
}
