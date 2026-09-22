package se.nixonmc.duels.core.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class ConfigManager {

    private final JavaPlugin plugin;

    private final Map<String, FileConfiguration> configurations = new HashMap<>();
    private final Map<String, File> files = new HashMap<>();

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void load() {
        load("config.yml");
        load("messages.yml");
    }

    public FileConfiguration load(String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);

        if (!file.exists()) {
            plugin.saveResource(fileName, false);
        }

        FileConfiguration configuration =
                YamlConfiguration.loadConfiguration(file);

        String key = fileName.toLowerCase();

        configurations.put(key, configuration);
        files.put(key, file);

        return configuration;
    }

    public FileConfiguration reload(String fileName) {
        return load(fileName);
    }

    public void save(String fileName) {
        String key = fileName.toLowerCase();

        FileConfiguration configuration = configurations.get(key);
        File file = files.get(key);

        if (configuration == null || file == null) {
            throw new IllegalArgumentException(
                    "Configuration '" + fileName + "' has not been loaded."
            );
        }

        try {
            configuration.save(file);
        } catch (IOException exception) {
            throw new RuntimeException(
                    "Failed to save configuration: " + fileName,
                    exception
            );
        }
    }

    public FileConfiguration get(String fileName) {
        return configurations.get(fileName.toLowerCase());
    }

    public boolean isLoaded(String fileName) {
        return configurations.containsKey(fileName.toLowerCase());
    }
}