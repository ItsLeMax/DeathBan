package de.max.deathban.init;

import de.max.deathban.commands.ToggleDeathBan;
import de.max.deathban.events.PlayerDeath;
import de.max.deathban.events.PlayerJoin;
import de.max.ilmlib.libraries.ConfigLib;
import de.max.ilmlib.libraries.MessageLib;
import de.max.ilmlib.utility.ErrorTemplate;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeathBan extends JavaPlugin {

    public static DeathBan plugin;
    public static ConfigLib configLib;
    public static MessageLib messageLib;

    @Override
    @SuppressWarnings("all")
    public void onEnable() {
        plugin = this;
        configLib = new ConfigLib()
                .setPlugin(this)
                .createDefaults("config")
                .createInsideDirectory("languages", "de_DE", "en_US", "custom_lang");

        messageLib = new MessageLib()
                .addSpacing()
                .createDefaults()
                .setPrefix("§6DeathBan §7»", true);

        new ErrorTemplate().setSuffix(configLib.lang("general.error"));

        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerDeath(), plugin);

        getCommand("toggledeathban").setExecutor(new ToggleDeathBan());

        Bukkit.getConsoleSender().sendMessage("§6" + configLib.lang("general.init").replace("%p%", "[DeathBan]"));
    }
}