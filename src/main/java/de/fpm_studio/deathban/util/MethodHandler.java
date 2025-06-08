package de.fpm_studio.deathban.util;

import de.fpm_studio.deathban.DeathBan;
import de.fpm_studio.deathban.data.GlobalVariables;
import de.fpm_studio.ilmlib.libraries.ConfigLib;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public final class MethodHandler {

    private final DeathBan instance;

    /**
     * Converts a time to the proper text
     *
     * @param time Time to convert
     * @param unit Unit of the time
     * @return String with converted time
     * @link <a href="https://stackoverflow.com/questions/11357945/java-convert-seconds-into-day-hour-minute-and-seconds-using-timeunit">StackOverflow</a>
     * @author StackOverflow, ItsLeMax
     * @since 1.0.0
     */
    public String convertTimeToText(final int time, @NotNull final TimeUnit unit) {

        final ConfigLib configLib = instance.getConfigLib();

        final ArrayList<String> conversion = new ArrayList<>();

        // Mathematical conversion

        final int days = (int) unit.toDays(time);
        final long hours = unit.toHours(time) - (days * 24L);
        final long minutes = unit.toMinutes(time) - (unit.toHours(time) * 60);
        final long seconds = unit.toSeconds(time) - (unit.toMinutes(time) * 60);

        // Show different time units only when they are not zero (0 days, 0 hours etc.)

        if (days != 0)
            conversion.add(days + " " + configLib.text("time.days"));

        if (hours != 0)
            conversion.add(hours + " " + configLib.text("time.hours"));

        if (minutes != 0)
            conversion.add(minutes + " " + configLib.text("time.minutes"));

        if (seconds != 0)
            conversion.add(seconds + " " + configLib.text("time.seconds"));

        return StringUtils.join(conversion, ", ");

    }

    /**
     * Creates an information if the banning is enabled
     *
     * @return String with info if banning is enabled
     * @author ItsLeMax
     * @since 1.0.0
     */
    public String info() {

        final ConfigLib configLib = instance.getConfigLib();

        final String banEnabled = GlobalVariables.banEnabled
                ? ("§c" + configLib.text("notification.locked"))
                : ("§a" + configLib.text("notification.unlocked"));

        // Replacing a lot of placeholders inside the text

        return "§3" + configLib.text("notification.info")
                .replaceFirst("%t%", GlobalVariables.banTime)
                .replaceFirst("%r%", banEnabled)
                .replaceFirst("%a%", GlobalVariables.timeUntilBan)
                .replaceFirst("%c% ", "§7")
                .replaceFirst("%c% ", "§e")
                .replaceFirst("%c% ", "§7")
                .replaceFirst(" %c%", "§7");

    }

}