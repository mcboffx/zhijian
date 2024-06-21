package org.fgg2333.zhijian.gongneng;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class TpaManager {
    private final HashMap<UUID, UUID> tpaRequests = new HashMap<>();

    public void sendTpaRequest(Player sender, Player target) {
        tpaRequests.put(target.getUniqueId(), sender.getUniqueId());
    }

    public UUID getRequest(Player target) {
        return tpaRequests.get(target.getUniqueId());
    }

    public void removeRequest(Player target) {
        tpaRequests.remove(target.getUniqueId());
    }
}
