package de.fpm_studio.deathban.util;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Holds different utility methods
 *
 * @author ItsLeMax
 * @since 1.0.0
 */
public final class MethodHandler {

    /**
     * Converts a time to the proper text
     *
     * @param time Time to convert
     * @param unit Unit of the time
     *
     * @return String with converted time
     *
     * @link <a href="https://stackoverflow.com/questions/11357945/java-convert-seconds-into-day-hour-minute-and-seconds-using-timeunit">StackOverflow</a>
     * @author StackOverflow, ItsLeMax
     * @since 1.0.0
     */
    public static String convertTimeToText(final int time, @NotNull final TimeUnit unit) {

        final ArrayList<String> conversion = new ArrayList<>();

        // Mathematical conversion

        final int days = (int) unit.toDays(time);
        final long hours = unit.toHours(time) - (days * 24L);
        final long minutes = unit.toMinutes(time) - (unit.toHours(time) * 60);
        final long seconds = unit.toSeconds(time) - (unit.toMinutes(time) * 60);

        // Show different time units only when they are not zero (0 days, 0 hours etc.)

        if (days != 0)
            conversion.add(days + " " + ConfigHandler.TIME_DAYS);

        if (hours != 0)
            conversion.add(hours + " " + ConfigHandler.TIME_HOURS);

        if (minutes != 0)
            conversion.add(minutes + " " + ConfigHandler.TIME_MINUTES);

        if (seconds != 0)
            conversion.add(seconds + " " + ConfigHandler.TIME_SECONDS);

        return StringUtils.join(conversion, ", ");

    }

    /**
     * Creates an information if the banning is enabled
     *
     * @return String with info if banning is enabled
     *
     * @author ItsLeMax
     * @since 1.0.0
     */
    public static String info() {

        final String banEnabled = ConfigHandler.BAN_ENABLED
                ? ("§c" + ConfigHandler.NOTIFICATION_LOCKED)
                : ("§a" + ConfigHandler.NOTIFICATION_UNLOCKED);

        // Replacing a lot of placeholders inside the text

        return "§3" + ConfigHandler.NOTIFICATION_INFO
                .replaceFirst("%t%", ConfigHandler.BAN_TIME)
                .replaceFirst("%r%", banEnabled)
                .replaceFirst("%a%", ConfigHandler.TIME_UNTIL_BAN)
                .replaceFirst("%c% ", "§7")
                .replaceFirst("%c% ", "§e")
                .replaceFirst("%c% ", "§7")
                .replaceFirst(" %c%", "§7");

    }

}