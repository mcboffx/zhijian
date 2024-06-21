package org.fgg2333.zhijian;

import org.bukkit.plugin.java.JavaPlugin;
import org.fgg2333.zhijian.welcome.player;
import org.fgg2333.zhijian.welcome.guanli;
import org.fgg2333.zhijian.gongneng.bed;

public class Zhijian extends JavaPlugin {

    @Override
    public void onEnable() {
        // 插件启动时调用的方法
        this.getCommand("zhijian").setExecutor(new about());
        getServer().getPluginManager().registerEvents(new player(), this);
        getServer().getPluginManager().registerEvents(new guanli(this), this);
        getServer().getPluginManager().registerEvents(new bed(this), this);
    }

    @Override
    public void onDisable() {
        // 插件关闭时调用的方法
    }
}
