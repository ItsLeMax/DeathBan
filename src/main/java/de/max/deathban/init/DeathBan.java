package de.max.deathban.init;

import com.destroystokyo.paper.profile.PlayerProfile;
import de.max.deathban.commands.ToggleDeathBan;
import de.max.deathban.events.PlayerDeath;
import de.max.deathban.events.PlayerJoin;
import de.max.ilmlib.libraries.ConfigLib;
import de.max.ilmlib.libraries.MessageLib;
import io.papermc.paper.ban.BanListType;
import org.bukkit.BanEntry;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

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

        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerDeath(), plugin);

        getCommand("toggledeathban").setExecutor(new ToggleDeathBan());

        Bukkit.getConsoleSender().sendMessage("§6" + configLib.lang("init").replace("%p%", "[DeathBan]"));
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onDisable() {
        for (BanEntry<? super PlayerProfile> ban : Bukkit.getBanList(BanListType.PROFILE).getEntries()) {
            if (!Objects.equals(ban.getReason(), banReason)) {
                continue;
            }

            Bukkit.getConsoleSender().sendMessage("§c[!] §c" + configLib.lang("warning.shutdown").replace("%r%", ban.getTarget()));
            Bukkit.getBanList(BanListType.PROFILE).pardon(ban.getTarget());
        }
    }
}