package org.fgg2333.zhijian.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class RestpCommand implements CommandExecutor {
    private final LocationManager locationManager;

    public RestpCommand(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("只有玩家才能使用此命令");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage(ChatColor.GREEN + "用法: /res <add|tp|about|del|list> <name>");
            return true;
        }

        String subCommand = args[0];

        switch (subCommand.toLowerCase()) {
            case "add":
                if (args.length < 2) {
                    player.sendMessage(ChatColor.GREEN + "用法: /res add <name>");
                    return true;
                }
                handleAddCommand(player, args[1]);
                break;

            case "tp":
                if (args.length < 2) {
                    player.sendMessage(ChatColor.GREEN + "用法: /res tp <name>");
                    return true;
                }
                handleTpCommand(player, args[1]);
                break;

            case "about":
                if (args.length < 2) {
                    player.sendMessage(ChatColor.GREEN + "用法: /res about <name>");
                    return true;
                }
                handleAboutCommand(player, args[1]);
                break;

            case "del":
                if (args.length < 2) {
                    player.sendMessage(ChatColor.GREEN + "用法: /res del <name>");
                    return true;
                }
                handleDelCommand(player, args[1]);
                break;

            case "list":
                int page = 1;
                if (args.length >= 2) {
                    try {
                        page = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        player.sendMessage(ChatColor.GREEN + "[zhijian]没有这一页");
                        return true;
                    }
                }
                handleListCommand(player, page);
                break;

            default:
                player.sendMessage(ChatColor.GREEN + "用法: /res <add|tp|about|del|list> <name>");
                break;
        }
        return true;
    }

    private void handleAddCommand(Player player, String name) {
        try {
            Location location = player.getLocation();
            locationManager.addLocation(player, name, location);
            player.sendMessage(ChatColor.GREEN + "位置 " + ChatColor.WHITE + name + ChatColor.GREEN + " 已创建");
        } catch (Exception e) {
            player.sendMessage(ChatColor.GREEN + e.getMessage());
        }
    }

    private void handleTpCommand(Player player, String name) {
        Location loc = locationManager.getLocation(name);
        if (loc == null) {
            player.sendMessage(ChatColor.GREEN + "位置 " + ChatColor.WHITE + name + ChatColor.GREEN + " 不存在");
        } else {
            player.teleport(loc);
            player.sendMessage(ChatColor.GREEN + "已传送到 " + ChatColor.WHITE + name);
        }
    }

    private void handleAboutCommand(Player player, String name) {
        LocationData data = locationManager.getLocationData(name);
        if (data == null) {
            player.sendMessage(ChatColor.GREEN + "位置 " + ChatColor.WHITE + name + ChatColor.GREEN + " 不存在");
        } else {
            String ownerName = Bukkit.getOfflinePlayer(data.getOwner()).getName();
            String creationDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(data.getTimestamp()));
            player.sendMessage(ChatColor.GREEN + "位置 " + ChatColor.WHITE + name + ChatColor.GREEN + " 创建于 " + ChatColor.WHITE + ownerName + ChatColor.GREEN + " ，创建时间： " + ChatColor.WHITE + creationDate + ChatColor.GREEN + "。");
        }
    }

    private void handleDelCommand(Player player, String name) {
        try {
            locationManager.removeLocation(player, name);
            player.sendMessage(ChatColor.GREEN + "位置 " + ChatColor.WHITE + name + ChatColor.GREEN + " 已删除");
        } catch (Exception e) {
            player.sendMessage(ChatColor.GREEN + e.getMessage());
        }
    }

    private void handleListCommand(Player player, int page) {
        Map<String, LocationData> locations = locationManager.getLocations();
        List<String> keys = new ArrayList<>(locations.keySet());
        int itemsPerPage = 10;
        int totalPage = (int) Math.ceil((double) keys.size() / itemsPerPage);
        page = Math.min(totalPage, Math.max(1, page));
        int start = (page - 1) * itemsPerPage;
        int end = Math.min(keys.size(), start + itemsPerPage);

        player.sendMessage(ChatColor.WHITE + "--------------位置列表--------------");
        for (int i = start; i < end; i++) {
            String name = keys.get(i);
            String ownerName = Bukkit.getOfflinePlayer(locations.get(name).getOwner()).getName();
            player.sendMessage((i + 1) + ". " + name + " 创建人：" + ownerName);
        }
        player.sendMessage(ChatColor.WHITE + "--------------------------------------");
        if (page < totalPage) {
            player.sendMessage(ChatColor.GREEN + "使用 /res list " + ChatColor.WHITE + (page + 1) + ChatColor.GREEN + " 查看下一页");
        }
    }
}