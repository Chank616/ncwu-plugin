package com.chank;

import com.chank.commands.MenuCommand;
import com.chank.commands.TeleportRequestCommand;
import com.chank.listeners.MenuLisner;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public final class NcwuPlugin extends JavaPlugin {

    public static NcwuPlugin main;
    public static final Map<String, Long> requestMap = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("你好，华水小水宝！");

        // 初始化配置文件
        File configFile = new File("plugins/ncwu-plugin", "config.json");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            try {
                configFile.createNewFile();
                Files.write(configFile.toPath(), "{\"home\":[]}".getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // 菜单指令相关
        Bukkit.getPluginCommand("menu").setExecutor(new MenuCommand());
        Bukkit.getPluginCommand("tpa").setExecutor(new TeleportRequestCommand());
        Bukkit.getPluginCommand("tpaccept").setExecutor(new TeleportRequestCommand());
        // 菜单事件相关
        Bukkit.getPluginManager().registerEvents(new MenuLisner(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
