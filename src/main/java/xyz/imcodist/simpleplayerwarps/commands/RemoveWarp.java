package xyz.imcodist.simpleplayerwarps.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import xyz.imcodist.simpleplayerwarps.data.WarpData;
import xyz.imcodist.simpleplayerwarps.data.WarpDataHandler;

import java.util.ArrayList;
import java.util.List;

public class RemoveWarp implements TabExecutor {
    private final WarpDataHandler dataHandler;

    public RemoveWarp(WarpDataHandler warpDataHandler) {
        dataHandler = warpDataHandler;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        // Check if a warp name has been entered.
        if (args.length < 1) {
            sender.sendRichMessage("<gray>No</gray> warp name <gray>has been entered.</gray>");
            return true;
        }

        // Get the warp and return if it doesn't exist.
        WarpData warp = dataHandler.getWarp(args[0]);
        if (warp == null) {
            sender.sendRichMessage("<gray>No</gray> warp <gray>with that name exists.</gray>");
            return true;
        }

        // Check if the sender is able to edit the warp.
        if (!dataHandler.canEditWarp(sender, warp, "simpleplayerwarps.warpdel.others")) {
            sender.sendRichMessage("<gray>You</gray> cannot <gray>delete a warp you do not own.</gray>");
            return true;
        }

        // Delete the warp.
        dataHandler.removeWarp(warp);
        sender.sendRichMessage("<gray>Deleted</gray> warp <gray>named</gray> " + args[0] + "<gray>.</gray>");

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        // Only show warps sender can edit.
        if (args.length == 1) return dataHandler.getEditableWarps(sender, "simpleplayerwarps.warpdel.others");
        return new ArrayList<>();
    }
}
