package org.fgg2333.zhijian.welcome;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.fgg2333.zhijian.Zhijian;

public class guanli implements Listener {

    private final PluginDescriptionFile pdf;

    public guanli(Zhijian plugin) {
        this.pdf = plugin.getDescription();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("zhijian.guanli")) {
            // 如果玩家有 zhijian.guanli 权限
            String serverVersion = Bukkit.getServer().getVersion();
            String pluginVersion = pdf.getVersion();
            player.sendMessage(ChatColor.GREEN + "亲爱的管理员您好,当前服务器版本为：" + ChatColor.WHITE + serverVersion + " " +  ChatColor.GREEN + "插件版本为：" + ChatColor.WHITE + pluginVersion);
            player.sendMessage(ChatColor.GREEN + "如遇服务器与插件问题请联系 " + ChatColor.WHITE + "Huyast1114188" + " " + ChatColor.GREEN + "进行处理");
            player.sendMessage(ChatColor.GREEN + "感谢您对服务器做出的贡献，为服务器内玩家进行问题处理");
        }
    }
}
