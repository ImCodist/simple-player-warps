package xyz.imcodist.simpleplayerwarps.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WarpDataHandler {
    public ArrayList<WarpData> warps = new ArrayList<>();
    public File warpDataFolder;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public WarpDataHandler(File dataFolder) {
        warpDataFolder = dataFolder;

        loadFiles();
    }

    public void addWarp(WarpData warp) {
        if (warp.author != null) {
            Player player = Bukkit.getPlayer(warp.author);
            if (player != null) warp.authorName = player.getName();
        }

        warps.add(warp);

        WarpData[] add = {warp};
        updateFiles(add, null);
    }

    public void removeWarp(WarpData warp) {
        warps.remove(warp);

        WarpData[] remove = {warp};
        updateFiles(null, remove);
    }

    public WarpData getWarp(String name, CommandSender sender) {
        for (WarpData warp : warps) {
            if (warp.name.equals(name)) {
                if (warp.isPrivate && sender instanceof Player) {
                    Player player = (Player) sender;
                    if (!player.hasPermission("simpleplayerwarps.warps.private") && !player.getUniqueId().equals(warp.author)) {
                        return null;
                    }
                }

                return warp;
            }
        }

        return null;
    }

    public ArrayList<String> getWarps(CommandSender sender) {
        return getWarps(null, false, sender);
    }

    public ArrayList<String> getWarps(Player player, CommandSender sender) {
        String uuid = null;
        if (player != null) uuid = player.getUniqueId().toString();

        return getWarps(uuid, false, sender);
    }

    public ArrayList<String> getWarps(String playerName, CommandSender sender) {
        return getWarps(playerName, true, sender);
    }

    public ArrayList<String> getWarps(String string, boolean username, CommandSender sender) {
        ArrayList<String> playerWarps = new ArrayList<>();

        for (WarpData warp : warps) {
            if (getWarp(warp.name, sender) == null) continue;

            String value2 = warp.authorName;
            if (!username && string != null && warp.author != null) value2 = warp.author.toString();

            if (string == null || string.equals(value2)) {
                playerWarps.add(warp.name);
            }
        }

        return playerWarps;
    }

    public ArrayList<String> getPlayers() {
        ArrayList<String> players = new ArrayList<>();

        for (WarpData warp : warps) {
            if (warp.authorName != null) {
                players.add(warp.authorName);
            }
        }

        return players;
    }

    public boolean canEditWarp(CommandSender sender, WarpData warp, String permission) {
        Player player;

        if (sender instanceof Player) player = (Player) sender;
        else return true;

        if (player.hasPermission(permission)) return true;
        if (player.getUniqueId().equals(warp.author)) return true;

        return false;
    }

    public List<String> getEditableWarps(CommandSender sender, String otherPermission) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
            if (player.hasPermission(otherPermission)) player = null;
        }

        return getWarps(player, sender);
    }

    public void loadFiles() {
        // Make sure warps is empty.
        warps.clear();

        // Load each warp file.
        File[] files = warpDataFolder.listFiles();
        if (files == null) return;

        for (File file : files) {
            loadWarp(file);
        }
    }

    public void updateFiles(WarpData[] add, WarpData[] remove) {
        if (add != null) {
            for (WarpData warp : add) {
                saveWarp(warp);
            }
        }

        if (remove != null) {
            for (WarpData warp : remove) {
                deleteWarp(warp);
            }
        }
    }

    private void saveWarp(WarpData warp) {
        Path path = Paths.get(warpDataFolder + "/" + warp.name + "-" + warp.author + ".json");

        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            }
        } catch (IOException e) {
            Bukkit.getLogger().info(e.toString());
        }

        try {
            FileWriter writer = new FileWriter(path.toFile());

            WarpJsonData jsonData = new WarpJsonData(warp);

            gson.toJson(jsonData, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            Bukkit.getLogger().info(e.toString());
        }
    }

    private void deleteWarp(WarpData warp) {
        Path path = Paths.get(warpDataFolder + "/" + warp.name + "-" + warp.author + ".json");

        try {
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (IOException e) {
            Bukkit.getLogger().info(e.toString());
        }
    }

    private void loadWarp(File file) {
        try {
            BufferedReader reader = Files.newBufferedReader(file.toPath());
            WarpJsonData data = gson.fromJson(reader, WarpJsonData.class);
            reader.close();

            file.delete();
            addWarp(data.toWarp());
        } catch (IOException e) {
            Bukkit.getLogger().info(e.toString());
        }
    }
}
