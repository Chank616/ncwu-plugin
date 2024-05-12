package com.chank.commands;

import com.chank.inventory.MenuInventory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class MenuCommand implements CommandExecutor {

    // 打开菜单指令 /cd or /cd open
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        if (strings.length == 0 || strings[0].equals("open")) {
            //是右键的话，打开菜单盒子
            Inventory inventory = Bukkit.createInventory(null, 54, "菜单");
            // 回城按钮
            ItemStack diamond = new ItemStack(Material.DIAMOND);
            ItemMeta itemMeta = diamond.getItemMeta();
            itemMeta.setDisplayName("§4回城");
            ArrayList<String> lore = new ArrayList<>();
            lore.add("点击快速回城");
            lore.add("附加文字");
            itemMeta.setLore(lore);
            diamond.setItemMeta(itemMeta);
            inventory.setItem(4, diamond);
            // 回死亡地点按钮
            ItemStack bone = new ItemStack(Material.BONE);
            ItemMeta boneItemMeta = bone.getItemMeta();
            boneItemMeta.setDisplayName("§5回到死亡地点");
            ArrayList<String> bonelore = new ArrayList<>();
            bonelore.add("再战百世轮回！");
            boneItemMeta.setLore(bonelore);
            bone.setItemMeta(boneItemMeta);
            inventory.setItem(31, bone);
            // 设置家物品
            for (int i = 0; i < 5; i++) {
                ItemStack home = new ItemStack(Material.RED_BED);
                ItemMeta firstHomeMeta = home.getItemMeta();
                firstHomeMeta.setDisplayName("§4设置第" + (i + 1) + "个家");
                ArrayList<String> firstHomeMetaLore = new ArrayList<>();
                firstHomeMetaLore.add("点击将此位置设置为第" + (i + 1) + "个家");
                firstHomeMeta.setLore(firstHomeMetaLore);
                home.setItemMeta(firstHomeMeta);
                // 给他加入菜单
                inventory.setItem(11 + i, home);
            }
            // 回到家
            for (int i = 0; i < 5; i++) {
                ItemStack home = new ItemStack(Material.GREEN_BED);
                ItemMeta firstHomeMeta = home.getItemMeta();
                firstHomeMeta.setDisplayName("§4回到第" + (i + 1) + "个家");
                ArrayList<String> firstHomeMetaLore = new ArrayList<>();
                firstHomeMetaLore.add("回到第" + (i + 1) + "个家");
                firstHomeMeta.setLore(firstHomeMetaLore);
                home.setItemMeta(firstHomeMeta);
                // 给他加入菜单
                inventory.setItem(22 + i, home);
            }
            player.openInventory(inventory);
            return true;
        }
        if (strings[0].equals("get")) {
            // 创建一个钟表物品对象
            ItemStack clock = new ItemStack(Material.CLOCK, 1);
            // 获取钟表物品的元数据
            ItemMeta meta = clock.getItemMeta();
            // 设置物品的自定义名称，使用ChatColor来设置颜色
            meta.setDisplayName("§c快捷菜单"); // "§c"是红色
            // 可以设置lore信息，这里不设置，所以传递一个空数组
            meta.setLore(Arrays.asList("§b省时省力！", "§b大豫高皇帝的全新发明！"));
            // 应用元数据到物品对象
            clock.setItemMeta(meta);
            // 将钟表添加到玩家的背包中
            player.getInventory().addItem(clock);
            // 给玩家发送一条消息，告知他们收到了一个钟表
            player.sendMessage("§c[电脑配件]§b你收到了来自大豫高皇帝的礼物！如若丢失，可通过/mu get重新获取！");
            return true;
        }
        return false;
    }
}
