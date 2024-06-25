package org.fgg2333.zhijian.tongji;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.fgg2333.zhijian.Zhijian;

import java.util.*;
import java.util.stream.Collectors;

public class WajueSidebar {
    private final ScoreboardManager manager;
    private final Map<UUID, Scoreboard> playerScoreboards = new HashMap<>();

    public WajueSidebar() {
        manager = Bukkit.getScoreboardManager();
    }

    public void createSidebar(Player player) {
        Scoreboard scoreboard = manager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("wajue", "dummy", ChatColor.GREEN + "挖掘排行榜");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        playerScoreboards.put(player.getUniqueId(), scoreboard);
        updateSidebar(player);
    }

    public void updateSidebar(Player player) {
        Scoreboard scoreboard = playerScoreboards.get(player.getUniqueId());
        if (scoreboard == null) {
            createSidebar(player);
            return;
        }

        Objective objective = scoreboard.getObjective("wajue");
        if (objective == null) {
            objective = scoreboard.registerNewObjective("wajue", "dummy", ChatColor.GREEN + "挖掘排行榜");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        }

        // 先清除之前的条目
        for (String entry : scoreboard.getEntries()) {
            scoreboard.resetScores(entry);
        }

        Zhijian plugin = (Zhijian) Bukkit.getPluginManager().getPlugin("zhijian");
        if (plugin == null) {
            return;
        }

        WajueManager wajueManager = plugin.getWajueManager();
        Map<UUID, Integer> allBlockBreaks = wajueManager.getAllBlockBreaks();

        // 获取前10名玩家
        List<Map.Entry<UUID, Integer>> topPlayers = allBlockBreaks.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .collect(Collectors.toList());

        objective.getScore(ChatColor.YELLOW + "------Top 10------").setScore(11);

        int rank = 1;
        for (Map.Entry<UUID, Integer> entry : topPlayers) {
            String name = wajueManager.getPlayerName(entry.getKey());
            objective.getScore(ChatColor.YELLOW + "#" + rank + " " + name).setScore(entry.getValue());
            rank++;
        }

        player.setScoreboard(scoreboard);
    }

    public void updateAllSidebars() {
        for (UUID playerId : playerScoreboards.keySet()) {
            Player player = Bukkit.getPlayer(playerId);
            if (player != null && player.isOnline()) {
                updateSidebar(player);
            }
        }
    }
}
