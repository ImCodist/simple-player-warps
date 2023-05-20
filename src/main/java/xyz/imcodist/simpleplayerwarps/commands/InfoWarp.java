package xyz.imcodist.simpleplayerwarps.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import xyz.imcodist.simpleplayerwarps.data.WarpData;
import xyz.imcodist.simpleplayerwarps.data.WarpDataHandler;

import java.util.ArrayList;
import java.util.List;

public class InfoWarp implements TabExecutor {
    private final WarpDataHandler dataHandler;
    public InfoWarp(WarpDataHandler warpDataHandler) {
        dataHandler = warpDataHandler;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        WarpData warp = null;
        if (args.length >= 1) warp = dataHandler.getWarp(args[0], sender);

        // If warp does not exist.
        if (warp == null) {
            sender.sendRichMessage("<gray>No</gray> warp <gray>named</gray> " + args[0] + " <gray>exists.</gray>");
            return true;
        }

        // About text.
        sender.sendRichMessage("Information <gray>on warp</gray> " + warp.name + "<gray>:</gray>");
        sender.sendRichMessage("<gray>- Position: </gray>" + String.format("%.2f<gray>,</gray> %.2f<gray>,</gray> %.2f", warp.location.getX(), warp.location.getY(), warp.location.getZ()));
        sender.sendRichMessage("<gray>- World: </gray>" + warp.location.getWorld().getName());

        if (warp.authorName != null) sender.sendRichMessage("<gray>- Author: </gray>" + warp.authorName);
        else if (warp.author != null) sender.sendRichMessage("<gray>- Author: </gray>" + warp.author);

        sender.sendRichMessage("<gray>- Private: </gray>" + warp.isPrivate);

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 1) {
            return dataHandler.getWarps(sender);
        }

        return new ArrayList<>();
    }
}
