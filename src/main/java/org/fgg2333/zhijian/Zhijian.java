package org.fgg2333.zhijian;

import org.bukkit.plugin.java.JavaPlugin;
import org.fgg2333.zhijian.welcome.player;
import org.fgg2333.zhijian.welcome.guanli;
import org.fgg2333.zhijian.gongneng.bed;
import org.fgg2333.zhijian.gongneng.TpaCommand;
import org.fgg2333.zhijian.gongneng.TpAcceptCommand;
import org.fgg2333.zhijian.gongneng.TpDenyCommand;
import org.fgg2333.zhijian.gongneng.TpaManager;

public class Zhijian extends JavaPlugin {
    private TpaManager tpaManager;

    @Override
    public void onEnable() {
        this.getCommand("zhijian").setExecutor(new about());
        this.getCommand("tpa").setExecutor(new TpaCommand(this));
        this.getCommand("tpaccept").setExecutor(new TpAcceptCommand(this));
        this.getCommand("tpano").setExecutor(new TpDenyCommand(this));

        getServer().getPluginManager().registerEvents(new player(), this);
        getServer().getPluginManager().registerEvents(new guanli(this), this);
        getServer().getPluginManager().registerEvents(new bed(this), this);

        tpaManager = new TpaManager();
    }

    @Override
    public void onDisable() {
        // 插件关闭时调用的方法
    }

    public TpaManager getTpaManager() {
        return tpaManager;
    }
}
