package xyz.imcodist.simpleplayerwarps.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.imcodist.simpleplayerwarps.data.WarpData;
import xyz.imcodist.simpleplayerwarps.data.WarpDataHandler;

import java.util.UUID;

public class PlayerJoin implements Listener {
    private final WarpDataHandler dataHandler;

    public PlayerJoin(WarpDataHandler warpDataHandler) {
        dataHandler = warpDataHandler;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        for (WarpData warp : dataHandler.warps) {
            if (warp.author.equals(uuid)) {
                warp.authorName = player.getName();

                WarpData[] add = {warp};
                dataHandler.updateFiles(add, null);
            }
        }
    }
}
