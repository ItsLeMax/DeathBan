package de.max.deathban.init;

import de.max.deathban.events.PlayerDeath;
import de.max.deathban.events.PlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeathBan extends JavaPlugin {
    public static DeathBan plugin;

    @Override
    @SuppressWarnings("all")
    public void onEnable() {
        plugin = this;

        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeath(), this);

        getCommand("toggle").setExecutor(new Toggle());
    }
}
