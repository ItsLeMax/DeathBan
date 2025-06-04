package de.fpm_studio.deathban;

import de.fpm_studio.deathban.commands.ToggleDeathBan;
import de.fpm_studio.deathban.data.GlobalVariables;
import de.fpm_studio.deathban.events.AsyncPlayerPreLogin;
import de.fpm_studio.deathban.events.PlayerDeath;
import de.fpm_studio.deathban.events.PlayerJoin;
import de.fpm_studio.deathban.util.MethodHandler;
import de.fpm_studio.ilmlib.libraries.ConfigLib;
import de.fpm_studio.ilmlib.libraries.MessageLib;
import de.fpm_studio.ilmlib.util.Template;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;

/**
 * Holds the plugins entry point
 *
 * @author ItsLeMax
 * @since 1.0.0
 */
public final class DeathBan extends JavaPlugin {

    private ConfigLib configLib;
    private MessageLib messageLib;

    private MethodHandler methodHandler;

    @Override
    public void onEnable() {

        // Library initialization

        configLib = new ConfigLib(this)
                .createDefaultConfigs("config")
                .createConfigsInsideDirectory("languages", "de_DE", "en_US", "custom_lang");

        messageLib = new MessageLib()
                .addSpacing()
                .setPrefix("§6DeathBan §7»", true)
                .createTemplateDefaults()
                .setSuffix(Template.ERROR, configLib.text("commands.error"));

        // Special utility class

        methodHandler = new MethodHandler(configLib);

        // Commands, events and global variables

        registerCommands();
        registerEvents();
        initializeGlobalVariables();

        Bukkit.getConsoleSender().sendMessage("§6" + configLib.text("init").replace("%p%", "[DeathBan]"));

    }

    /**
     * Initializes global variables
     *
     * @author ItsLeMax
     * @since Code: 1.0.0 <br> Method: 1.0.2
     */
    public void initializeGlobalVariables() {

        GlobalVariables.banTime = methodHandler.convertTimeToText(
                configLib.getConfig("config").getInt("banTime"), TimeUnit.MINUTES
        );

        GlobalVariables.timeUntilBan = methodHandler.convertTimeToText(
                configLib.getConfig("config").getInt("timeUntilBan"), TimeUnit.SECONDS
        );

        GlobalVariables.banReason = "§c" + configLib.text("warning.ban")
                .replace("%t%", GlobalVariables.banTime);

    }

    /**
     * Registers the plugins commands
     *
     * @author ItsLeMax
     * @since Code: 1.0.0 <br> Method - 1.0.2
     */
    @SuppressWarnings("ConstantConditions")
    private void registerCommands() {
        getCommand("toggledeathban").setExecutor(new ToggleDeathBan(configLib, messageLib, methodHandler));
    }

    /**
     * Registers the plugins events
     *
     * @author ItsLeMax
     * @since Code: 1.0.0 <br> Method - 1.0.2
     */
    private void registerEvents() {

        Bukkit.getPluginManager().registerEvents(new AsyncPlayerPreLogin(configLib, methodHandler), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(methodHandler), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeath(this, configLib, methodHandler), this);

    }

}