package com.gabrieldgamer.bundlemc.commands;

import java.io.File;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gabrieldgamer.bundlemc.Main;

public class BundleMC implements CommandExecutor {
    Main instance = Main.getMain();
    FileConfiguration config = instance.getConfig();
    File messages = new File(instance.getDataFolder(), "messages.yml");
    YamlConfiguration mensagem = YamlConfiguration.loadConfiguration(messages);

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(
                    "§6Ajuda do comando: §r/bundlemc\n/bundlemc messages §e(joinMessageOp/OutraMensagem)§6 - Ativa | Desativa Mensagens§r\n/bundlemc help");
            return true;
        }
        if (args[0].equalsIgnoreCase("messages")) {
            if (args[1].length() == 0) {
                sender.sendMessage(
                        "Utilize uma argumento (joinMessageOp) | §6Exemplo: /bunglemc messages joinMessageOp true");
                return true;
            }
            if (args[1].equalsIgnoreCase("joinMessageOp")) {
                if (Boolean.parseBoolean(args[1]) == true || Boolean.parseBoolean(args[1]) == false) {
                    instance.getConfig().set("messages.joinMessageOp", Boolean.parseBoolean(args[1]));
                } else {
                    sender.sendMessage("Utilize §6true §rou §6false");
                }
            }
        }
        if (args[0].equalsIgnoreCase("debug")) {
            var debug = config.getBoolean("debug");
            if (debug == true) {
                sender.sendMessage("§8[§6Bundle§bMC§8] §rO modo debug está §4DESATIVADO");
                instance.getConfig().set("debug", false);
            } else {
                sender.sendMessage("§8[§6Bundle§bMC§8] §rO modo debug está §2ATIVADO");
                instance.getConfig().set("debug", true);
            }
        }
        return true;
    }
}
