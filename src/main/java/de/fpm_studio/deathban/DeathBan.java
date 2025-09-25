package de.fpm_studio.deathban;

import de.fpm_studio.deathban.commands.ToggleDeathBanCommand;
import de.fpm_studio.deathban.listener.AsyncPlayerPreLoginListener;
import de.fpm_studio.deathban.listener.PlayerDeathListener;
import de.fpm_studio.deathban.listener.PlayerJoinListener;
import de.fpm_studio.deathban.util.ConfigHandler;
import de.fpm_studio.ilmlib.libraries.ConfigLib;
import de.fpm_studio.ilmlib.libraries.MessageLib;
import de.fpm_studio.ilmlib.util.Template;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Holds the plugins entry point
 *
 * @author ItsLeMax
 * @since 1.0.0
 */
@Getter
public final class DeathBan extends JavaPlugin {

    @Getter
    private static DeathBan instance;

    private ConfigLib configLib;
    private MessageLib messageLib;

    @Override
    public void onEnable() {

        instance = this;

        // Library initialization

        configLib = new ConfigLib(this)
                .createDefaultConfigs("config")
                .createConfigsInsideDirectory("localization", "de_DE", "en_US", "custom");

        messageLib = new MessageLib()
                .addSpacing()
                .setPrefix("§6DeathBan §7»", true)
                .createTemplateDefaults()
                .setSuffix(Template.ERROR, ConfigHandler.COMMANDS_ERROR);

        // Commands, events and global variables

        registerEvents();
        registerCommands();

    }

    /**
     * Registers the plugins events
     *
     * @author ItsLeMax
     * @since Code: 1.0.0 <br> Method - 1.0.2
     */
    private void registerEvents() {

        Bukkit.getPluginManager().registerEvents(new AsyncPlayerPreLoginListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(this), this);

    }

    /**
     * Registers the plugins commands
     *
     * @author ItsLeMax
     * @since Code: 1.0.0 <br> Method - 1.0.2
     */
    @SuppressWarnings("ConstantConditions")
    private void registerCommands() {
        getCommand("toggledeathban").setExecutor(new ToggleDeathBanCommand(this));
    }

}