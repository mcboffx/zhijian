package org.fgg2333.zhijian.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LocationManager {
    private final JavaPlugin plugin;
    private final File dataFile;
    private final FileConfiguration config;
    private final Map<String, LocationData> locations = new HashMap<>();
    private final Map<UUID, Integer> playerLocationCount = new HashMap<>();

    public LocationManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.dataFile = new File(plugin.getDataFolder(), "locations.yml");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.config = YamlConfiguration.loadConfiguration(dataFile);
        loadLocations();
    }

    public void addLocation(Player player, String name, Location location) throws Exception {
        if (locations.containsKey(name)) {
            throw new Exception(ChatColor.GREEN + "[zhijian]位置名称已存在");
        }

        UUID playerId = player.getUniqueId();
        String playerRole = getPlayerRole(player);
        int maxLocations = playerRole.equals("zhijian.guanli") ? 999 : 5;

        int count = playerLocationCount.getOrDefault(playerId, 0);
        if (count >= maxLocations) {
            throw new Exception(ChatColor.GREEN + "[zhijian]您创建到达到最大位置数");
        }

        LocationData locationData = new LocationData(location, playerId, System.currentTimeMillis());
        locations.put(name, locationData);
        playerLocationCount.put(playerId, count + 1);
        saveLocations();
    }

    public LocationData getLocationData(String name) {
        return locations.get(name);
    }

    public Location getLocation(String name) {
        LocationData data = locations.get(name);
        return data != null ? data.getLocation() : null;
    }

    public void removeLocation(Player player, String name) throws Exception {
        LocationData data = locations.get(name);
        if (data == null) {
            throw new Exception("找不到位置");
        }

        if (!player.hasPermission("zhijian.guanli") && !data.getOwner().equals(player.getUniqueId())) {
            throw new Exception(ChatColor.GREEN + "[zhijian]您没有删除此位置的权限");
        }

        locations.remove(name);
        playerLocationCount.put(data.getOwner(), playerLocationCount.get(data.getOwner()) - 1);
        saveLocations();
    }

    public Map<String, LocationData> getLocations() {
        return locations;
    }

    private void loadLocations() {
        for (String name : config.getKeys(false)) {
            Location location = (Location) config.get(name + ".location");
            UUID owner = UUID.fromString(config.getString(name + ".owner"));
            long timestamp = config.getLong(name + ".timestamp");
            locations.put(name, new LocationData(location, owner, timestamp));
            playerLocationCount.put(owner, playerLocationCount.getOrDefault(owner, 0) + 1);
        }
    }

    private void saveLocations() {
        for (Map.Entry<String, LocationData> entry : locations.entrySet()) {
            String name = entry.getKey();
            LocationData data = entry.getValue();
            config.set(name + ".location", data.getLocation());
            config.set(name + ".owner", data.getOwner().toString());
            config.set(name + ".timestamp", data.getTimestamp());
        }
        try {
            config.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getPlayerRole(Player player) {
        if (player.hasPermission("zhijian.guanli")) {
            return "zhijian.guanli";
        } else if (player.hasPermission("zhijian.wanjia")) {
            return "zhijian.wanjia";
        }
        return "none";
    }
}

class LocationData {
    private final Location location;
    private final UUID owner;
    private final long timestamp;

    public LocationData(Location location, UUID owner, long timestamp) {
        this.location = location;
        this.owner = owner;
        this.timestamp = timestamp;
    }

    public Location getLocation() {
        return location;
    }

    public UUID getOwner() {
        return owner;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
