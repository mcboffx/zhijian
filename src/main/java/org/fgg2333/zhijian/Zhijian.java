package org.fgg2333.zhijian;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.fgg2333.zhijian.welcome.player;
import org.fgg2333.zhijian.welcome.guanli;
import org.fgg2333.zhijian.gongneng.bed;
import org.fgg2333.zhijian.gongneng.TpaCommand;
import org.fgg2333.zhijian.gongneng.TpAcceptCommand;
import org.fgg2333.zhijian.gongneng.TpDenyCommand;
import org.fgg2333.zhijian.gongneng.TpaManager;
import org.fgg2333.zhijian.gongneng.BackCommand;
import org.fgg2333.zhijian.gongneng.BackManager;

public class Zhijian extends JavaPlugin implements Listener {
    private TpaManager tpaManager;
    private BackManager backManager;

    @Override
    public void onEnable() {
        // 插件启动时调用的方法
        this.getCommand("zhijian").setExecutor(new about());
        this.getCommand("tpa").setExecutor(new TpaCommand(this));
        this.getCommand("tpaccept").setExecutor(new TpAcceptCommand(this));
        this.getCommand("tpano").setExecutor(new TpDenyCommand(this));
        this.getCommand("back").setExecutor(new BackCommand(this));

        getServer().getPluginManager().registerEvents(new player(), this);
        getServer().getPluginManager().registerEvents(new guanli(this), this);
        getServer().getPluginManager().registerEvents(new bed(this), this);
        getServer().getPluginManager().registerEvents(this, this);

        tpaManager = new TpaManager();
        backManager = new BackManager();
    }

    @Override
    public void onDisable() {
        // 插件关闭时调用的方法
    }

    public TpaManager getTpaManager() {
        return tpaManager;
    }

    public BackManager getBackManager() {
        return backManager;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        backManager.setLastLocation(event.getEntity(), event.getEntity().getLocation());
    }
}
