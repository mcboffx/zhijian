package org.fgg2333.zhijian.gongneng;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class BackManager {
    private final HashMap<UUID, Location> lastLocations = new HashMap<>();

    public void setLastLocation(Player player, Location location) {
        lastLocations.put(player.getUniqueId(), location);
    }

    public Location getLastLocation(Player player) {
        return lastLocations.get(player.getUniqueId());
    }

    public void removeLastLocation(Player player) {
        lastLocations.remove(player.getUniqueId());
    }
}
