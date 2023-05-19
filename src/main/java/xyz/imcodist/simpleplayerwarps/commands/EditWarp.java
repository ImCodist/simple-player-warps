package xyz.imcodist.simpleplayerwarps.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.imcodist.simpleplayerwarps.data.WarpData;
import xyz.imcodist.simpleplayerwarps.data.WarpDataHandler;

import java.util.*;

public class EditWarp implements TabExecutor {
    private final WarpDataHandler dataHandler;

    public final HashMap<String, Boolean> propertys = new HashMap<>();

    public EditWarp(WarpDataHandler warpDataHandler) {
        dataHandler = warpDataHandler;

        // TODO: Let users configure if an option is considered advanced or not.
        propertys.put("name", false);
        propertys.put("location", true);
        propertys.put("world", true);
        propertys.put("author", true);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        // TODO: Don't have this code just be a copy of RemoveWarp.

        // Check if a warp name has been entered.
        if (args.length < 1) {
            sender.sendRichMessage("<gray>No</gray> warp name <gray>has been entered.</gray>");
            return true;
        }

        // Get the warp and return if it doesn't exist.
        WarpData warp = dataHandler.getWarp(args[0]);
        if (warp == null) {
            sender.sendRichMessage("<gray>No</gray> warp <gray>named</gray> " + args[0] + " <gray>exists.</gray>");
            return true;
        }

        // Check if the sender is able to edit the warp.
        if (!dataHandler.canEditWarp(sender, warp, "simpleplayerwarps.warpedit.others")) {
            sender.sendRichMessage("<gray>You</gray> don't have permission <gray>to edit warps you don't own.</gray>");
            return true;
        }

        if (args.length >= 3) {
            // Property and Value given
            String property = args[1];
            List<String> values = Arrays.asList(args).subList(2, args.length);

            WarpData oldWarp = new WarpData();
            oldWarp.name = warp.name;
            oldWarp.author = warp.author;

            String couldNotSetMessage = null;
            switch (property) {
                case ("name"):
                    if (warp.isValidName(values.get(0))) warp.name = values.get(0);
                    else couldNotSetMessage = "Name uses invalid character(s)";
                    break;
                case ("location"):
                    // TODO: limit how far the player can set the location so they don't put -100000000000000000000000 or some shit
                    if (values.size() >= 3) {
                        try {
                            warp.location = new Location(
                                    warp.location.getWorld(),
                                    Double.parseDouble(values.get(0)),
                                    Double.parseDouble(values.get(1)),
                                    Double.parseDouble(values.get(2))
                            );
                        }
                        catch (Exception e) {
                            couldNotSetMessage = "Invalid position";
                        }
                    }
                    else couldNotSetMessage = "Missing full position (x y z)";
                    break;
                case ("author"):
                    Player player = Bukkit.getPlayer(values.get(0));

                    if (player != null) {
                        warp.author = player.getUniqueId();
                        warp.authorName = player.getName();
                    } else {
                        try {
                            warp.author = UUID.fromString(values.get(0));
                            warp.authorName = null;
                        } catch (Exception ignored) {
                            couldNotSetMessage = "Author is not valid, trying using the players UUID";
                        }
                    }
                    break;
                case ("world"):
                    World world = Bukkit.getWorld(values.get(0));

                    if (world != null) warp.location.setWorld(world);
                    else couldNotSetMessage = "World does not exist";
                    break;
            }

            if (couldNotSetMessage == null) {
                String valueDisplay = getPropertyDisplayValue(warp, property);
                sender.sendRichMessage("<gray>Set</gray> " + property + " <gray>of</gray> " + oldWarp.name + " <gray>to</gray> " + valueDisplay + "<gray>.</gray>");

                WarpData[] remove = {oldWarp};
                dataHandler.updateFiles(null, remove);

                WarpData[] add = {warp};
                dataHandler.updateFiles(add, null);
            }
            else {
                if (getPropertyList(sender).contains(property)) {
                    sender.sendRichMessage("<gray>Could not set property</gray> " + property + " <gray>to</gray> " + values + "<gray>. (" + couldNotSetMessage + ")</gray>");
                } else {
                    sender.sendRichMessage("<gray>Property</gray> " + property + " <gray>does not exist.</gray>");
                }
            }
        } else if (args.length == 2) {
            // Property given
            String property = args[1];
            String valueDisplay = getPropertyDisplayValue(warp, property);

            if (getPropertyList(sender).contains(property)) {
                sender.sendRichMessage("<gray>Property</gray> " + property + " <gray>of</gray> " + warp.name + " <gray>is</gray> " + valueDisplay + "<gray>.</gray>");
            } else {
                sender.sendRichMessage("<gray>Property</gray> " + property + " <gray>does not exist.</gray>");
            }
        } else {
            // Only warp given
            ArrayList<String> propertyList = getPropertyList(sender);
            if (propertyList.size() > 0) {
                sender.sendRichMessage("Editable propertys <gray>of warp</gray> " + warp.name + "<gray>:</gray>");

                for (String property : propertyList) {
                    String valueDisplay = getPropertyDisplayValue(warp, property);
                    String commandInsert = "/warpedit " + warp.name + " " + property;

                    String suffix = "";
                    if (propertys.get(property)) suffix = " ★";

                    sender.sendRichMessage(String.format("<hover:show_text:'%s'><click:suggest_command:%s ><gray>- %s%s:</gray> %s</click></hover>", commandInsert, commandInsert, property, suffix, valueDisplay));
                }
            } else {
                sender.sendRichMessage("<gray>There are</gray> no editable propertys <gray>of warp</gray> " + warp.name + "<gray>.</gray>");
            }
        }

        return true;
    }

    public String getPropertyDisplayValue(WarpData warp, String property) {
        String valueDisplay = "N/A";
        switch (property) {
            case ("name"): valueDisplay = warp.name; break;
            case ("location"): valueDisplay = String.format("%.2f, %.2f, %.2f", warp.location.getX(), warp.location.getY(), warp.location.getZ()); break;
            case ("author"):
                Player player = null;
                if (warp.author != null) player = Bukkit.getPlayer(warp.author);

                if (player != null) valueDisplay = player.getName();
                else if (warp.authorName != null) valueDisplay = warp.authorName;
                else if (warp.author != null) valueDisplay = warp.author.toString();
                break;
            case ("world"): valueDisplay = warp.location.getWorld().getName(); break;
        }

        return valueDisplay;
    }

    private boolean canEditAdvanced(CommandSender sender) {
        boolean canAdvanced = true;
        if (sender instanceof Player) {
            Player player = (Player) sender;
            canAdvanced = player.hasPermission("simpleplayerwarps.warpedit.advanced");
        }

        return canAdvanced;
    }

    private ArrayList<String> getPropertyList(CommandSender sender) {
        boolean canAdvanced = canEditAdvanced(sender);
        ArrayList<String> propertyList = new ArrayList<>();

        for (String property : propertys.keySet()) {
            boolean advanced = propertys.get(property);
            if (advanced && !canAdvanced) continue;

            propertyList.add(property);
        }

        return propertyList;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        // Only show warps sender can edit.
        if (args.length == 1) return dataHandler.getEditableWarps(sender, "simpleplayerwarps.warpedit.others");
        if (args.length == 2) return getPropertyList(sender);
        return new ArrayList<>();
    }
}
