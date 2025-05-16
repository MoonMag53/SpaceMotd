package ru.moonmag;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import ru.moonmag.utils.Hex;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class MotdListener implements Listener {

    private final SpaceMotd plugin;

    public MotdListener(SpaceMotd plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        FileConfiguration config = plugin.getConfig();

        String line1;
        String line2;

        if (plugin.isMaintenanceMode()) {
            line1 = config.getString("Maintenance.Line-1");
            line2 = config.getString("Maintenance.Line-2");
        } else {
            line1 = config.getString("SERVER-MOTD.Line-1");
            line2 = config.getString("SERVER-MOTD.Line-2");
        }

        event.setMotd(Hex.colorize(line1) + "\n" + Hex.colorize(line2));

        if (config.getBoolean("Custom-Server-Icon.Enabled")) {
            try {
                File iconFile = new File(Bukkit.getServer().getWorldContainer(), config.getString("Custom-Server-Icon.Image"));
                if (iconFile.exists()) {
                    BufferedImage image = ImageIO.read(iconFile);
                    event.setServerIcon(Bukkit.loadServerIcon(image));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}