package de.max.deathban.init;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static de.max.deathban.init.DeathBan.configLib;

public class ToggleDeathBan implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 0) {
            sender.sendMessage("§c" + configLib.lang("commands.tooManyArgs"));
            return true;
        }

        if (!sender.hasPermission("deathban.toggle")) {
            sender.sendMessage("§c" + configLib.lang("general.noPerms"));
            return true;
        }

        Information.enabled = !Information.enabled;
        Information.informPlayers();
        return true;
    }
}