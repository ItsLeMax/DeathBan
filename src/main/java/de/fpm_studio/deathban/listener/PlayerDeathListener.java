package de.fpm_studio.deathban.listener;

import de.fpm_studio.deathban.DeathBan;
import de.fpm_studio.deathban.util.ConfigHandler;
import de.fpm_studio.deathban.util.MethodHandler;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Contains player death processes
 *
 * @author ItsLeMax
 * @since 1.0.0
 */
public final class PlayerDeathListener implements Listener {

    private final DeathBan instance;

    public PlayerDeathListener(@NotNull final DeathBan instance) {

        this.instance = instance;

        final FileConfiguration config = instance.getConfig();

        SECOND_DEATH_IMMEDIATE = config.getBoolean("secondDeathImmediate");
        TIME_UNTIL_BAN = config.getInt("timeUntilBan");
        REMINDERS = config.getIntegerList("reminders");
        BAN_TIME = config.get("banTime");

    }

    private static boolean SECOND_DEATH_IMMEDIATE;
    private static int TIME_UNTIL_BAN;
    private static List<Integer> REMINDERS;
    private static Object BAN_TIME;

    private static final Map<UUID, Integer> BANS_IN_PROCESS = new HashMap<>();

    @EventHandler
    public void playerDeath(PlayerDeathEvent event) {

        if (!ConfigHandler.BAN_ENABLED)
            return;

        final Player player = event.getEntity();

        // Ban player immediately on second death if enabled inside the config

        if (BANS_IN_PROCESS.containsKey(player.getUniqueId())) {

            if (SECOND_DEATH_IMMEDIATE)
                getHisAss(player);

            return;

        }

        final AtomicInteger timer = new AtomicInteger(20 * TIME_UNTIL_BAN);

        // Send ban notice if the ban will not be immediate

        if (TIME_UNTIL_BAN != 0) {

            player.sendMessage("");
            player.sendMessage("§c§l" + ConfigHandler.WARNING_DEATH.replace("%a%", ConfigHandler.TIME_UNTIL_BAN));
            player.sendMessage("§3" + ConfigHandler.WARNING_EXPLANATION.replace("%t%", ConfigHandler.BAN_TIME));
            player.sendMessage("");

        }

        BANS_IN_PROCESS.put(player.getUniqueId(), Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(instance, () -> {

            timer.set(timer.get() - 20);

            for (final int minute : REMINDERS) {

                // Repeating hints about the ban with a certain amount of time left

                if (timer.get() == 20 * 60 * minute) {

                    final String time = MethodHandler.convertTimeToText(timer.get() / 20, TimeUnit.SECONDS);

                    player.sendMessage("");
                    player.sendMessage("§c§l" + ConfigHandler.WARNING_UPDATE + ":");
                    player.sendMessage("§3" + ConfigHandler.WARNING_TIME_UNTIL_BAN.replace("%a%", time));
                    player.sendMessage("");

                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);

                }

            }

            // Final ban

            if (timer.get() <= 0)
                getHisAss(player);

        }, 0, 20));

    }

    /**
     * Bans the player from the server and informs the console
     *
     * @param player Player to ban
     *
     * @author ItsLeMax
     * @since Code: 1.0.0 <br> Method: 1.0.1
     */
    @SuppressWarnings("all")
    private void getHisAss(@NotNull final Player player) {

        final Instant timeOfUnban = Instant.now().plus((int) BAN_TIME, ChronoUnit.MINUTES);

        player.ban(ConfigHandler.BAN_REASON, timeOfUnban, "Plugin", true);

        // Clear memory from unused data

        Bukkit.getScheduler().cancelTask(BANS_IN_PROCESS.get(player.getUniqueId()));
        BANS_IN_PROCESS.remove(player.getUniqueId());

        Bukkit.getConsoleSender().sendMessage("§c" + ConfigHandler.WARNING_CONSOLE.replace("%r%", player.getName()));

    }

}