package org.fgg2333.zhijian.welcome;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public class player implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("zhijian.wanjia") && !player.hasPermission("zhijian.guanli")) {
            String serverVersion = Bukkit.getServer().getVersion();
            player.sendMessage(ChatColor.GREEN + "欢迎您加入指尖的小服，当前服务器版本为 " + ChatColor.WHITE + "Purpur " + serverVersion);
            player.sendMessage(ChatColor.GREEN + "如有BUG与其他问题，请联系服务器开发者 " + ChatColor.WHITE + "/zhijian about" + ChatColor.GREEN + "查看本插件详细信息");
        }
    }
}
