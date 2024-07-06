package de.max.deathban.init;

import de.max.deathban.commands.ToggleDeathBan;
import de.max.deathban.events.PlayerDeath;
import de.max.deathban.events.PlayerJoin;
import de.max.ilmlib.init.ConfigLib;
import de.max.ilmlib.init.ILMLib;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeathBan extends JavaPlugin {
    public static DeathBan plugin;
    public static ConfigLib configLib;

    @Override
    @SuppressWarnings("all")
    public void onEnable() {
        plugin = this;

        configLib = new ILMLib(plugin).getConfigLib();
        configLib
                .createDefaults("config")
                .createInsideDirectory("languages", "de_DE", "en_US", "custom_lang");

        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerDeath(), plugin);

        getCommand("toggledeathban").setExecutor(new ToggleDeathBan());

        Bukkit.getConsoleSender().sendMessage("§6" + configLib.lang("general.init"));
    }
}
