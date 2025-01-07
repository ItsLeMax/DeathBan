package de.max.deathban.commands;

import de.max.deathban.init.Methods;
import de.max.ilmlib.libraries.MessageLib;
import de.max.ilmlib.utility.HoverText;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static de.max.deathban.init.DeathBan.configLib;
import static de.max.deathban.init.DeathBan.messageLib;

public class ToggleDeathBan implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 0) {
            messageLib.sendInfo(sender, MessageLib.Template.ERROR, configLib.lang("commands.tooManyArgs"), new HoverText("/toggledeathban"));
            return true;
        }

        if (!sender.hasPermission("deathban.toggle")) {
            messageLib.sendInfo(sender, MessageLib.Template.ERROR, configLib.lang("general.noPerms"));
            return true;
        }

        Methods.enabled = !Methods.enabled;
        Methods.informPlayers();
        return true;
    }
}