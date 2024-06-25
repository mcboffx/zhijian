package org.fgg2333.zhijian;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.fgg2333.zhijian.welcome.player;
import org.fgg2333.zhijian.welcome.guanli;
import org.fgg2333.zhijian.gongneng.bed;
import org.fgg2333.zhijian.gongneng.TpaCommand;
import org.fgg2333.zhijian.gongneng.TpAcceptCommand;
import org.fgg2333.zhijian.gongneng.TpDenyCommand;
import org.fgg2333.zhijian.gongneng.BackCommand;
import org.fgg2333.zhijian.gongneng.BackManager;
import org.fgg2333.zhijian.gongneng.TpaManager;
import org.fgg2333.zhijian.tongji.WajueManager;
import org.fgg2333.zhijian.tongji.WajueListener;
import org.fgg2333.zhijian.tongji.WajueSidebar;
import org.fgg2333.zhijian.command.LocationManager;
import org.fgg2333.zhijian.command.RestpCommand;

public class Zhijian extends JavaPlugin implements Listener {
    private TpaManager tpaManager;
    private BackManager backManager;
    private WajueManager wajueManager;
    private WajueSidebar wajueSidebar;
    private LocationManager locationManager;

    @Override
    public void onEnable() {
        // 插件启动时调用的方法
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        tpaManager = new TpaManager();
        backManager = new BackManager();
        wajueManager = new WajueManager(this);
        wajueSidebar = new WajueSidebar();
        locationManager = new LocationManager(this);

        this.getCommand("zhijian").setExecutor(new about());
        this.getCommand("tpa").setExecutor(new TpaCommand(this));
        this.getCommand("tpaccept").setExecutor(new TpAcceptCommand(this));
        this.getCommand("tpano").setExecutor(new TpDenyCommand(this));
        this.getCommand("back").setExecutor(new BackCommand(this));
        this.getCommand("res").setExecutor(new RestpCommand(locationManager));

        getServer().getPluginManager().registerEvents(new player(), this);
        getServer().getPluginManager().registerEvents(new guanli(this), this);
        getServer().getPluginManager().registerEvents(new bed(this), this);
        getServer().getPluginManager().registerEvents(new WajueListener(this), this);
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // 插件关闭时调用的方法
        if (wajueManager != null) {
            wajueManager.saveData();
        }
    }

    public TpaManager getTpaManager() {
        return tpaManager;
    }

    public BackManager getBackManager() {
        return backManager;
    }

    public WajueManager getWajueManager() {
        return wajueManager;
    }

    public WajueSidebar getWajueSidebar() {
        return wajueSidebar;
    }

    public LocationManager getLocationManager() {
        return locationManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        wajueSidebar.createSidebar(event.getPlayer());
        wajueSidebar.updateAllSidebars();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        backManager.setLastLocation(event.getEntity(), event.getEntity().getLocation());
    }
}
