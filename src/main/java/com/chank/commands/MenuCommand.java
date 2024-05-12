package com.chank.commands;

import com.chank.inventory.MenuInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class MenuCommand implements CommandExecutor {

    // 打开菜单指令 /cd or /cd open
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        System.out.println(s);
        System.out.println(Arrays.toString(strings));
        if (strings.length == 0) {
            Player p = (Player) commandSender;
            p.openInventory(MenuInventory.INSTANCE.getMenuInventory());
            return true;
        } else {
            if (strings[0].equals("open")) {
                Player p = (Player) commandSender;
                p.openInventory(MenuInventory.INSTANCE.getMenuInventory());
                return true;
            }
        }
        return false;
    }
}
