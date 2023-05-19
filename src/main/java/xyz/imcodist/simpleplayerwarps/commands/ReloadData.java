package xyz.imcodist.simpleplayerwarps.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.jetbrains.annotations.NotNull;
import xyz.imcodist.simpleplayerwarps.SimplePlayerWarps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReloadData implements TabExecutor {
    private final SimplePlayerWarps plugin;
    public ReloadData(SimplePlayerWarps javaPlugin) {
        plugin = javaPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        // Reload warps.
        plugin.warpDataHandler.loadFiles();

        // TODO: Make this better.
        try {
            plugin.getConfig().load(plugin.getDataFolder() + "/config.yml");
        } catch (Exception ignored) {}

        //sender.sendRichMessage("<gray>Reloaded</gray> warps<gray>.</gray>");
        sender.sendRichMessage("<gray>Reloaded</gray> config <gray>and</gray> warps<gray>.</gray>");
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        return new ArrayList<>();
    }
}
