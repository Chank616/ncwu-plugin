package com.chank;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class MyListener implements Listener {
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
            if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                //是右键的话，打开一个盒子
                Inventory inventory = Bukkit.createInventory(null, 54, "菜单");
                player.openInventory(inventory);
            }
        }
    }
}
