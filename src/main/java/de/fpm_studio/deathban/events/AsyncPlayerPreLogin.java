package de.fpm_studio.deathban.events;

import com.destroystokyo.paper.profile.PlayerProfile;
import de.fpm_studio.deathban.data.GlobalVariables;
import de.fpm_studio.ilmlib.libraries.ConfigLib;
import io.papermc.paper.ban.BanListType;
import lombok.AllArgsConstructor;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

/**
 * Holds a custom ban screen for people dying and getting temp banned
 *
 * @author ItsLeMax
 * @since 1.0.2
 */
@AllArgsConstructor
public final class AsyncPlayerPreLogin implements Listener {

    private final ConfigLib configLib;

    @EventHandler
    @SuppressWarnings("deprecation")
    public void asyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {

        final BanList<PlayerProfile> banList = Bukkit.getBanList(BanListType.PROFILE);
        final PlayerProfile player = event.getPlayerProfile();

        final BanEntry<PlayerProfile> banEntry = banList.getBanEntry(player);

        // Custom ban screen when the player has been banned by the plugin

        if (banEntry == null)
            return;

        if (banEntry.getReason() != null && !banEntry.getReason().equals(GlobalVariables.banReason))
            return;

        assert banEntry.getExpiration() != null;

        // Möchte schon noch die Fragezeichen gegen eine Entbannungszeit ersetzen. xD

        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, banEntry.getReason() + "\n" + configLib.text("warning.unban")
                .replace("%t%", "???")
                .replace("%u%", banEntry.getExpiration().toString()) + "."
        );

    }

}