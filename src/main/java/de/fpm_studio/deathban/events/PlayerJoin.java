package de.fpm_studio.deathban.events;

import de.fpm_studio.deathban.util.MethodHandler;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Contains the player join event, informing players about the plugins status
 *
 * @author ItsLeMax
 * @since 1.0.0
 */
@AllArgsConstructor
public final class PlayerJoin implements Listener {

    private final MethodHandler methodHandler;

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        // Simple information about the ban state

        player.sendMessage("");
        player.sendMessage(methodHandler.info());
        player.sendMessage("");

    }

}