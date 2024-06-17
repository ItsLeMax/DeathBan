package de.max.deathban.init;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Information {
    public static boolean enabled = false;

    private static String info() {
        return "§3Spieler §7werden zurzeit §emit dem Tod §7nach 5 Minuten für 12 Stunden " + (enabled ? "§cgesperrt" : "§anicht gesperrt") + "§7. Dies kann sich mit der Zeit ändern.";
    }

    /**
     * Informiert die Spieler über den Status des Toggle-Befehls und dementsprechend, ob der Tod eine Sperre verursacht
     *
     * @author ItsLeMax
     */
    public static void informPlayers(Player player) {
        player.sendMessage("");
        player.sendMessage(info());
        player.sendMessage("");
    }

    /**
     * @see #informPlayers(Player)
     */
    @SuppressWarnings("deprecation")
    public static void informPlayers() {
        Bukkit.broadcast(new TextComponent(""));
        Bukkit.broadcast(new TextComponent(info()));
        Bukkit.broadcast(new TextComponent(""));
    }
}
