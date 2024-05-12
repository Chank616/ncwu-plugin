package com.chank;

import com.chank.commands.MenuCommand;
import com.chank.commands.MyCommand;
import com.chank.listeners.MenuLisner;
import com.chank.listeners.MyListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class NcwuPlugin extends JavaPlugin {

    public static NcwuPlugin main;
    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("你好，华水小水宝！");
        // 菜单指令相关
        Bukkit.getPluginCommand("menu").setExecutor(new MenuCommand());
        // 菜单事件相关
        Bukkit.getPluginManager().registerEvents(new MenuLisner(), this);

        // 生成配置文件
        saveConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
