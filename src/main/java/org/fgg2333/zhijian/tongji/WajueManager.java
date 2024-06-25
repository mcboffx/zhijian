package org.fgg2333.zhijian.tongji;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class WajueManager {
    private final Map<UUID, Integer> playerBlockBreaks = new HashMap<>();
    private final Map<UUID, String> playerNames = new HashMap<>();
    private final File dataFile;
    private final Gson gson = new Gson();

    public WajueManager(JavaPlugin plugin) {
        this.dataFile = new File(plugin.getDataFolder(), "wajue_data.json");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        loadData();
    }

    public void incrementBlockBreaks(Player player) {
        UUID playerId = player.getUniqueId();
        playerBlockBreaks.put(playerId, playerBlockBreaks.getOrDefault(playerId, 0) + 1);
        playerNames.put(playerId, player.getName());
    }

    public int getBlockBreaks(Player player) {
        return playerBlockBreaks.getOrDefault(player.getUniqueId(), 0);
    }

    public Map<UUID, Integer> getAllBlockBreaks() {
        return new HashMap<>(playerBlockBreaks);
    }

    public String getPlayerName(UUID playerId) {
        return playerNames.getOrDefault(playerId, playerId.toString());
    }

    public void saveData() {
        try (FileWriter writer = new FileWriter(dataFile)) {
            Map<String, Object> data = new HashMap<>();
            data.put("blockBreaks", playerBlockBreaks);
            data.put("playerNames", playerNames);
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        try (FileReader reader = new FileReader(dataFile)) {
            Map<String, Object> data = gson.fromJson(reader, new TypeToken<Map<String, Object>>() {}.getType());
            if (data != null) {
                Map<String, Double> blockBreaks = (Map<String, Double>) data.get("blockBreaks");
                if (blockBreaks != null) {
                    blockBreaks.forEach((k, v) -> playerBlockBreaks.put(UUID.fromString(k), v.intValue()));
                }
                Map<String, String> names = (Map<String, String>) data.get("playerNames");
                if (names != null) {
                    names.forEach((k, v) -> playerNames.put(UUID.fromString(k), v));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
