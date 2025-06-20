package de.fpm_studio.deathban.listener;

import de.fpm_studio.deathban.DeathBan;
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
public final class PlayerJoinListener implements Listener {

    private final DeathBan instance;

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        // Simple information about the ban state

        final String info = instance.getMethodHandler().info();

        player.sendMessage("");
        player.sendMessage(info);
        player.sendMessage("");

    }

}