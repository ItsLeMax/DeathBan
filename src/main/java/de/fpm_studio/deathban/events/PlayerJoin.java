package de.fpm_studio.deathban.events;

import de.fpm_studio.deathban.util.Methods;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    @EventHandler
    public static void playerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.sendMessage("");
        player.sendMessage(Methods.info());
        player.sendMessage("");
    }
}