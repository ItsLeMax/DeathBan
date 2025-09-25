package de.fpm_studio.deathban.commands;

import de.fpm_studio.deathban.DeathBan;
import de.fpm_studio.deathban.util.ConfigHandler;
import de.fpm_studio.deathban.util.MethodHandler;
import de.fpm_studio.ilmlib.libraries.MessageLib;
import de.fpm_studio.ilmlib.util.HoverText;
import de.fpm_studio.ilmlib.util.Template;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * Allows to switch between ban and no ban (enabling and disabling the plugin functionality)
 *
 * @author ItsLeMax
 * @since 1.0.0
 */
public final class ToggleDeathBanCommand implements CommandExecutor {

    private final MessageLib messageLib;

    public ToggleDeathBanCommand(@NotNull final DeathBan instance) {
        this.messageLib = instance.getMessageLib();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!sender.hasPermission("deathban.toggle")) {
            messageLib.sendInfo(sender, Template.ERROR, ConfigHandler.NO_PERMS);
            return true;
        }

        if (args.length > 0) {
            messageLib.sendInfo(sender, Template.ERROR, ConfigHandler.TOO_MANY_ARGS, new HoverText("/toggledeathban"));
            return true;
        }

        ConfigHandler.BAN_ENABLED = !ConfigHandler.BAN_ENABLED;

        final String info = MethodHandler.info();

        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(info);
        Bukkit.broadcastMessage("");

        return true;

    }

}