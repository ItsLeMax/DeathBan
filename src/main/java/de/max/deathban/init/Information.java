package de.max.deathban.init;

import de.max.ilmlib.init.ConfigLib;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Information {
    public static boolean enabled = false;

    /**
     * Erstellt eine Information, ob die Sperre aktiv ist
     * <p>
     * Creates an information if the banning is enabled
     *
     * @return Info, ob Sperre aktiv <p> Info if banning is enabled
     * @author ItsLeMax
     */
    private static String info() {
        String timeUntilBan = convertTimeToText(ConfigLib.getDefaultConfig().getInt("timeUntilBan"), true);
        String banTime = convertTimeToText(ConfigLib.getDefaultConfig().getInt("banTime"), false);
        String replacement = enabled ? ("§c" + ConfigLib.lang("notification.locked")) : ("§a" + ConfigLib.lang("notification.unlocked"));

        return "§3" + ConfigLib.lang("notification.info")
                .replaceFirst("%t%", banTime)
                .replaceFirst("%r%", replacement)
                .replaceFirst("%a%", timeUntilBan)
                .replaceFirst("%c% ", "§7")
                .replaceFirst("%c% ", "§e")
                .replaceFirst("%c% ", "§7")
                .replaceFirst(" %c%", "§7");
    }

    /**
     * Konvertiert eine Zeit zum entsprechenden Text
     * <p>
     * Converts a time to the proper text
     *
     * @param time       Zeit <p> Time
     * @param useSeconds Handelt es sich um Sekunden?
     *                   <p>
     *                   Is is seconds?
     * @return Konvertierte Zeit <p> Converted Time
     * @link <a href="https://stackoverflow.com/questions/11357945/java-convert-seconds-into-day-hour-minute-and-seconds-using-timeunit">StackOverflow</a>
     * @author StackOverflow
     */
    private static String convertTimeToText(int time, boolean useSeconds) {
        TimeUnit unit = useSeconds ? TimeUnit.SECONDS : TimeUnit.MINUTES;
        ArrayList<String> conversion = new ArrayList<>();

        int days = (int) unit.toDays(time);
        long hours = unit.toHours(time) - (days * 24L);
        long minutes = unit.toMinutes(time) - (unit.toHours(time) * 60);
        long seconds = unit.toSeconds(time) - (unit.toMinutes(time) * 60);

        if (days != 0) conversion.add(days + " " + ConfigLib.lang("time.days"));
        if (hours != 0) conversion.add(hours + " " + ConfigLib.lang("time.hours"));
        if (minutes != 0) conversion.add(minutes + " " + ConfigLib.lang("time.minutes"));
        if (seconds != 0) conversion.add(seconds + " " + ConfigLib.lang("time.seconds"));

        return conversion.toString().replace("[", "").replace("]", "");
    }

    /**
     * @see #informPlayers(Player)
     */
    @SuppressWarnings("deprecation")
    public static void informPlayers() {
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(info());
        Bukkit.broadcastMessage("");
    }

    /**
     * Informiert die Spieler über den Status des ToggleDeathBan-Befehls und dementsprechend, ob der Tod eine Sperre verursacht
     * <p>
     * Informs the players about the status of the ToggleDeathBan Command and therefor if death results in a ban
     *
     * @author ItsLeMax
     */
    public static void informPlayers(Player player) {
        player.sendMessage("");
        player.sendMessage(info());
        player.sendMessage("");
    }
}
