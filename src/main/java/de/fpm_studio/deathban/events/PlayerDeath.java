package de.fpm_studio.deathban.events;

import de.fpm_studio.deathban.DeathBan;
import de.fpm_studio.deathban.util.Methods;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.UUID;

import static de.fpm_studio.deathban.DeathBan.configLib;

public class PlayerDeath implements Listener {
    static HashMap<UUID, Integer> bansInProcess = new HashMap<>();

    @EventHandler
    public static void playerDeath(PlayerDeathEvent event) {
        if (!DeathBan.banEnabled) {
            return;
        }

        FileConfiguration config = configLib.getConfig("config");
        Player player = event.getPlayer();

        if (bansInProcess.containsKey(player.getUniqueId())) {
            if (config.getBoolean("secondDeathImmediate")) {
                getHisAss(player);
            }

            return;
        }

        int timeUntilBan = config.getInt("timeUntilBan");
        int[] timer = {20 * timeUntilBan};

        if (timeUntilBan != 0) {
            player.sendMessage("");
            player.sendMessage("§c§l" + configLib.lang("warning.death").replace("%a%", DeathBan.timeUntilBan));
            player.sendMessage("§3" + configLib.lang("warning.explanation").replace("%t%", DeathBan.banTime));
            player.sendMessage("");
        }

        bansInProcess.put(player.getUniqueId(), Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(DeathBan.plugin, () -> {
            timer[0] = timer[0] - 20;

            for (int minute : config.getIntegerList("reminders")) {
                if (timer[0] == 20 * 60 * minute) {
                    player.sendMessage("");
                    player.sendMessage("§c§l" + configLib.lang("warning.update") + ":");
                    player.sendMessage("§3" + configLib.lang("warning.timeUntilBan").replace("%a%", Methods.convertTimeToText(timer[0] / 20, true)));
                    player.sendMessage("");
                }
            }

            if (timer[0] <= 0) {
                getHisAss(player);
            }
        }, 0, 20));
    }

    /**
     * Bannt dem Spieler vom Server und informiert die Konsole
     * <p>
     * Bans the player from the server and informs the console
     */
    @SuppressWarnings("all")
    private static void getHisAss(Player player) {
        player.ban(DeathBan.banReason,
                Instant.now().plus((int) configLib.getConfig("config").get("banTime"), ChronoUnit.MINUTES),
                "Plugin",
                true
        );

        Bukkit.getScheduler().cancelTask(bansInProcess.get(player.getUniqueId()));
        bansInProcess.remove(player.getUniqueId());

        Bukkit.getConsoleSender().sendMessage("§c" + configLib.lang("warning.console").replace("%r%", player.getName()));
    }
}