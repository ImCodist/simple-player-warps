package xyz.imcodist.simpleplayerwarps.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import xyz.imcodist.simpleplayerwarps.data.WarpData;
import xyz.imcodist.simpleplayerwarps.data.WarpDataHandler;

import java.util.ArrayList;
import java.util.List;

public class ListWarps implements TabExecutor {
    private WarpDataHandler dataHandler;

    public ListWarps(WarpDataHandler warpDataHandler) {
        dataHandler = warpDataHandler;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Get player if the sender input a player.
        // TODO: Add a way to get warps made by the console.
        Player player = null;
        if (args.length >= 1) {
            player = Bukkit.getPlayer(args[0]);

            if (player == null) {
                sender.sendRichMessage("<gray>Could not find</gray> player<gray> named</gray> " + args[0] + "<gray>.</gray>");
                return true;
            }
        }

        String string = "";
        ArrayList<String> warps = dataHandler.getWarps(player);

        // Display a list of warps.
        // TODO: Sort alphabetically.
        int i = 0;
        for (String warp : warps) {
            string += "<hover:show_text:'<gray>Click to</gray> warp <gray>to</gray> " + warp + "<gray>.</gray>'><click:run_command:/warp " + warp + ">" + warp + "</click></hover>";
            if (i < warps.size() - 1) string += "<gray>, </gray>";

            i++;
        }

        sender.sendRichMessage("<gray>[</gray>" + string + "<gray>]</gray>");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length <= 1) return null;
        return new ArrayList<String>();
    }
}
