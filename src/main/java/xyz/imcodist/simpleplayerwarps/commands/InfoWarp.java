package xyz.imcodist.simpleplayerwarps.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import xyz.imcodist.simpleplayerwarps.data.WarpData;
import xyz.imcodist.simpleplayerwarps.data.WarpDataHandler;

import java.util.ArrayList;
import java.util.List;

public class InfoWarp implements TabExecutor {
    private WarpDataHandler dataHandler;
    public InfoWarp(WarpDataHandler warpDataHandler) {
        dataHandler = warpDataHandler;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        WarpData warp = null;
        if (args.length >= 1) warp = dataHandler.getWarp(args[0]);

        // If warp does not exist.
        if (warp == null) {
            sender.sendRichMessage("Warp <gray>does not exist.</gray>");
            return true;
        }

        // About text.
        sender.sendRichMessage("<gray>Warp </gray>" + warp.name + "<gray> info:</gray>");
        sender.sendRichMessage("<gray>- Position: </gray>" + String.format("%.2f<gray>,</gray> %.2f<gray>,</gray> %.2f", warp.location.getX(), warp.location.getY(), warp.location.getZ()));
        sender.sendRichMessage("<gray>- World: </gray>" + warp.location.getWorld().getName());
        if (warp.authorName != null) sender.sendRichMessage("<gray>- Author: </gray>" + warp.authorName);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            return dataHandler.getWarps();
        }

        return new ArrayList<String>();
    }
}
