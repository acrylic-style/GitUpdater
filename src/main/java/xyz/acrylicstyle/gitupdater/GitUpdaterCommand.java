package xyz.acrylicstyle.gitupdater;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GitUpdaterCommand implements TabExecutor {
    private final GitUpdater plugin;

    public GitUpdaterCommand(GitUpdater plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args[0].equals("reload")) {
            plugin.reloadConfig();
            plugin.repositories.clear();
            plugin.repositories.addAll(plugin.getConfig().getStringList("repositories"));
            sender.sendMessage("§aReloaded config.");
        } else if (args[0].equals("update")) {
            if (args.length < 2) {
                sender.sendMessage("§cUsage: /gitupdater update <repository>");
                return true;
            }
            String repository = args[1];
            if (!plugin.repositories.contains(repository)) {
                sender.sendMessage("§cRepository not found.");
                return true;
            }
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                sender.sendMessage("§aUpdating " + repository + "...");
                try {
                    String result = plugin.update(repository).toString();
                    sender.sendMessage("Update result:");
                    sender.sendMessage(result);
                } catch (Exception e) {
                    sender.sendMessage("§cFailed to update " + repository + ".");
                    e.printStackTrace();
                    return;
                }
                sender.sendMessage("§aUpdated " + repository + ".");
            });
            return true;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("update", "reload");
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("update")) {
                return plugin.repositories;
            }
        }
        return Collections.emptyList();
    }
}
