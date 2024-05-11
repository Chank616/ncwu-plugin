package com.chank;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Ncwu_plugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("你好，华水小水宝！");
        Bukkit.getPluginCommand("myplugin").setExecutor(new MyCommand());
        Bukkit.getPluginManager().registerEvents(new MyListener(),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
