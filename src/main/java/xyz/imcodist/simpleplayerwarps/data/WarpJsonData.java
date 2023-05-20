package xyz.imcodist.simpleplayerwarps.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.UUID;

public class WarpJsonData {
    public String name;
    public String world;
    public ArrayList<Double> location = new ArrayList<>();
    public String author;
    public String authorName;
    public boolean isPrivate;

    public WarpJsonData(WarpData warp) {
        name = warp.name;

        world = warp.location.getWorld().getName();

        location.add(warp.location.getX());
        location.add(warp.location.getY());
        location.add(warp.location.getZ());
        location.add((double) warp.location.getYaw());
        location.add((double) warp.location.getPitch());

        isPrivate = warp.isPrivate;

        if (warp.author != null) author = warp.author.toString();
        if (warp.authorName != null) authorName = warp.authorName;
    }

    public WarpData toWarp() {
        WarpData warp = new WarpData();

        warp.name = name;

        warp.location = new Location(
                Bukkit.getWorld(world),
                location.get(0),
                location.get(1),
                location.get(2),
                location.get(3).floatValue(),
                location.get(4).floatValue()
        );

        warp.isPrivate = isPrivate;

        // TODO: Convert author input manually to UUID's if its a username.
        if (author != null) warp.author = UUID.fromString(author);
        if (authorName != null) warp.authorName = authorName;

        return warp;
    }
}
