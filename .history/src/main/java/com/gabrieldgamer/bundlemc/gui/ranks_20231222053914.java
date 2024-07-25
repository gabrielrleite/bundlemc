package com.gabrieldgamer.bundlemc.gui;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.text.DecimalFormat;

import redempt.redlib.inventorygui.InventoryGUI;
// 
import redempt.redlib.inventorygui.ItemButton;
import redempt.redlib.itemutils.ItemBuilder;
import com.gabrieldgamer.bundlemc.Main;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;

import com.gabrieldgamer.bundlemc.Main;

public class Ranks {
    //

    // public static Inventory criarPadrao(CommandSender sender, Command command,
    // String label, String[] args)
    static Main instance = Main.getMain();

    private static String checkValue(Double d, Double Value) {
        if (Value >= d) {
            return "§a✔";
        }
        return "§4✖";
    }

    private static String checkVisitante(Double a, Double A, Double b, Double B, Double c, Double C) {
        if (a >= A && b >= B && c >= C) {
            return "§2§l✔ §a§lVocê pode upar de Rank §2§l✔";
        } else {
            return "§4§l✖ §c§lVocê não pode upar de Rank §4§l✖";
        }
    }

    private static String checkRank(String suffix) {
        if (suffix.equalsIgnoreCase("visitante")) {
            return "Novato";
        }
        if (suffix.equalsIgnoreCase("novato")) {
            return "Membro";
        }
        if (suffix.equalsIgnoreCase("membro")) {
            return "Aprendiz";
        }
        if (suffix.equalsIgnoreCase("aprendiz")) {
            return "Veterano";
        }
        return "Veterano";
    }

    public static void criarPadrao(CommandSender sender, org.bukkit.command.Command command, String label,
            String[] args) {
        var valor = instance.getConfig();
        User user = LuckPermsProvider.get().getPlayerAdapter(Player.class).getUser((Player) sender);
        String suffix = (String) user.getCachedData().getMetaData().getSuffix();
        Player player = (Player) sender;
        InventoryGUI gui = new InventoryGUI(Bukkit.createInventory(player, 54, "§4§l⇨ Rankup"));
        Double playtime_ticks = Double.valueOf(player.getStatistic(Statistic.PLAY_ONE_MINUTE)) / 20.0 / 60 / 60;
        Double walkedCm = player.getStatistic(Statistic.WALK_ONE_CM) / 100.0 / 1000.0;
        DecimalFormat d = new DecimalFormat("#.##");
        Double playerMoney = instance.economy.getBalance(player);
        Double playerTime = Double.valueOf(d.format((playtime_ticks)));
        Double playerWalk = Double.valueOf(d.format(walkedCm));

        ItemStack fill_red_item = new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .setName(" ");

        ItemStack fill_black_item = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
                .setName(" ");

        var price_rankup = valor.getDouble("rankup_price." + checkRank(suffix));

        ItemStack visitante_item = new ItemBuilder(Material.LEATHER_CHESTPLATE)
                .setName("Visitante")
                .addLore("")
                .addLore("§7Rank atual: " + "§2§o" + suffix)
                .addLore("§e§lPróximo rank ➹ " + "§b§l§o" + checkRank(suffix))
                .addLore("")
                .addLore("§6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅ §e§lRankUp §6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅")
                .addLore("")
                .addLore(checkValue(price_rankup, playerMoney) + " §aDinheiro: §2$" + playerMoney + "§e/§6$"
                        + price_rankup)
                .addLore(checkValue(2.0, playerTime) + " §aPlay time: §2" + playerTime + " horas§e/§62.0 horas")
                .addLore(checkValue(0.5, playerWalk) + " §aAndar: §2" + playerWalk + "km§e/§60.5km")
                .addLore("")
                .addLore("§6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅")
                .addLore("")
                .addLore(checkVisitante(playerWalk, 0.5, playerTime, 2.0, playerMoney, price_rankup))
                .addLore("");
        ItemStack novato_item = new ItemBuilder(Material.CHAINMAIL_CHESTPLATE)
                .setName("Novato");
        ItemStack membro_item = new ItemBuilder(Material.IRON_CHESTPLATE)
                .setName("Membro");
        ItemStack aprendiz_item = new ItemBuilder(Material.GOLDEN_CHESTPLATE)
                .setName("Aprendiz");
        ItemStack veterano_item = new ItemBuilder(Material.DIAMOND_CHESTPLATE)
                .setName("Veterano");
        ItemStack voltar_item = new ItemBuilder(Material.MAP)
                .setName("Voltar")
                .addLore(" ")
                .addLore("Volta ao Menu Principal");

        ItemStack visitanteItemStack = new ItemBuilder(Material.TOTEM_OF_UNDYING)
                .setName("§d§lClique aqui para RankUpar")
                .addLore("")
                .addLore("§7Rank atual: " + "§2§o" + suffix)
                .addLore("§e§lPróximo rank ➹ " + "§b§l§o" + checkRank(suffix))
                .addLore("")
                .addLore("§6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅ §e§lRankUp §6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅")
                .addLore("")
                .addLore(checkValue(price_rankup, playerMoney) + " §aDinheiro: §2$" + playerMoney + "§e/§6$"
                        + price_rankup)
                .addLore(checkValue(2.0, playerTime) + " §aPlay time: §2" + playerTime + " horas§e/§62.0 horas")
                .addLore(checkValue(0.5, playerWalk) + " §aAndar: §2" + playerWalk + "km§e/§60.5km")
                .addLore("")
                .addLore("§6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅")
                .addLore("")
                .addLore(checkVisitante(playerWalk, 0.5, playerTime, 2.0, playerMoney, price_rankup))
                .addLore("");

        ItemStack novatoItemStack = new ItemBuilder(Material.TOTEM_OF_UNDYING)
                .setName("§d§lClique aqui para RankUpar")
                .addLore("")
                .addLore("§7Rank atual: " + "§2§o" + suffix)
                .addLore("§e§lPróximo rank ➹ " + "§b§l§o" + checkRank(suffix))
                .addLore("")
                .addLore("§6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅ §e§lRankUp §6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅")
                .addLore("")
                .addLore(checkValue(price_rankup, playerMoney) + " §aDinheiro: §2$" + playerMoney + "§e/§6$"
                        + price_rankup)
                .addLore(checkValue(4.0, playerTime) + " §aPlay time: §2" + playerTime + " horas§e/§64.0 horas")
                .addLore(checkValue(1.0, playerWalk) + " §aAndar: §2" + playerWalk + "km§e/§61.0km")
                .addLore("")
                .addLore("§6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅")
                .addLore("")
                .addLore("§4§l✖ §c§lVocê não pode upar de Rank §4§l✖")
                .addLore("");

        ItemStack membroItemStack = new ItemBuilder(Material.TOTEM_OF_UNDYING)
                .setName("§d§lClique aqui para RankUpar")
                .addLore("")
                .addLore("§7Rank atual: " + "§2§o" + suffix)
                .addLore("§e§lPróximo rank ➹ " + "§b§l§o" + checkRank(suffix))
                .addLore("")
                .addLore("§6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅ §e§lRankUp §6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅")
                .addLore("")
                .addLore(checkValue(price_rankup, playerMoney) + " §aDinheiro: §2$" + playerMoney + "§e/§6$"
                        + price_rankup)
                .addLore(checkValue(8.0, playerTime) + " §aPlay time: §2" + playerTime + " horas§e/§68.0 horas")
                .addLore(checkValue(2.0, playerWalk) + " §aAndar: §2" + playerWalk + "km§e/§62.0km")
                .addLore("")
                .addLore("§6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅")
                .addLore("")
                .addLore("§4§l✖ §c§lVocê não pode upar de Rank §4§l✖")
                .addLore("");

        ItemStack aprendizItemStack = new ItemBuilder(Material.TOTEM_OF_UNDYING)
                .setName("§d§lClique aqui para RankUpar")
                .addLore("")
                .addLore("§7Rank atual: " + "§2§o" + suffix)
                .addLore("§e§lPróximo rank ➹ " + "§b§l§o" + checkRank(suffix))
                .addLore("")
                .addLore("§6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅ §e§lRankUp §6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅")
                .addLore("")
                .addLore(checkValue(price_rankup, playerMoney) + " §aDinheiro: §2$" + playerMoney + "§e/§6$"
                        + price_rankup)
                .addLore(checkValue(16.0, playerTime) + " §aPlay time: §2" + playerTime + " horas§e/§616.0 horas")
                .addLore(checkValue(4.0, playerWalk) + " §aAndar: §2" + playerWalk + "km§e/§64.0km")
                .addLore("")
                .addLore("§6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅")
                .addLore("")
                .addLore("§4§l✖ §c§lVocê não pode upar de Rank §4§l✖")
                .addLore("");

        ItemStack veteranoItemStack = new ItemBuilder(Material.TOTEM_OF_UNDYING)
                .setName("§d§lClique aqui para RankUpar")
                .addLore("")
                .addLore("§7Rank atual: " + "§2§o" + suffix)
                .addLore("§e§lPróximo rank ➹ " + "§b§l§o" + checkRank(suffix))
                .addLore("")
                .addLore("§6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅ §e§lRankUp §6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅")
                .addLore("")
                .addLore(checkValue(price_rankup, playerMoney) + " §aDinheiro: §2$" + playerMoney + "§e/§6$"
                        + price_rankup)
                .addLore(checkValue(32.0, playerTime) + " §aPlay time: §2" + playerTime + " horas§e/§632.0 horas")
                .addLore(checkValue(8.0, playerWalk) + " §aAndar: §2" + playerWalk + "km§e/§68.0km")
                .addLore("")
                .addLore("§6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅")
                .addLore("")
                .addLore("§4§l✖ §c§lVocê não pode upar de Rank §4§l✖")
                .addLore("");

        ItemButton visitanteButton = ItemButton.create(visitanteItemStack, e -> {
            if (playerTime >= 2 && playerMoney >= valor.getDouble("rankup_price." + checkRank(suffix))
                    && playerWalk >= 0 && suffix.equalsIgnoreCase("visitante")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + sender.getName() + " promote rankup");
                player.closeInventory();
            }
        });
        ItemButton novatoButton = ItemButton.create(novatoItemStack, e -> {
            return;
        });
        ItemButton membroButton = ItemButton.create(membroItemStack, e -> {
            return;
        });
        ItemButton aprendizButton = ItemButton.create(aprendizItemStack, e -> {
            return;
        });
        ItemButton veteranoButton = ItemButton.create(veteranoItemStack, e -> {
            return;
        });
        ItemButton visitante = ItemButton.create(visitante_item, e -> {
            return;
        });
        ItemButton novato = ItemButton.create(novato_item, e -> {
            return;
        });
        ItemButton membro = ItemButton.create(membro_item, e -> {
            return;
        });
        ItemButton aprendiz = ItemButton.create(aprendiz_item, e -> {
            return;
        });
        ItemButton veterano = ItemButton.create(veterano_item, e -> {
            return;
        });

        ItemButton fill_red = ItemButton.create(fill_red_item, e -> {
            return;
        });
        ItemButton fill_black = ItemButton.create(fill_black_item, e -> {
            return;
        });
        ItemButton voltarButton = ItemButton.create(voltar_item, e -> {
            player.closeInventory();
            return;
        });

        switch (suffix.toString()) {
            case "Visitante":
                gui.addButton(visitanteButton, 13);
                break;
            case "Novato":
                gui.addButton(novatoButton, 13);
                break;
            case "Membro":
                gui.addButton(membroButton, 13);
                break;
            case "Aprendiz":
                gui.addButton(aprendizButton, 13);
                break;
            case "Veterano":
                gui.addButton(veteranoButton, 13);
                break;
        }
        ;

        gui.addButton(visitante, 38);
        gui.addButton(novato, 39);
        gui.addButton(membro, 40);
        gui.addButton(aprendiz, 41);
        gui.addButton(veterano, 42);

        gui.addButton(fill_red, 0);
        gui.addButton(fill_black, 1);
        gui.addButton(fill_black, 9);

        gui.addButton(fill_black, 3);
        gui.addButton(fill_red, 4);
        gui.addButton(fill_black, 5);

        gui.addButton(fill_black, 7);
        gui.addButton(fill_red, 8);
        gui.addButton(fill_black, 17);

        gui.addButton(fill_black, 36);
        gui.addButton(fill_red, 45);
        gui.addButton(fill_black, 46);

        gui.addButton(fill_black, 48);
        gui.addButton(voltarButton, 49);
        gui.addButton(fill_black, 50);

        gui.addButton(fill_black, 44);
        gui.addButton(fill_red, 53);
        gui.addButton(fill_black, 52);

        gui.open(player);
        return;
    }
}
