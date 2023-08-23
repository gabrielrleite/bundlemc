package com.gabrieldgamer.bundlemc;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.gabrieldgamer.bundlemc.listener.ActionListener;
import com.gabrieldgamer.bundlemc.listener.EventListener;
import com.gabrieldgamer.bundlemc.commands.Backpack;
import com.gabrieldgamer.bundlemc.commands.BundleMC;
import com.gabrieldgamer.bundlemc.commands.Ranks;
import com.gabrieldgamer.bundlemc.commands.Rankup;

import net.luckperms.api.LuckPerms;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {
    public static RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager()
            .getRegistration(LuckPerms.class);
    public LuckPerms api;
    private static final Logger log = Logger.getLogger("Minecraft");
    public Economy economy;
    private FileConfiguration customConfig;

    @Override
    public void onEnable() {
        if (!new File(getDataFolder(), "config.yml").exists()) {
            saveDefaultConfig();
        }
        if (!new File(getDataFolder(), "messages.yml").exists()) {
            saveResource("messages.yml", false);
        }
        new ActionListener(this);
        getCommand("bundlemc").setExecutor(new BundleMC());
        getCommand("ranks").setExecutor(new Ranks());
        getCommand("rankup").setExecutor(new Rankup());
        getCommand("backpack").setExecutor(new Backpack());
        new EventListener(this);
        if (!setupEconomy()) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        customConfig = new YamlConfiguration();
    }

    public FileConfiguration getCustomConfig(String string) {
        return this.customConfig;
    }

    @Override
    public void onLoad() {
        log.info("A versão " + getDescription().getVersion() + " do plugin §6Bundle§bMC§r foi carregada!");
    }

    public static Main getMain() {
        return (Main) Bukkit.getServer().getPluginManager().getPlugin("BundleMC");
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

}
