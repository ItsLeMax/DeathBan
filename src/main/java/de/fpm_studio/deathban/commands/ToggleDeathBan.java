package de.fpm_studio.deathban.commands;

import de.fpm_studio.deathban.DeathBan;
import de.fpm_studio.deathban.util.Methods;
import de.max.ilmlib.init.HoverText;
import de.max.ilmlib.libraries.MessageLib;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static de.fpm_studio.deathban.DeathBan.configLib;
import static de.fpm_studio.deathban.DeathBan.messageLib;

public class ToggleDeathBan implements CommandExecutor {
    @Override
    @SuppressWarnings("deprecation")
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("deathban.toggle")) {
            messageLib.sendInfo(sender, MessageLib.Template.ERROR, configLib.lang("commands.noPerms"));
            return true;
        }

        if (args.length > 0) {
            messageLib.sendInfo(sender, MessageLib.Template.ERROR, configLib.lang("commands.tooManyArgs"), new HoverText("/toggledeathban"));
            return true;
        }

        DeathBan.banEnabled = !DeathBan.banEnabled;

        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(Methods.info());
        Bukkit.broadcastMessage("");

        return true;
    }
}