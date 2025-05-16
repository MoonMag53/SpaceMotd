package ru.moonmag;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import ru.moonmag.utils.Hex;
import java.util.List;

public class Maintenance implements Listener {

    private final SpaceMotd plugin;

    public Maintenance(SpaceMotd plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        if (!plugin.isMaintenanceMode()) return;

        Player player = event.getPlayer();
        FileConfiguration config = plugin.getConfig();
        List<String> allowedPlayers = config.getStringList("Maintenance.allowed-players");
        boolean isAllowed = allowedPlayers.stream()
                .anyMatch(name -> name.equalsIgnoreCase(player.getName()));

        if (!isAllowed) {
            String kickMessage = config.getString("Maintenance.kickmessage");
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Hex.colorize(kickMessage));
        }
    }
}