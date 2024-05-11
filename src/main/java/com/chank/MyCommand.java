package com.chank;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        String c = strings[0];
        if (c.equals("open")) {
            Inventory inventory = Bukkit.createInventory(null, 54, "测试");
            Player p = (Player) commandSender;
            p.openInventory(inventory);
        }
        return false;
    }
}
