package xyz.imcodist.simpleplayerwarps.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import xyz.imcodist.simpleplayerwarps.data.WarpData;
import xyz.imcodist.simpleplayerwarps.data.WarpDataHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateWarp implements TabExecutor {
    private WarpDataHandler dataHandler;
    private FileConfiguration config;

    public CreateWarp(WarpDataHandler warpDataHandler, FileConfiguration fileConfig) {
        // Get variables from the plugin class.
        dataHandler = warpDataHandler;
        config = fileConfig;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Create a new warp.
        WarpData warp = new WarpData();

        // Set the name to be the name argument if it exists.
        if (args.length >= 1) warp.name = args[0];

        // Set the location based on the sender.
        // TODO: Let the user input a player to set as.
        if (sender instanceof Player) {
            // Get information from the player.
            Player player = (Player) sender;
            warp.location = player.getLocation();
        } else {
            // Get the information manually.
            Location location = null;
            World world = Bukkit.getServer().getWorlds().get(0);

            // If world was given as an input.
            if (args.length >= 5) world = Bukkit.getWorld(args[4]);

            // Get coordinates from user input.
            if (args.length >= 4) {
                try {
                    location = new Location(
                            world,
                            Double.parseDouble(args[1]),
                            Double.parseDouble(args[2]),
                            Double.parseDouble(args[3])
                    );
                } catch (Exception e) {
                    location = null;
                }
            }

            // Check if location is valid.
            if (location == null) {
                sender.sendRichMessage("<gray>Invalid</gray> location<gray>.</gray>");
                return true;
            }

            // Check if world is valid.
            if (world == null) {
                sender.sendRichMessage("<gray>Invalid</gray> world<gray>.</gray>");
                return true;
            }

            warp.location = location;
        }

        // Set the author based on the sender.
        if (sender instanceof Player) {
            Player player = (Player) sender;
            warp.author = player.getUniqueId();
        }


        // Check if name is valid.
        if (warp.name == null) {
            // The user has not input any arguments.
            sender.sendRichMessage("<gray>No</gray> warp name <gray>has been entered.</gray>");
            return true;
        } else {
            // Check warp name against regex.
            String nameRegex = "[A-Za-z0-9_-]*";

            Pattern pattern = Pattern.compile(nameRegex);
            Matcher matcher = pattern.matcher(warp.name);

            if (!matcher.matches()) {
                // The warp name uses an invalid character.
                sender.sendRichMessage("Warp name <gray>uses invalid character(s).</gray>" + " <gray>[" + matcher.replaceAll("") + "]</gray>");
                return true;
            }
        }


        // If a warp with the same name exists attempt to remove it.
        boolean replaced = false;
        WarpData oldWarp = dataHandler.getWarp(warp.name);
        if (oldWarp != null) {
            if (dataHandler.canEditWarp(sender, oldWarp)) {
                dataHandler.removeWarp(oldWarp);
                replaced = true;
            } else {
                sender.sendRichMessage("<gray>Another</gray> warp <gray>exists named</gray> " + warp.name + "<gray>.</gray>");
                return true;
            }
        }

        // Check if player has too many warps.
        int maxPlayerWarps = config.getInt("maxPlayerWarps");
        if (maxPlayerWarps >= 0 && !replaced) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                ArrayList<String> playerWarps = dataHandler.getWarps(player);

                if (playerWarps.size() >= maxPlayerWarps && !player.hasPermission("simpleplayerwarps.setwarp.infinite")) {
                    sender.sendRichMessage("Maximum <gray>amount of</gray> warps <gray>reached.</gray>" + " <gray>(" + playerWarps.size() + " / " + maxPlayerWarps + ")</gray>");
                    return true;
                }
            }
        }


        // Add the warp to the data handler.
        String messagePrefix = "Created";
        if (replaced) messagePrefix = "Replaced";

        dataHandler.addWarp(warp);
        sender.sendRichMessage("<gray>" + messagePrefix + "</gray> warp <gray>named</gray> " + warp.name + "<gray>.</gray>");

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return new ArrayList<String>();
    }
}
