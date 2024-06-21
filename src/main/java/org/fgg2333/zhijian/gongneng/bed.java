package org.fgg2333.zhijian.gongneng;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.fgg2333.zhijian.Zhijian;

public class bed implements Listener {
    private Zhijian plugin;
    private int sleepingPlayers = 0;

    public bed(Zhijian plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        if (event.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK) {
            sleepingPlayers++;
            updateSleepingPlayers();
        }
    }

    @EventHandler
    public void onPlayerBedLeave(PlayerBedLeaveEvent event) {
        sleepingPlayers--;
        updateSleepingPlayers();
    }

    private void updateSleepingPlayers() {
        String message;
        int multiplier;

        if (sleepingPlayers == 0) {
            message = ChatColor.GREEN + "[zhijian]当前有0人正在睡觉，游戏加速1x";
            multiplier = 1;
        } else if (sleepingPlayers == 1) {
            message = ChatColor.GREEN + "[zhijian]当前有" + sleepingPlayers + "人正在睡觉，游戏加速5x";
            multiplier = 2;
        } else {
            message = ChatColor.GREEN + "[zhijian]当前有" + sleepingPlayers + "人正在睡觉，游戏加速10x";
            multiplier = 999;
        }

        Bukkit.broadcastMessage(message);
        accelerateTime(multiplier);
    }

    private void accelerateTime(int multiplier) {
        for (World world : Bukkit.getWorlds()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (sleepingPlayers == 0) {
                        world.setTime(world.getTime() + 1);
                        cancel();
                    } else {
                        long time = world.getTime();
                        world.setTime(time + multiplier);
                    }
                }
            }.runTaskTimer(plugin, 0L, 1L);
        }
    }
}
