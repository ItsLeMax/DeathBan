package de.max.deathban.events;

import com.destroystokyo.paper.profile.PlayerProfile;
import de.max.deathban.init.DeathBan;
import io.papermc.paper.ban.BanListType;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import static de.max.deathban.init.DeathBan.configLib;

public class AsyncPlayerPreLogin implements Listener {
    @EventHandler
    @SuppressWarnings("deprecation")
    public void asyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        BanList<PlayerProfile> banList = Bukkit.getBanList(BanListType.PROFILE);
        PlayerProfile player = event.getPlayerProfile();

        if (!banList.isBanned(player)) {
            return;
        }

        BanEntry<PlayerProfile> banEntry = banList.getBanEntry(player);
        assert banEntry != null;

        if (banEntry.getReason() != null && !banEntry.getReason().equals(DeathBan.banReason)) {
            return;
        }

        assert banEntry.getExpiration() != null;

        // Möchte schon noch die Fragezeichen gegen eine Entbannungszeit ersetzen. xD
        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, banEntry.getReason() + "\n" + configLib.lang("warning.unban")
                .replace("%t%", "???")
                .replace("%u%", banEntry.getExpiration().toString()) + "."
        );
    }
}