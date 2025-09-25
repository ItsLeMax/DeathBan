package de.fpm_studio.deathban.listener;

import de.fpm_studio.deathban.util.MethodHandler;
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
public final class PlayerJoinListener implements Listener {

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {

        final Player player = event.getPlayer();

        // Simple information about the ban state

        player.sendMessage("");
        player.sendMessage(MethodHandler.info());
        player.sendMessage("");

    }

}