package org.fgg2333.zhijian.tongji;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.fgg2333.zhijian.Zhijian;

public class WajueListener implements Listener {
    private final Zhijian plugin;

    public WajueListener(Zhijian plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        plugin.getWajueManager().incrementBlockBreaks(event.getPlayer());
        plugin.getWajueSidebar().updateAllSidebars();
    }
}
