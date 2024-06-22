package de.max.deathban.init;

import de.max.ilmlib.init.ConfigLib;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ToggleDeathBan implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("§c" + ConfigLib.lang("general.noPerms"));
            return true;
        }

        Information.enabled = !Information.enabled;
        Information.informPlayers();
        return true;
    }
}