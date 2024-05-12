package com.chank.listeners;

import com.chank.inventory.MenuInventory;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

import static com.chank.NcwuPlugin.main;

public class MenuLisner implements Listener {
    //监听玩家右键交互事件
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        //获取玩家
        Player player = event.getPlayer();
        //获取主手上拿的物品堆
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        //获取该物品堆的种类
        Material type = itemStack.getType();
        //判断是否为钟表
        if (type.equals(Material.CLOCK)) {
            //是钟表的话，判断是否右键
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                //是右键的话，打开菜单盒子
                Inventory inventory = Bukkit.createInventory(null, 54, "菜单");
                ItemStack diamond = new ItemStack(Material.DIAMOND);
                ItemMeta itemMeta = diamond.getItemMeta();
                itemMeta.setDisplayName("§4回城");
                ArrayList<String> lore = new ArrayList<>();
                lore.add("点击快速回城");
                lore.add("附加文字");
                itemMeta.setLore(lore);
                diamond.setItemMeta(itemMeta);
                inventory.setItem(0, diamond);
                player.openInventory(inventory);
            }
        }
    }

    // 监听玩家点击菜单盒子的事件
    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {
        // 获取此次箱子点击时间的箱子实体
        if (event.getView().getTitle().equals("菜单")) {
            // 传送回主城
            if (event.getRawSlot() == 0) {
                HumanEntity whoClicked = event.getWhoClicked();
                whoClicked.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
                event.setCancelled(true);
            }
            // 设置第一个家
            if (event.getRawSlot() == 9 * 2 + 1) {
                // 第一个家的物品
                ItemStack firstHome = new ItemStack(Material.RED_BED);
                ItemMeta firstHomeMeta = firstHome.getItemMeta();
                firstHomeMeta.setDisplayName("§4设置第一个家");
                ArrayList<String> firstHomeMetaLore = new ArrayList<>();
                firstHomeMetaLore.add("点击将此位置设置为第一个家");
                firstHomeMetaLore.add("附加文字");
                firstHomeMeta.setLore(firstHomeMetaLore);
                firstHome.setItemMeta(firstHomeMeta);
                // 给他加入菜单
                event.getInventory().setItem(9 * 2 + 1, firstHome);

                // 写入配置文件，遍历home数组，如果home里没有该玩家的playerName，则创建
                if (main.getConfig().getConfigurationSection("home") == null) {
                    main.getConfig().set("home", null);
                    main.saveConfig();
                }
                if (main.getConfig().contains("home.0.playerName")) {
                    main.getConfig().set("home.0.playerName", "");
                    main.getConfig().set("home.0.name", "");
                    main.getConfig().set("home.0.one.0", 0);
                    main.getConfig().set("home.0.one.1", 0);
                    main.getConfig().set("home.0.one.2", 0);
                    main.getConfig().set("home.0.one.3", 0);
                    main.saveConfig();
                }

                main.getConfig().set("home.0.name", "");
                main.getConfig().set("home.0.one.0", 0);
                main.getConfig().set("home.0.one.1", 0);
                main.getConfig().set("home.0.one.2", 0);
                main.getConfig().set("home.0.one.3", 0);
                main.saveConfig();

                // 防止拿出来
                event.setCancelled(true);
            }
        }
    }

    // 玩家进入服务器事件
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        // 发送欢迎语
        player.sendMessage(player.getName() + "哎呦我，你来了");

    }
}
