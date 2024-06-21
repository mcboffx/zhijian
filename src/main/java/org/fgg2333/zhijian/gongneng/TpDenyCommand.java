package org.fgg2333.zhijian.gongneng;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.fgg2333.zhijian.Zhijian;

import java.util.UUID;

public class TpDenyCommand implements CommandExecutor {
    private final Zhijian plugin;

    public TpDenyCommand(Zhijian plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player target = (Player) sender;
            UUID senderId = plugin.getTpaManager().getRequest(target);

            if (senderId == null) {
                target.sendMessage(ChatColor.RED + "[zhijian]你没有待定的TPA请求。");
                return false;
            }

            Player senderPlayer = target.getServer().getPlayer(senderId);
            if (senderPlayer != null) {
                senderPlayer.sendMessage(ChatColor.RED + "[zhijian]你的传送请求被 " + ChatColor.WHITE +target.getName() + ChatColor.RED + " 拒绝");
                target.sendMessage(ChatColor.GREEN + "[zhijian]你拒绝了来自 " + ChatColor.WHITE + senderPlayer.getName() + ChatColor.GREEN + " 的传送请求");
            }
            plugin.getTpaManager().removeRequest(target);
            return true;
        }
        return false;
    }
}
