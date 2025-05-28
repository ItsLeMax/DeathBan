package de.fpm_studio.deathban;

import com.destroystokyo.paper.profile.PlayerProfile;
import de.fpm_studio.deathban.commands.ToggleDeathBan;
import de.fpm_studio.deathban.events.AsyncPlayerPreLogin;
import de.fpm_studio.deathban.events.PlayerDeath;
import de.fpm_studio.deathban.events.PlayerJoin;
import de.fpm_studio.deathban.util.Methods;
import de.max.ilmlib.libraries.ConfigLib;
import de.max.ilmlib.libraries.MessageLib;
import io.papermc.paper.ban.BanListType;
import org.bukkit.BanEntry;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeathBan extends JavaPlugin {
    public static DeathBan plugin;

    public static ConfigLib configLib;
    public static MessageLib messageLib;

    public static boolean banEnabled = false;
    public static String timeUntilBan;
    public static String banTime;
    public static String banReason;

    @Override
    @SuppressWarnings("ConstantConditions")
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

        banTime = Methods.convertTimeToText(configLib.getConfig("config").getInt("banTime"), false);
        timeUntilBan = Methods.convertTimeToText(configLib.getConfig("config").getInt("timeUntilBan"), true);
        banReason = "§c" + configLib.lang("warning.ban").replace("%t%", DeathBan.banTime);

        Bukkit.getPluginManager().registerEvents(new AsyncPlayerPreLogin(), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerDeath(), plugin);

        getCommand("toggledeathban").setExecutor(new ToggleDeathBan());

        Bukkit.getConsoleSender().sendMessage("§6" + configLib.lang("init").replace("%p%", "[DeathBan]"));
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onDisable() {
        for (BanEntry<? super PlayerProfile> ban : Bukkit.getBanList(BanListType.PROFILE).getEntries()) {
            if (ban.getReason() != null && !ban.getReason().equals(banReason)) {
                continue;
            }

            Bukkit.getConsoleSender().sendMessage("§c" + configLib.lang("warning.shutdown").replace("%r%", ban.getTarget()));
            Bukkit.getBanList(BanListType.PROFILE).pardon(ban.getTarget());
        }
    }
}