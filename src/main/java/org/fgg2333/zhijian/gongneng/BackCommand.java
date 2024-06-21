package org.fgg2333.zhijian.gongneng;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.fgg2333.zhijian.Zhijian;

public class BackCommand implements CommandExecutor {
    private final Zhijian plugin;

    public BackCommand(Zhijian plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Location lastLocation = plugin.getBackManager().getLastLocation(player);

            if (lastLocation == null) {
                player.sendMessage(ChatColor.RED + "[zhijian]您没有能返回的位置");
                return false;
            }

            plugin.getBackManager().setLastLocation(player, player.getLocation());
            player.teleport(lastLocation);
            player.sendMessage(ChatColor.GREEN + "[zhijian]已传送到上次的位置");
            return true;
        }
        return false;
    }
}
