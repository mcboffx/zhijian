package org.fgg2333.zhijian.gongneng;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.fgg2333.zhijian.Zhijian;

public class TpaCommand implements CommandExecutor {
    private final Zhijian plugin;

    public TpaCommand(Zhijian plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length != 1) {
                player.sendMessage(ChatColor.RED + "传送: /tpa <player>");
                return false;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(ChatColor.RED + "[zhijian]该玩家不在线");
                return false;
            }

            plugin.getTpaManager().sendTpaRequest(player, target);
            player.sendMessage(ChatColor.GREEN + "[zhijian]tpa请求已发送到 " + target.getName());
            target.sendMessage(ChatColor.GREEN + "[zhijian] " + ChatColor.WHITE + player.getName() + ChatColor.GREEN + " 向你发送传送请求. 同意: " + ChatColor.WHITE + "/tpaccept " + ChatColor.GREEN + "拒绝: " + ChatColor.WHITE +"tpano");
            return true;
        }
        return false;
    }
}
