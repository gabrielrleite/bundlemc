package com.gabrieldgamer.bundlemc.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gabrieldgamer.bundlemc.Main;

public class BundleMC implements CommandExecutor, TabCompleter {
    Main instance = Main.getMain();
    FileConfiguration config = instance.getConfig();
    File messages = new File(instance.getDataFolder(), "messages.yml");
    YamlConfiguration mensagem = YamlConfiguration.loadConfiguration(messages);

    private String getArgs2(String status, CommandSender jogador) {
        switch (status) {
            case "true":
                return "true";
            case "false":
                return "false";
            default:
                jogador.sendMessage("§8[§6Bundle§bMC§8] §rUtilize §6true §rou §6false");
                return "null";
        }
    }

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        var debug = instance.getConfig().getBoolean("debug");
        String helpMessage = mensagem.getString("helpMessage");
        if (args.length == 0) {
            sender.sendMessage(helpMessage);
            return true;
        }
        String argumento2 = (args.length > 2) ? args[2] : "null";
        switch (args[0].toLowerCase()) {
            case "messages":
                if (args.length <= 1) {
                sender.sendMessage("§8[§6Bundle§bMC§8] §rUtilize um argumento (joinMessageOp) | §6Exemplo: /bundlemc messages joinMessageOp true");
                return true;
                };
                String valor = args[1];
                switch (valor) {
                    case "joinMessageOp":
                        if(getArgs2(argumento2, sender).contains("null")) break; 
                        instance.getConfig().set("messages.joinMessageOp", Boolean.parseBoolean(args[2]));
                        break;
                    default:
                        sender.sendMessage("§8[§6Bundle§bMC§8] §rAs mensagens disponíveis são: \"joinMessageOp\".");
                        break;
                }
                instance.saveConfig();
                break;
            case "debug":
                if (debug) {
                    sender.sendMessage("§8[§6Bundle§bMC§8] §rO modo debug está §4DESATIVADO");
                    instance.getConfig().set("debug", false);
                } else {
                    sender.sendMessage("§8[§6Bundle§bMC§8] §rO modo debug está §2ATIVADO");
                    instance.getConfig().set("debug", true);
                }
                instance.saveConfig();
                break;
            default:
                sender.sendMessage("§8[§6Bundle§bMC§8] §rComando não reconhecido.");
                break;
        }
        
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("bundlemc")) {
            if (args.length == 1) {
                return getMatchingSubcommands(args[0], Arrays.asList("debug", "messages"));
            } else if (args.length == 2 && !args[0].equalsIgnoreCase("debug")) {
                return getMatchingSubcommands(args[1], Arrays.asList("joinMessageOp"));
            } else if (args.length == 3) {
                return getMatchingSubcommands(args[2], Arrays.asList("true", "false"));
            }
        }
        return Collections.emptyList();
    }

    private List<String> getMatchingSubcommands(String arg, List<String> subcommands) {
        List<String> completions = new ArrayList<>();
        for (String subcommand : subcommands) {
            if (subcommand.toLowerCase().startsWith(arg.toLowerCase())) {
                completions.add(subcommand);
            }
        }
        return completions;
    }
}