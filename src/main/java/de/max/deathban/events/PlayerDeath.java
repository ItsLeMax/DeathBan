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

public class PlayerDeath implements Listener {
    static HashMap<UUID, Integer> tasks = new HashMap<>();

    @EventHandler
    public static void playerDeath(PlayerDeathEvent event) {
        if (!Information.enabled) return;

        Player player = event.getPlayer();
        if (tasks.containsKey(player.getUniqueId())) return;

        final int[] timer = {20 * 60 * 5};
        player.sendMessage("");
        player.sendMessage("§c§lAchtung! Du wirst in 5 Minuten gesperrt!");
        player.sendMessage("§3Durch deinen Tod wirst du für 12 Stunden gesperrt.");
        player.sendMessage("");

        tasks.put(player.getUniqueId(), Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(DeathBan.plugin, () -> {
            timer[0] = timer[0] - 20;

            switch (timer[0]) {
                case 20 * 60 * 3:
                    player.sendMessage("");
                    player.sendMessage("§c§lUpdate:");
                    player.sendMessage("§3Du wirst in 3 Minuten gesperrt.");
                    player.sendMessage("");
                    break;
                case 20 * 60:
                    player.sendMessage("");
                    player.sendMessage("§c§lUpdate:");
                    player.sendMessage("§3Du wirst in einer Minute gesperrt.");
                    player.sendMessage("");
                    break;
            }

            if (timer[0] <= 0) {
                player.ban("§cDurch deinen letzten Tod bist du für 12 Stunden gesperrt." + "\n" + "§7Sollte der Tod ungerecht gewesen sein, kannst du gerne über den Discord (§e8hnXeggWP9§7) um eine Entbannung bitten.", Instant.now().plus(12, ChronoUnit.HOURS), "Plugin", true);
                Bukkit.getScheduler().cancelTask(tasks.get(player.getUniqueId()));
                tasks.remove(player.getUniqueId());
            }
        }, 0, 20));
    }
}