package de.max.deathban.init;

import de.max.deathban.events.PlayerDeath;
import de.max.deathban.events.PlayerJoin;
import de.max.ilmlib.init.ConfigLib;
import de.max.ilmlib.init.ILMLib;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeathBan extends JavaPlugin {
    public static DeathBan plugin;

    @Override
    @SuppressWarnings("all")
    public void onEnable() {
        plugin = this;
        ConfigLib.saveDefaultConfig();

        ILMLib.init(this);
        ConfigLib.create("de_DE", "en_US", "custom_lang");

        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeath(), this);

        getCommand("toggledeathban").setExecutor(new ToggleDeathBan());

        Bukkit.getConsoleSender().sendMessage("§6" + ConfigLib.lang("general.init"));
    }
}
