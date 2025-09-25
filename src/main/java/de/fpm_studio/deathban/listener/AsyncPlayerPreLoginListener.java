package de.fpm_studio.deathban.listener;

import de.fpm_studio.deathban.DeathBan;
import de.fpm_studio.deathban.util.ConfigHandler;
import de.fpm_studio.deathban.util.MethodHandler;
import de.fpm_studio.ilmlib.libraries.ConfigLib;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.profile.PlayerProfile;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Holds a custom ban screen for people dying and getting temp banned
 *
 * @author ItsLeMax
 * @since 1.0.2
 */
public final class AsyncPlayerPreLoginListener implements Listener {

    private final ConfigLib configLib;

    public AsyncPlayerPreLoginListener(@NotNull final DeathBan instance) {
        configLib = instance.getConfigLib();
    }

    @EventHandler
    public void asyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {

        final BanList<PlayerProfile> banList = Bukkit.getBanList(BanList.Type.PROFILE);
        final PlayerProfile player = Bukkit.getOfflinePlayer(event.getUniqueId()).getPlayerProfile();

        final BanEntry<PlayerProfile> banEntry = banList.getBanEntry(player);

        // Custom ban screen when the player has been banned by the plugin

        if (banEntry == null)
            return;

        if (banEntry.getReason() != null && !banEntry.getReason().equals(ConfigHandler.BAN_REASON))
            return;

        assert banEntry.getExpiration() != null;

        final String timeFormat = configLib.getConfig("config").getString("timeFormat");
        assert timeFormat != null;

        final String dateOfBan = new SimpleDateFormat(timeFormat).format(banEntry.getExpiration());

        final long remainingBanTime = (banEntry.getExpiration().getTime() - new Date().getTime());
        final String remainingBanText = MethodHandler.convertTimeToText((int) remainingBanTime, TimeUnit.MILLISECONDS);

        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, banEntry.getReason() + "\n" + ConfigHandler.WARNING_UNBAN
                .replace("%u%", dateOfBan)
                .replace("%t%", remainingBanText)
        );

    }

}