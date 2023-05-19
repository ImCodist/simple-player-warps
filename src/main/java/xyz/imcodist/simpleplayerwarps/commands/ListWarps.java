package xyz.imcodist.simpleplayerwarps.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import xyz.imcodist.simpleplayerwarps.data.WarpDataHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListWarps implements TabExecutor {
    private final WarpDataHandler dataHandler;

    public ListWarps(WarpDataHandler warpDataHandler) {
        dataHandler = warpDataHandler;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        // Get player if the sender input a player.
        // TODO: Add a way to get warps made by the console.
        StringBuilder string = new StringBuilder();

        String name = null;
        if (args.length >= 1) name = args[0];

        ArrayList<String> warps = dataHandler.getWarps(name);
        if (warps.isEmpty()) {
            if (name != null) sender.sendRichMessage("Player<gray> named</gray> " + args[0] + "<gray> has no warps set.</gray>");
            else sender.sendRichMessage("<gray>No</gray> warps <gray>have been set.</gray>");

            return true;
        }

        Collections.sort(warps);

        // Display a list of warps.
        int i = 0;
        for (String warp : warps) {
            string.append("<hover:show_text:'<gray>Click to</gray> warp <gray>to</gray> ").append(warp).append("<gray>.</gray>'><click:run_command:/warp ").append(warp).append(">").append(warp).append("</click></hover>");
            if (i < warps.size() - 1) string.append("<gray>, </gray>");

            i++;
        }

        if (i > 0) sender.sendRichMessage("<gray>[</gray>" + string + "<gray>]</gray>");
        else sender.sendRichMessage("<gray>No</gray> warps <gray>found.</gray>");

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length <= 1) return dataHandler.getPlayers();
        return new ArrayList<>();
    }
}
