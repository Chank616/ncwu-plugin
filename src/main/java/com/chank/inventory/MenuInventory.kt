package com.chank.inventory

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

object MenuInventory {
    fun getMenuInventory(): Inventory {
        val inventory = Bukkit.createInventory(null, 54, "测试")
//        for (i in 0..53) {
//            inventory.setItem(i, ItemUtil.getEmptyItem())
//        }
        val itemStack = ItemStack(Material.GOLDEN_APPLE)
        val itemMeta = itemStack.itemMeta?.displayName ?: "§4回到主城"
        inventory.addItem(itemStack)
        return inventory
    }
}
