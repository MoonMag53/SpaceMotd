package ru.moonmag;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MotdCommand implements CommandExecutor, TabCompleter {

    private final SpaceMotd plugin;

    public MotdCommand(SpaceMotd plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(plugin.getMessage("usage"));
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                if (sender.hasPermission("spacemotd.reload")) {
                    plugin.reload();
                    sender.sendMessage(plugin.getMessage("reload"));
                } else {
                    sender.sendMessage(plugin.getMessage("nopermission"));
                }
                return true;

            case "maintenance":
                if (args.length < 2) {
                    sender.sendMessage(plugin.getMessage("usage"));
                    return false;
                }

                if (args[1].equalsIgnoreCase("enable")) {
                    if (!plugin.isMaintenanceMode()) {
                        plugin.setMaintenanceMode(true);
                        sender.sendMessage(plugin.getMessage("enabledmaintenance"));
                    } else {
                        sender.sendMessage(plugin.getMessage("alreadyenabled"));
                    }
                } else if (args[1].equalsIgnoreCase("disable")) {
                    if (plugin.isMaintenanceMode()) {
                        plugin.setMaintenanceMode(false);
                        sender.sendMessage(plugin.getMessage("disabledmaintenance"));
                    } else {
                        sender.sendMessage(plugin.getMessage("alreadydisabled"));
                    }
                } else {
                    sender.sendMessage(plugin.getMessage("usage"));
                }
                return true;

            default:
                sender.sendMessage(plugin.getMessage("usage"));
                return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("reload", "maintenance");
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("maintenance")) {
            return Arrays.asList("enable", "disable");
        }
        return Collections.emptyList();
    }
}