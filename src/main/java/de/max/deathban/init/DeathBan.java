package de.max.deathban.init;

import de.max.deathban.commands.ToggleDeathBan;
import de.max.deathban.events.PlayerDeath;
import de.max.deathban.events.PlayerJoin;
import de.max.ilmlib.libraries.ConfigLib;
import de.max.ilmlib.libraries.MessageLib;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

public final class DeathBan extends JavaPlugin {
    public static DeathBan plugin;

    public static ConfigLib configLib;
    public static MessageLib messageLib;

    @Override
    public void onEnable() {
        plugin = this;

        configLib = new ConfigLib(this)
                .createDefaults("config")
                .createInsideDirectory("languages", "de_DE", "en_US", "custom_lang");

        messageLib = new MessageLib()
                .addSpacing()
                .setPrefix("§6DeathBan §7»", true)
                .createDefaults()
                .setSuffix(MessageLib.Template.ERROR, configLib.lang("commands.error"));

        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerDeath(), plugin);

        @Nullable PluginCommand command = getCommand("toggledeathban");
        assert command != null;
        command.setExecutor(new ToggleDeathBan());

        Bukkit.getConsoleSender().sendMessage("§6" + configLib.lang("init").replace("%p%", "[DeathBan]"));
    }
}