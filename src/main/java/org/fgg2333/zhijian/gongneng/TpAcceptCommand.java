package org.fgg2333.zhijian.gongneng;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.fgg2333.zhijian.Zhijian;

import java.util.UUID; // 添加此行

public class TpAcceptCommand implements CommandExecutor {
    private final Zhijian plugin;

    public TpAcceptCommand(Zhijian plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player target = (Player) sender;
            UUID senderId = plugin.getTpaManager().getRequest(target);

            if (senderId == null) {
                target.sendMessage(ChatColor.RED + "[zhijian]你没有TPA请求");
                return false;
            }

            Player senderPlayer = target.getServer().getPlayer(senderId);
            if (senderPlayer != null) {
                senderPlayer.teleport(target);
                senderPlayer.sendMessage(ChatColor.GREEN + "[zhijian]是否接受 " + ChatColor.WHITE + target.getName() + ChatColor.GREEN + " 的传送请求");
                target.sendMessage(ChatColor.GREEN + "[zhijian]你接受了来自 " + ChatColor.WHITE + senderPlayer.getName() + ChatColor.GREEN + " 的传送请求");
            }
            plugin.getTpaManager().removeRequest(target);
            return true;
        }
        return false;
    }
}
