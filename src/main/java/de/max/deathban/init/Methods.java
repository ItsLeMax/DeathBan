package de.max.deathban.init;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static de.max.deathban.init.DeathBan.configLib;

public class Methods {
    /**
     * Konvertiert eine Zeit zum entsprechenden Text
     * <p>
     * Converts a time to the proper text
     *
     * @param time            Zeit <p> Time
     * @param providedSeconds Wurden Sekunden statt Minuten übergeben?
     *                        <p>
     *                        Were seconds instead of minutes given?
     * @return Konvertierte Zeit <p> Converted Time
     * @link <a href="https://stackoverflow.com/questions/11357945/java-convert-seconds-into-day-hour-minute-and-seconds-using-timeunit">StackOverflow</a>
     * @author StackOverflow, ItsLeMax
     */
    public static String convertTimeToText(int time, boolean providedSeconds) {
        TimeUnit unit = providedSeconds ? TimeUnit.SECONDS : TimeUnit.MINUTES;
        ArrayList<String> conversion = new ArrayList<>();

        int days = (int) unit.toDays(time);
        long hours = unit.toHours(time) - (days * 24L);
        long minutes = unit.toMinutes(time) - (unit.toHours(time) * 60);
        long seconds = unit.toSeconds(time) - (unit.toMinutes(time) * 60);

        if (days != 0) conversion.add(days + " " + configLib.lang("time.days"));
        if (hours != 0) conversion.add(hours + " " + configLib.lang("time.hours"));
        if (minutes != 0) conversion.add(minutes + " " + configLib.lang("time.minutes"));
        if (seconds != 0) conversion.add(seconds + " " + configLib.lang("time.seconds"));

        return conversion.toString().replace("[", "").replace("]", "");
    }

    /**
     * Erstellt eine Information, ob die Sperre aktiv ist
     * <p>
     * Creates an information if the banning is enabled
     *
     * @return Info, ob Sperre aktiv <p> Info if banning is enabled
     * @author ItsLeMax
     */
    public static String info() {
        return "§3" + configLib.lang("notification.info")
                .replaceFirst("%t%", DeathBan.banTime)
                .replaceFirst("%r%", DeathBan.banEnabled ? ("§c" + configLib.lang("notification.locked")) : ("§a" + configLib.lang("notification.unlocked")))
                .replaceFirst("%a%", DeathBan.timeUntilBan)
                .replaceFirst("%c% ", "§7")
                .replaceFirst("%c% ", "§e")
                .replaceFirst("%c% ", "§7")
                .replaceFirst(" %c%", "§7");
    }
}