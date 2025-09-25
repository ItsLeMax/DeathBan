package de.fpm_studio.deathban.util;

import de.fpm_studio.deathban.DeathBan;
import de.fpm_studio.ilmlib.libraries.ConfigLib;

/**
 * Holds simple text and boolean data
 *
 * @author ItsLeMax
 * @since Code: 1.0.0 <br> Class: 1.0.3
 */
public final class ConfigHandler {

    private static final ConfigLib configLib = DeathBan.getInstance().getConfigLib();

    public static boolean BAN_ENABLED = false;

    public static String
            TIME_UNTIL_BAN,
            BAN_TIME,
            BAN_REASON;

    public static String
            COMMANDS_ERROR = configLib.text("commands.error"),
            WARNING_BAN = configLib.text("warning.ban"),
            TIME_DAYS = configLib.text("time.days"),
            TIME_HOURS = configLib.text("time.hours"),
            TIME_MINUTES = configLib.text("time.minutes"),
            TIME_SECONDS = configLib.text("time.seconds"),
            NOTIFICATION_LOCKED = configLib.text("notification.locked"),
            NOTIFICATION_UNLOCKED = configLib.text("notification.unlocked"),
            NOTIFICATION_INFO = configLib.text("notification.info"),
            WARNING_DEATH = configLib.text("warning.death"),
            WARNING_EXPLANATION = configLib.text("warning.explanation"),
            WARNING_UPDATE = configLib.text("warning.update"),
            WARNING_TIME_UNTIL_BAN = configLib.text("warning.timeUntilBan"),
            WARNING_CONSOLE = configLib.text("warning.console"),
            WARNING_UNBAN = configLib.text("warning.unban"),
            NO_PERMS = configLib.text("commands.noPerms"),
            TOO_MANY_ARGS = configLib.text("commands.tooManyArgs");

}