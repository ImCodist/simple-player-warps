package xyz.imcodist.simpleplayerwarps.data;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {
    public FileConfiguration config;
    private JavaPlugin plugin;

    public Config(JavaPlugin javaPlugin) {
        plugin = javaPlugin;
        config = javaPlugin.getConfig();

        addDefaults();

        config.options().copyDefaults(true);
        plugin.saveConfig();
    }

    private void addDefaults() {
        config.addDefault("maxPlayerWarps", 3);
    }
}
