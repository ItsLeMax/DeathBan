package de.fpm_studio.deathban.events;

import com.destroystokyo.paper.profile.PlayerProfile;
import de.fpm_studio.deathban.DeathBan;
import de.fpm_studio.deathban.data.GlobalVariables;
import de.fpm_studio.deathban.util.MethodHandler;
import de.fpm_studio.ilmlib.libraries.ConfigLib;
import io.papermc.paper.ban.BanListType;
import lombok.AllArgsConstructor;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Holds a custom ban screen for people dying and getting temp banned
 *
 * @author ItsLeMax
 * @since 1.0.2
 */
@AllArgsConstructor
public final class AsyncPlayerPreLogin implements Listener {

    private final DeathBan instance;

    @EventHandler
    @SuppressWarnings("deprecation")
    public void asyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {

        final ConfigLib configLib = instance.getConfigLib();
        final MethodHandler methodHandler = instance.getMethodHandler();

        final BanList<PlayerProfile> banList = Bukkit.getBanList(BanListType.PROFILE);
        final PlayerProfile player = event.getPlayerProfile();

        final BanEntry<PlayerProfile> banEntry = banList.getBanEntry(player);

        // Custom ban screen when the player has been banned by the plugin

        if (banEntry == null)
            return;

        if (banEntry.getReason() != null && !banEntry.getReason().equals(GlobalVariables.banReason))
            return;

        assert banEntry.getExpiration() != null;

        final String timeFormat = configLib.getConfig("config").getString("timeFormat");
        assert timeFormat != null;

        final String dateOfBan = new SimpleDateFormat(timeFormat).format(banEntry.getExpiration());

        final long remainingBanTime = (banEntry.getExpiration().getTime() - new Date().getTime());
        final String remainingBanText = methodHandler.convertTimeToText((int) remainingBanTime, TimeUnit.MILLISECONDS);

        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, banEntry.getReason() + "\n" + configLib.text("warning.unban")
                .replace("%u%", dateOfBan)
                .replace("%t%", remainingBanText)
        );

    }

}