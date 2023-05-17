package xyz.imcodist.simpleplayerwarps.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.imcodist.simpleplayerwarps.SimplePlayerWarps;
import xyz.imcodist.simpleplayerwarps.data.Config;
import xyz.imcodist.simpleplayerwarps.data.WarpDataHandler;

import java.util.ArrayList;
import java.util.List;

public class ReloadData implements TabExecutor {
    private SimplePlayerWarps plugin;
    public ReloadData(SimplePlayerWarps javaPlugin) {
        plugin = javaPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Reload warps.
        plugin.warpDataHandler.loadFiles();

        // TODO: Make this actually reload the config. I have no idea how it works.
        //plugin.reloadConfig();

        sender.sendRichMessage("<gray>Reloaded</gray> warps<gray>.</gray>");
        //sender.sendRichMessage("<gray>Reloaded</gray> config <gray>and</gray> warps<gray>.</gray>");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return new ArrayList<String>();
    }
}
