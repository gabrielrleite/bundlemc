package com.gabrieldgamer.bundlemc.services;

import org.bukkit.configuration.file.FileConfiguration;

public interface ConfigurationService extends Service {

    FileConfiguration getConfig();
}
