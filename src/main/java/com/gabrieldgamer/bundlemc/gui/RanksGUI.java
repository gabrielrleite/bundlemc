package com.gabrieldgamer.bundlemc.gui;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import redempt.redlib.inventorygui.InventoryGUI;
import redempt.redlib.inventorygui.ItemButton;
import com.gabrieldgamer.bundlemc.Main;
import com.gabrieldgamer.bundlemc.utils.ItemBuilder;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;

public class RanksGUI {
    private static String presuffix;

    static Main instance = Main.getMain();

    private static String checkValue(double required, double current) {
        return current >= required ? "§a✔" : "§c✘";
    }

    private static String checkRankup(String prefix, Player player) {
        if (getRankCommand(prefix, player) == true) {
            return "§2§l✔ §a§lVocê pode upar para esse Rank §2§l✔";
        } else {
            return "§4§l✖ §c§lVocê não pode upar para esse Rank §4§l✖";
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

    public static boolean getRankCommand(String rank, Player player) {
        File rankRequirements = new File(instance.getDataFolder(), "rank_requirements.yml");
        FileConfiguration requirementsData = YamlConfiguration.loadConfiguration(rankRequirements);
        String rankToLowerCase = rank.toLowerCase();

        ConfigurationSection novatoData = requirementsData.getConfigurationSection("novato");
        ConfigurationSection membroData = requirementsData.getConfigurationSection("membro");
        ConfigurationSection aprendizData = requirementsData.getConfigurationSection("aprendiz");
        ConfigurationSection veteranoData = requirementsData.getConfigurationSection("veterano");
        Double playtime_ticks = Double.valueOf(player.getStatistic(Statistic.PLAY_ONE_MINUTE)) / 20.0 / 60 / 60;
        Double walkedCm = player.getStatistic(Statistic.WALK_ONE_CM) / 100.0 / 1000.0;
        Double playerMoney = instance.economy.getBalance(player);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat d = new DecimalFormat("#.##", symbols);

        String formattedPlaytime = d.format(playtime_ticks).replace(',', '.');
        String formattedWalkedCm = d.format(walkedCm).replace(',', '.');
        Double playerTime = Double.valueOf(formattedPlaytime);
        Double playerWalk = Double.valueOf(formattedWalkedCm);
        switch (rankToLowerCase) {
            case "visitante":
                return isRequirementMet(novatoData, "ontime", playerTime) &&
                       isRequirementMet(novatoData, "money", playerMoney) &&
                       isRequirementMet(novatoData, "walk_distance", playerWalk) &&
                       areMobsKilled(novatoData, "mobs_kills", player);
    
            case "novato":
                return isRequirementMet(membroData, "ontime", playerTime) &&
                       isRequirementMet(membroData, "money", playerMoney) &&
                       isRequirementMet(membroData, "walk_distance", playerWalk) &&
                       areMobsKilled(membroData, "mobs_kills", player);
    
            case "membro":
                return isRequirementMet(aprendizData, "ontime", playerTime) &&
                       isRequirementMet(aprendizData, "money", playerMoney) &&
                       isRequirementMet(aprendizData, "walk_distance", playerWalk) &&
                       areMobsKilled(aprendizData, "mobs_kills", player);
    
            case "aprendiz":
                return isRequirementMet(veteranoData, "ontime", playerTime) &&
                       isRequirementMet(veteranoData, "money", playerMoney) &&
                       isRequirementMet(veteranoData, "walk_distance", playerWalk) &&
                       areMobsKilled(veteranoData, "mobs_kills", player);
    
            default:
                return false;
        }
    }

    private static boolean isRequirementMet(ConfigurationSection data, String key, double playerValue) {
        if (data.contains(key)) {
            double requiredValue = data.getDouble(key);
            return playerValue >= requiredValue;
        }
        return true;
    }
    
    private static boolean areMobsKilled(ConfigurationSection data, String key, Player player) {
        if (data.contains(key)) {
            ConfigurationSection mobsKillsSection = data.getConfigurationSection(key);
            if (mobsKillsSection == null) {
                return true;
            }
            
            for (String mobType : mobsKillsSection.getKeys(false)) {
                int requiredKills = mobsKillsSection.getInt(mobType);
                int actualKills = getPlayerMobKills(player, EntityType.valueOf(mobType));
    
                if (actualKills < requiredKills) {
                    return false;
                }
            }
            return true;
        }
        return true;
    }
    
    public static String PreRank(String rank) {

        String cleanedRank = rank.toLowerCase().trim();

        switch (cleanedRank) {
            case "visitante":
                presuffix = "novato";
                break;
            case "novato":
                presuffix = "membro";
                break;
            case "membro":
                presuffix = "aprendiz";
                break;
            case "aprendiz":
                presuffix = "veterano";
                break;
            default:
                presuffix = "veterano";
                break;
        }
        return presuffix;
    }


    public static void rankGUI(CommandSender sender, org.bukkit.command.Command command, String label,
            String[] args) {
                
        File rankRequirements = new File(instance.getDataFolder(), "rank_requirements.yml");
        FileConfiguration requirementsData = YamlConfiguration.loadConfiguration(rankRequirements);

        User user = LuckPermsProvider.get().getPlayerAdapter(Player.class).getUser((Player) sender);
        String suffix = (String) user.getCachedData().getMetaData().getSuffix();
        Player player = (Player) sender;
        InventoryGUI gui = new InventoryGUI(Bukkit.createInventory(player, 54, "§4§l⇨ Rankup"));
        Double playtime_ticks = Double.valueOf(player.getStatistic(Statistic.PLAY_ONE_MINUTE)) / 20.0 / 60 / 60;
        Double walkedCm = player.getStatistic(Statistic.WALK_ONE_CM) / 100.0 / 1000.0;
        Double playerMoney = instance.economy.getBalance(player);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat d = new DecimalFormat("#.##", symbols);

        String formattedPlaytime = d.format(playtime_ticks).replace(',', '.');
        String formattedWalkedCm = d.format(walkedCm).replace(',', '.');

        Double playerTime = Double.valueOf(formattedPlaytime);
        Double playerWalk = Double.valueOf(formattedWalkedCm);

        ItemStack fill_red_item = new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .setName(" ")
                .build();
        ItemStack fill_black_item = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
                .setName(" ")
                .build();
        ItemStack visitante_item = new ItemBuilder(Material.LEATHER_CHESTPLATE)
                .setName("§2§oVisitante")
                .addLore("")
                .addLore("§7Rank atual: " + "§2§o" + suffix)
                .addLore("§e§lPróximo rank ➹ " + "§b§l§o" + checkRank(suffix))
                .addLore("")
                .addLore("§6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅ §e§lRankUp §6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅")
                .addLore("")
                .addLore(checkValue(0.0, playerMoney) + " §aDinheiro: §2$" + playerMoney + "§e/§6$0")
                .addLore(checkValue(0.0, playerTime) + " §aPlay time: §2" + playerTime + " horas§e/§60 horas")
                .addLore(checkValue(0.0, playerWalk) + " §aAndar: §2" + playerWalk + "km§e/§60km")
                .addLore("")
                .addLore("§6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅")
                .addLore("")
                .addLore("§2§l✔ §a§lVocê pode upar para esse Rank §2§l✔")
                .addLore("")
                .build();
        ItemStack novato_item = new ItemBuilder(Material.CHAINMAIL_CHESTPLATE)
                .setName("Novato")
                .addLore("")
                .addLore("§7Rank atual: " + "§2§o" + suffix)
                .addLore("§e§lPróximo rank ➹ " + "§b§l§o" + checkRank(suffix))
                .addLore("")
                .addLore("§6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅ §e§lRankUp §6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅")
                .addLore("")
                .addLoreIfPresent(getMoneyLore(playerMoney, requirementsData.getConfigurationSection("novato").getDouble("money")), requirementsData.getConfigurationSection("novato").contains("money"))
                .addLoreIfPresent(getPlayTimeLore(playerTime, requirementsData.getConfigurationSection("novato").getDouble("ontime")), requirementsData.getConfigurationSection("novato").contains("ontime"))
                .addLoreIfPresent(getWalkDistanceLore(playerWalk, requirementsData.getConfigurationSection("novato").getDouble("walk_distance")), requirementsData.getConfigurationSection("novato").contains("walk_distance"))
                .addLoresToItem(getMobKillsLores(player, "novato"))
                .addLore("")
                .addLore("§6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅")
                .addLore("")
                .addLore(checkRankup("visitante", player))
                .addLore("")
                .build();
        ItemStack membro_item = new ItemBuilder(Material.IRON_CHESTPLATE)
                .setName("Membro")
                .addLore("")
                .addLore("§7Rank atual: " + "§2§o" + suffix)
                .addLore("§e§lPróximo rank ➹ " + "§b§l§o" + checkRank(suffix))
                .addLore("")
                .addLore("§6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅ §e§lRankUp §6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅")
                .addLore("")
                .addLoreIfPresent(getMoneyLore(playerMoney, requirementsData.getConfigurationSection("membro").getDouble("money")), requirementsData.getConfigurationSection("membro").contains("money"))
                .addLoreIfPresent(getPlayTimeLore(playerTime, requirementsData.getConfigurationSection("membro").getDouble("ontime")), requirementsData.getConfigurationSection("membro").contains("ontime"))
                .addLoreIfPresent(getWalkDistanceLore(playerWalk, requirementsData.getConfigurationSection("membro").getDouble("walk_distance")), requirementsData.getConfigurationSection("membro").contains("walk_distance"))
                .addLoresToItem(getMobKillsLores(player, "membro"))
                .addLore("")
                .addLore("§6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅")
                .addLore("")
                .addLore(checkRankup("novato", player))
                .addLore("")
                .build();
        ItemStack aprendiz_item = new ItemBuilder(Material.GOLDEN_CHESTPLATE)
                .setName("Aprendiz")
                .addLore("")
                .addLore("§7Rank atual: " + "§2§o" + suffix)
                .addLore("§e§lPróximo rank ➹ " + "§b§l§o" + checkRank(suffix))
                .addLore("")
                .addLore("§6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅ §e§lRankUp §6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅")
                .addLore("")
                .addLoreIfPresent(getMoneyLore(playerMoney, requirementsData.getConfigurationSection("aprendiz").getDouble("money")), requirementsData.getConfigurationSection("aprendiz").contains("money"))
                .addLoreIfPresent(getPlayTimeLore(playerTime, requirementsData.getConfigurationSection("aprendiz").getDouble("ontime")), requirementsData.getConfigurationSection("aprendiz").contains("ontime"))
                .addLoreIfPresent(getWalkDistanceLore(playerWalk, requirementsData.getConfigurationSection("aprendiz").getDouble("walk_distance")), requirementsData.getConfigurationSection("aprendiz").contains("walk_distance"))
                .addLoresToItem(getMobKillsLores(player, "aprendiz"))
                .addLore("")
                .addLore("§6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅")
                .addLore("")
                .addLore(checkRankup("membro", player))
                .addLore("")
                .build();
        ItemStack veterano_item = new ItemBuilder(Material.DIAMOND_CHESTPLATE)
                .setName("Veterano")
                .addLore("")
                .addLore("§7Rank atual: " + "§2§o" + suffix)
                .addLore("§e§lPróximo rank ➹ " + "§b§l§o" + checkRank(suffix))
                .addLore("")
                .addLore("§6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅ §e§lRankUp §6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅")
                .addLore("")
                .addLoreIfPresent(getMoneyLore(playerMoney, requirementsData.getConfigurationSection("veterano").getDouble("money")), requirementsData.getConfigurationSection("veterano").contains("money"))
                .addLoreIfPresent(getPlayTimeLore(playerTime, requirementsData.getConfigurationSection("veterano").getDouble("ontime")), requirementsData.getConfigurationSection("veterano").contains("ontime"))
                .addLoreIfPresent(getWalkDistanceLore(playerWalk, requirementsData.getConfigurationSection("veterano").getDouble("walk_distance")), requirementsData.getConfigurationSection("veterano").contains("walk_distance"))
                .addLoresToItem(getMobKillsLores(player, "veterano"))
                .addLore("")
                .addLore("§6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅")
                .addLore("")
                .addLore(checkRankup("aprendiz", player))
                .addLore("")
                .build();
        ItemStack voltar_item = new ItemBuilder(Material.MAP)
                .setName("Voltar")
                .addLore(" ")
                .addLore("Volta ao Menu Principal")
                .build();
        String presuffix = PreRank(suffix);
        ItemStack rankupItemStack = new ItemBuilder(Material.TOTEM_OF_UNDYING)     
                .setName("§d§lClique aqui para RankUpar")
                .addLore("")
                .addLore("§7Rank atual: " + "§2§o" + suffix)
                .addLore("§e§lPróximo rank ➹ " + "§b§l§o" + checkRank(suffix))
                .addLore("")
                .addLore("§6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅ §e§lRankUp §6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅")
                .addLore("")
                .addLoreIfPresent(getMoneyLore(playerMoney, requirementsData.getConfigurationSection(presuffix).getDouble("money")), requirementsData.getConfigurationSection(presuffix).contains("money"))
                .addLoreIfPresent(getPlayTimeLore(playerTime, requirementsData.getConfigurationSection(presuffix).getDouble("ontime")), requirementsData.getConfigurationSection(presuffix).contains("ontime"))
                .addLoreIfPresent(getWalkDistanceLore(playerWalk, requirementsData.getConfigurationSection(presuffix).getDouble("walk_distance")), requirementsData.getConfigurationSection(presuffix).contains("walk_distance"))
                .addLoresToItem(getMobKillsLores(player, presuffix))
                .addLore("")
                .addLore("§6┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅┅")
                .addLore("")
                .addLore(checkRankup(suffix, player))
                .addLore("")
                .build();
        ItemButton rankupButton = ItemButton.create(rankupItemStack, e -> {
            if (getRankCommand(suffix, player) == true) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + sender.getName() + " promote rankup");
                player.closeInventory();
            } else {
                sender.sendMessage("Ocorreu algum erro:\n" + getRankCommand(suffix, player));
            }

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
        gui.addButton(rankupButton, 13);

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

    private static List<String> getMobKillsLores(Player player, String suffixo) {
        List<String> mobKillsLores = new ArrayList<>();
        File rankRequirements = new File(instance.getDataFolder(), "rank_requirements.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(rankRequirements);
        if (config.contains(suffixo.toLowerCase() + ".mobs_kills")) {
            ConfigurationSection mobsSection = config.getConfigurationSection(suffixo.toLowerCase() + ".mobs_kills");
            if (mobsSection != null) {
                for (String key : mobsSection.getKeys(false)) {
                    int requiredKills = mobsSection.getInt(key);
                    EntityType entityType = EntityType.valueOf(key.toUpperCase());
                    int currentKills = getPlayerMobKills(player, entityType);
                    String lore = getMobKillsLore(key, currentKills, requiredKills);
                    if (lore != null && !lore.isEmpty()) {
                        mobKillsLores.add(lore);
                    }
                }
            }
        }
        return mobKillsLores;
    }
    private static String mobTypeName(String mobName) {
        File messages = new File(instance.getDataFolder(), "messages.yml");
        FileConfiguration messsagesText = YamlConfiguration.loadConfiguration(messages);
        String result = messsagesText.getConfigurationSection("rankupGUI.mobs").getString(mobName.toLowerCase());
        return result;
    }
    private static String getMobKillsLore(String mobType, int currentKills, int requiredKills) {
        return checkValue(requiredKills, currentKills) + " §aMate " + mobTypeName(mobType) + ": §2" + currentKills + "§e/§6" + requiredKills;
    }
    private static String getPlayTimeLore(double playerTime, double requiredTime) {
        return checkValue(requiredTime, playerTime) + " §aPlay time: §2" + playerTime + " horas§e/§6" + requiredTime + " horas";
    }
    private static String getWalkDistanceLore(double playerWalk, double requiredWalk) {
        return checkValue(requiredWalk, playerWalk) + " §aAndar: §2" + playerWalk + "km§e/§6" + requiredWalk + "km";
    }
    private static String getMoneyLore(double playerMoney, double requiredMoney) {
        return checkValue(requiredMoney, playerMoney) + " §aDinheiro: §2$" + playerMoney + "§e/§6$" + requiredMoney;
    }
    private static int getPlayerMobKills(Player player, EntityType mobType) {
        return player.getStatistic(Statistic.KILL_ENTITY, mobType);
    }
}