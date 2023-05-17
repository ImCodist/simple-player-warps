package xyz.imcodist.simpleplayerwarps;

import org.bukkit.plugin.java.JavaPlugin;

import xyz.imcodist.simpleplayerwarps.commands.*;
import xyz.imcodist.simpleplayerwarps.data.Config;
import xyz.imcodist.simpleplayerwarps.data.WarpDataHandler;

import java.io.File;

public final class SimplePlayerWarps extends JavaPlugin {
    public WarpDataHandler warpDataHandler;

    @Override
    public void onEnable() {
        // Set up the config class.
        loadConfig();
        getLogger().info("Loaded configuration file");

        // Set up the warp data class.
        warpDataHandler = new WarpDataHandler(new File(getDataFolder() + "/warps/"));
        getLogger().info("Loaded " + warpDataHandler.warps.size() + " warps");

        // Register commands
        try {
            getCommand("warp").setExecutor(new TeleportWarp(warpDataHandler));
            getCommand("warps").setExecutor(new ListWarps(warpDataHandler));
            getCommand("warpset").setExecutor(new CreateWarp(warpDataHandler, this.getConfig()));
            getCommand("warpdel").setExecutor(new RemoveWarp(warpDataHandler));
            getCommand("warpinfo").setExecutor(new InfoWarp(warpDataHandler));
            getCommand("warpreload").setExecutor(new ReloadData(this));
        } catch (NullPointerException e) {
            getLogger().info(e.toString());
        }
    }

    @Override
    public void onDisable() {
        // Disable logic.
        // Nothing here yet.
    }

    public void loadConfig() {
        new Config(this);
    }
}
