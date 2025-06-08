package de.fpm_studio.deathban.events;

import de.fpm_studio.deathban.DeathBan;
import de.fpm_studio.deathban.data.GlobalVariables;
import de.fpm_studio.deathban.util.MethodHandler;
import de.fpm_studio.ilmlib.libraries.ConfigLib;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Contains player death processes
 *
 * @author ItsLeMax
 * @since 1.0.0
 */
@RequiredArgsConstructor
public final class PlayerDeath implements Listener {

    private final DeathBan instance;

    private final HashMap<UUID, Integer> bansInProcess = new HashMap<>();

    @EventHandler
    public void playerDeath(PlayerDeathEvent event) {

        if (!GlobalVariables.banEnabled)
            return;

        final ConfigLib configLib = instance.getConfigLib();
        final MethodHandler methodHandler = instance.getMethodHandler();

        final FileConfiguration config = configLib.getConfig("config");
        final Player player = event.getPlayer();

        // Ban player immediately on second death if enabled inside the config

        if (bansInProcess.containsKey(player.getUniqueId())) {

            if (config.getBoolean("secondDeathImmediate")) {
                getHisAss(player);
            }

            return;

        }

        final int timeUntilBan = config.getInt("timeUntilBan");
        final int[] timer = {20 * timeUntilBan};

        // Send ban notice if the ban will not be immediate

        if (timeUntilBan != 0) {

            player.sendMessage("");
            player.sendMessage("§c§l" + configLib.text("warning.death").replace("%a%", GlobalVariables.timeUntilBan));
            player.sendMessage("§3" + configLib.text("warning.explanation").replace("%t%", GlobalVariables.banTime));
            player.sendMessage("");

        }

        bansInProcess.put(player.getUniqueId(), Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(instance, () -> {
            timer[0] = timer[0] - 20;

            for (final int minute : config.getIntegerList("reminders")) {

                // Repeating hints about the ban with a certain amount of time left

                if (timer[0] == 20 * 60 * minute) {

                    player.sendMessage("");
                    player.sendMessage("§c§l" + configLib.text("warning.update") + ":");

                    player.sendMessage("§3" + configLib.text("warning.timeUntilBan")
                            .replace("%a%", methodHandler.convertTimeToText(timer[0] / 20, TimeUnit.SECONDS))
                    );

                    player.sendMessage("");

                }
            }

            // Final ban

            if (timer[0] <= 0) {
                getHisAss(player);
            }

        }, 0, 20));

    }

    /**
     * Bans the player from the server and informs the console
     *
     * @param player Player to ban
     * @author ItsLeMax
     * @since Code: 1.0.0 <br> Method: 1.0.1
     */
    @SuppressWarnings("all")
    private void getHisAss(@NotNull final Player player) {

        final ConfigLib configLib = instance.getConfigLib();
        final MethodHandler methodHandler = instance.getMethodHandler();

        final Instant timeOfUnban = Instant.now().plus((int) configLib.getConfig("config").get("banTime"), ChronoUnit.MINUTES);

        player.ban(GlobalVariables.banReason, timeOfUnban, "Plugin", true);

        // Clear memory from unused data

        Bukkit.getScheduler().cancelTask(bansInProcess.get(player.getUniqueId()));
        bansInProcess.remove(player.getUniqueId());

        Bukkit.getConsoleSender().sendMessage("§c" + configLib.text("warning.console").replace("%r%", player.getName()));

    }

}