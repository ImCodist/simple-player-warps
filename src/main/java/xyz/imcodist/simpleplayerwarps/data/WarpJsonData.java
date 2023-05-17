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

    public WarpJsonData(WarpData warp) {
        name = warp.name;

        world = warp.location.getWorld().getName();

        location.add(warp.location.getX());
        location.add(warp.location.getY());
        location.add(warp.location.getZ());
        location.add((double) warp.location.getYaw());
        location.add((double) warp.location.getPitch());

        if (warp.author != null) author = warp.author.toString();
    }

    public WarpData toWarp() {
        WarpData warp = new WarpData();

        warp.name = name;

        Location newLocation = new Location(
                Bukkit.getWorld(world),
                location.get(0),
                location.get(1),
                location.get(2),
                location.get(3).floatValue(),
                location.get(4).floatValue()
        );
        warp.location = newLocation;

        if (author != null) warp.author = UUID.fromString(author);

        return warp;
    }
}
