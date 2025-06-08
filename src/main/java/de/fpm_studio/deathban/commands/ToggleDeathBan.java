package de.fpm_studio.deathban.commands;

import de.fpm_studio.deathban.DeathBan;
import de.fpm_studio.deathban.data.GlobalVariables;
import de.fpm_studio.deathban.util.MethodHandler;
import de.fpm_studio.ilmlib.libraries.ConfigLib;
import de.fpm_studio.ilmlib.libraries.MessageLib;
import de.fpm_studio.ilmlib.util.HoverText;
import de.fpm_studio.ilmlib.util.Template;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public final class ToggleDeathBan implements CommandExecutor {

    private final DeathBan instance;

    @Override
    @SuppressWarnings("deprecation")
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        final ConfigLib configLib = instance.getConfigLib();
        final MessageLib messageLib = instance.getMessageLib();

        final MethodHandler methodHandler = instance.getMethodHandler();

        if (!sender.hasPermission("deathban.toggle")) {
            messageLib.sendInfo(sender, Template.ERROR, configLib.text("commands.noPerms"));
            return true;
        }

        if (args.length > 0) {
            messageLib.sendInfo(sender, Template.ERROR, configLib.text("commands.tooManyArgs"), new HoverText("/toggledeathban"));
            return true;
        }

        GlobalVariables.banEnabled = !GlobalVariables.banEnabled;

        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(methodHandler.info());
        Bukkit.broadcastMessage("");

        return true;

    }

}