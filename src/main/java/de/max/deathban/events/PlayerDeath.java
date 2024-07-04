package de.max.deathban.events;

import de.max.deathban.init.DeathBan;
import de.max.deathban.init.Information;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.UUID;

import static de.max.deathban.init.DeathBan.configLib;

public class PlayerDeath implements Listener {
    static HashMap<UUID, Integer> tasks = new HashMap<>();

    @EventHandler
    public static void playerDeath(PlayerDeathEvent event) {
        if (!Information.enabled) return;

        Player player = event.getPlayer();
        if (tasks.containsKey(player.getUniqueId())) return;

        final int[] timer = {20 * configLib.getConfig("config").getInt("timeUntilBan")};
        player.sendMessage("");
        player.sendMessage("§c§l" + configLib.lang("warning.death"));
        player.sendMessage("§3" + configLib.lang("warning.explanation"));
        player.sendMessage("");

        tasks.put(player.getUniqueId(), Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(DeathBan.plugin, () -> {
            timer[0] = timer[0] - 20;

            switch (timer[0]) {
                case 20 * 60 * 3:
                    player.sendMessage("");
                    player.sendMessage("§c§l" + configLib.lang("warning.update") + ":");
                    player.sendMessage("§3" + configLib.lang("warning.threeMinutes"));
                    player.sendMessage("");
                    break;
                case 20 * 60:
                    player.sendMessage("");
                    player.sendMessage("§c§l" + configLib.lang("warning.update") + ":");
                    player.sendMessage("§3" + configLib.lang("warning.oneMinute"));
                    player.sendMessage("");
                    break;
            }

            if (timer[0] <= 0) {
                player.ban("§c" + configLib.lang("warning.ban"), Instant.now().plus(12, ChronoUnit.HOURS), "Plugin", true);

                Bukkit.getScheduler().cancelTask(tasks.get(player.getUniqueId()));
                tasks.remove(player.getUniqueId());

                Bukkit.getConsoleSender().sendMessage("§c" + configLib.lang("warning.console").replace("%r%", player.getName()));
            }
        }, 0, 20));
    }
}