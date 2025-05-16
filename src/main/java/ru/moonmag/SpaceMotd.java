package ru.moonmag;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import ru.moonmag.utils.Hex;
import java.io.File;
import java.io.IOException;

public class SpaceMotd extends JavaPlugin {

    private boolean maintenanceMode;
    private File configFile;
    private YamlConfiguration config;

    @Override
    public void onDisable() {
        getLogger().info("§x§f§f§7§c§0§0╔");
        getLogger().info("§x§f§f§7§c§0§0║ §fОтключение плагина...");
        getLogger().info("§x§f§f§7§c§0§0║ §x§f§f§0§0§0§0Плагин отключился! §fКодер: §x§f§f§6§e§0§0SpaceDev");
        getLogger().info("§x§f§f§7§c§0§0║ §x§0§0§f§5§f§fh§x§0§0§f§4§f§ft§x§0§0§f§3§f§ft§x§0§0§f§2§f§fp§x§0§0§f§1§f§fs§x§0§0§e§f§f§f:§x§0§0§e§e§f§f/§x§0§0§e§d§f§f/§x§0§0§e§c§f§ft§x§0§0§e§b§f§f.§x§0§0§e§a§f§fm§x§0§0§e§9§f§fe§x§0§0§e§8§f§f/§x§0§0§e§7§f§fs§x§0§0§e§5§f§fp§x§0§0§e§4§f§fa§x§0§0§e§3§f§fc§x§0§0§e§2§f§fe§x§0§0§e§1§f§fs§x§0§0§e§0§f§ft§x§0§0§d§f§f§fu§x§0§0§d§e§f§fd§x§0§0§d§c§f§fi§x§0§0§d§b§f§fo§x§0§0§d§a§f§fm§x§0§0§d§9§f§fc");
        getLogger().info("§x§f§f§7§c§0§0╚");
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        configFile = new File(getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);
        loadMaintenanceStatus();
        Bukkit.getPluginManager().registerEvents(new Maintenance(this), this);
        Bukkit.getPluginManager().registerEvents(new MotdListener(this), this);
        getCommand("spacemotd").setExecutor(new MotdCommand(this));
        getCommand("spacemotd").setTabCompleter(new MotdCommand(this));
        getLogger().info("§x§f§f§7§c§0§0╔");
        getLogger().info("§x§f§f§7§c§0§0║ §fЗапуск плагина...");
        getLogger().info("§x§f§f§7§c§0§0║ §x§0§0§f§f§1§7Плагин запустился! §fКодер: §x§f§f§6§e§0§0SpaceDev");
        getLogger().info("§x§f§f§7§c§0§0║ §x§0§0§f§5§f§fh§x§0§0§f§4§f§ft§x§0§0§f§3§f§ft§x§0§0§f§2§f§fp§x§0§0§f§1§f§fs§x§0§0§e§f§f§f:§x§0§0§e§e§f§f/§x§0§0§e§d§f§f/§x§0§0§e§c§f§ft§x§0§0§e§b§f§f.§x§0§0§e§a§f§fm§x§0§0§e§9§f§fe§x§0§0§e§8§f§f/§x§0§0§e§7§f§fs§x§0§0§e§5§f§fp§x§0§0§e§4§f§fa§x§0§0§e§3§f§fc§x§0§0§e§2§f§fe§x§0§0§e§1§f§fs§x§0§0§e§0§f§ft§x§0§0§d§f§f§fu§x§0§0§d§e§f§fd§x§0§0§d§c§f§fi§x§0§0§d§b§f§fo§x§0§0§d§a§f§fm§x§0§0§d§9§f§fc");
        getLogger().info("§x§f§f§7§c§0§0╚");
        new ru.moonmag.utils.Metrics(this, 25622);
        new ru.moonmag.utils.UpdateChecker(this).checkForUpdates();
        }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(configFile);
        loadMaintenanceStatus();
    }

    private void loadMaintenanceStatus() {
        maintenanceMode = config.getBoolean("Maintenance.enabled");
    }

    public void setMaintenanceMode(boolean enabled) {
        maintenanceMode = enabled;
        config.set("Maintenance.enabled", enabled);
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isMaintenanceMode() {
        return maintenanceMode;
    }

    public String getMessage(String path) {
        return Hex.colorize(config.getString("messages." + path, "&cТакого сообщения нет."));
    }
}