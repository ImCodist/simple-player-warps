package xyz.imcodist.simpleplayerwarps.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import xyz.imcodist.simpleplayerwarps.data.WarpData;
import xyz.imcodist.simpleplayerwarps.data.WarpDataHandler;

import java.util.ArrayList;
import java.util.List;

public class TeleportWarp implements TabExecutor {
    private WarpDataHandler dataHandler;

    public TeleportWarp(WarpDataHandler warpDataHandler) {
        dataHandler = warpDataHandler;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        WarpData warp = null;
        if (args.length >= 1) warp = dataHandler.getWarp(args[0]);

        // Make sure the warp exists.
        if (warp == null) {
            sender.sendRichMessage("<gray>No</gray> warp <gray>named</gray> " + args[0] + " <gray>exists.</gray>");
            return true;
        }

        // Teleport the player.
        // TODO: Add player to be teleported as an argument as well for console.
        if (sender instanceof Player) {
            Player player = (Player) sender;

            player.teleport(warp.location);
            sender.sendRichMessage("<gray>Warped</gray> " + sender.getName() + " <gray>to</gray> " + warp.name + "<gray>.</gray>");
        }

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
