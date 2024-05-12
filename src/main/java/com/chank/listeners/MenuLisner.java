package com.chank.listeners;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.chank.NcwuPlugin.main;

public class MenuLisner implements Listener {
    private final Map<UUID, Location> deathLocations = new HashMap<>();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Location deathLocation = player.getLocation();
        deathLocations.put(player.getUniqueId(), deathLocation);
    }

    //监听传送事件
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        Location deathLocation = event.getFrom();
        deathLocations.put(player.getUniqueId(), deathLocation);
    }

    public Location getDeathLocation(UUID playerId) {
        return deathLocations.get(playerId);
    }

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
                // 回城按钮
                ItemStack diamond = new ItemStack(Material.DIAMOND);
                ItemMeta itemMeta = diamond.getItemMeta();
                itemMeta.setDisplayName("§4回城");
                ArrayList<String> lore = new ArrayList<>();
                lore.add("养精蓄锐^_^");
                itemMeta.setLore(lore);
                diamond.setItemMeta(itemMeta);
                inventory.setItem(4, diamond);
                // 回死亡地点按钮
                ItemStack bone = new ItemStack(Material.BONE);
                ItemMeta boneItemMeta = bone.getItemMeta();
                boneItemMeta.setDisplayName("§5回到上一个地点");
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
                    inventory.setItem(20 + i, home);
                }


                player.openInventory(inventory);
            }
        }
    }

    // 监听玩家点击菜单盒子的事件
    @EventHandler
    public void onMenuClick(InventoryClickEvent event) throws IOException {
        // 获取此次箱子点击时间的箱子实体
        if (event.getView().getTitle().equals("菜单")) {
            // 传送回主城
            if (event.getRawSlot() == 4) {
                HumanEntity whoClicked = event.getWhoClicked();
                whoClicked.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
                event.setCancelled(true);
            }
            // 传送回死亡地点
            if (event.getRawSlot() == 31) {
                HumanEntity player = event.getWhoClicked();
                UUID playerId = player.getUniqueId();
                Location deathLocation = this.getDeathLocation(playerId);
                if (deathLocation != null) {
                    player.teleport(deathLocation);
                    player.sendMessage("您已被传送到上一个地点。");
                } else {
                    player.sendMessage("您还没有上一次记录的地点！");
                }
                event.setCancelled(true);
            }
            // 设置家
            if (event.getRawSlot() >= 11 && event.getRawSlot() <= 15) {
                HumanEntity whoClicked = event.getWhoClicked();
                byte[] bytes = Files.readAllBytes(Paths.get("plugins/ncwu-plugin/config.json"));
                String jsonContent = new String(bytes);
                // 将 JSON 字符串转换为 JSON 对象
                JSONObject config = JSON.parseObject(jsonContent);
                System.out.println(config);
                // 遍历home项
                JSONArray jsonArray = config.getJSONArray("home");
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String id = jsonObject.getString("id");
                    if (id.equals(whoClicked.getName())) {
                        JSONArray order = jsonObject.getJSONArray("order");
                        System.out.println(order);
                        Location location = whoClicked.getLocation();
                        String worldName = location.getWorld().getName(); // 获取世界名称
                        int x = (int) location.getX(); // 获取X坐标
                        int y = (int) location.getY(); // 获取Y坐标
                        int z = (int) location.getZ(); // 获取Z坐标
                        order.set(event.getRawSlot() % 11, JSON.parseObject("{\"world\": \"" + worldName + "\", \"x\": " + x + ", \"y\": " + y + ", \"z\": " + z + "}"));
                        break;
                    }
                }
                // 将 JSON 对象转换为 JSON 字符串
                String newJsonContent = JSON.toJSONString(config);
                Files.write(Paths.get("plugins/ncwu-plugin/config.json"), newJsonContent.getBytes());
                // 防止拿出来
                event.setCancelled(true);
            }
            // 回到家
            if (event.getRawSlot() >= 20 && event.getRawSlot() <= 24) {
                HumanEntity whoClicked = event.getWhoClicked();
                byte[] bytes = Files.readAllBytes(Paths.get("plugins/ncwu-plugin/config.json"));
                String jsonContent = new String(bytes);
                // 将 JSON 字符串转换为 JSON 对象
                JSONObject config = JSON.parseObject(jsonContent);
                System.out.println(config);
                // 遍历home项
                JSONArray jsonArray = config.getJSONArray("home");
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String id = jsonObject.getString("id");
                    if (id.equals(whoClicked.getName())) {
                        JSONArray order = jsonObject.getJSONArray("order");
                        HumanEntity p = event.getWhoClicked();
                        JSONObject jsonObject1 = order.getJSONObject(event.getRawSlot() % 20);
                        if (jsonObject1.equals(new JSONObject())) {
                            // 发送欢迎语
                            p.sendMessage(p.getName() + "此家尚未设置");
                        } else {
                            p.teleport(new Location(Bukkit.getWorld(jsonObject1.getString("world")), jsonObject1.getIntValue("x"), jsonObject1.getIntValue("y"), jsonObject1.getIntValue("z")));
                            break;
                        }

                    }
                }
                // 防止拿出来
                event.setCancelled(true);
            }
        }
    }

    // 玩家进入服务器事件
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
//        // 发送欢迎语
//        player.sendMessage(player.getName() + "哎呦我，你来了");
        // 初始化玩家的家设置
        try {
            boolean exist = false;
            byte[] bytes = Files.readAllBytes(Paths.get("plugins/ncwu-plugin/config.json"));
            String jsonContent = new String(bytes);
            // 将 JSON 字符串转换为 JSON 对象
            JSONObject config = JSON.parseObject(jsonContent);
            // 遍历home项
            JSONArray jsonArray = config.getJSONArray("home");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                if (id.equals(player.getName())) {
                    exist = true;
                    break;
                }
            }
            if (!exist) {
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
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", player.getName());
                jsonObject.put("order", new JSONArray());
                JSONArray jsonArray1 = jsonObject.getJSONArray("order");
                jsonArray1.add(0, new JSONObject());
                jsonArray1.add(1, new JSONObject());
                jsonArray1.add(2, new JSONObject());
                jsonArray1.add(3, new JSONObject());
                jsonArray1.add(4, new JSONObject());
                jsonArray.add(jsonObject);
                // 将 JSON 对象转换为 JSON 字符串
                String newJsonContent = JSON.toJSONString(config);
                Files.write(Paths.get("plugins/ncwu-plugin/config.json"), newJsonContent.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
