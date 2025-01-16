package de.max.deathban.events;

import de.max.deathban.init.Methods;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    @EventHandler
    public static void playerJoin(PlayerJoinEvent e) {
        Methods.informPlayers(e.getPlayer());
    }
}