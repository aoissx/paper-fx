package net.aoissx.mc.paperfx.events;

import java.util.Collections;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.aoissx.mc.paperfx.config.Config;
import net.aoissx.mc.paperfx.config.Gui;
import net.aoissx.mc.paperfx.db.Chest;
import net.aoissx.mc.paperfx.db.ChestDao;
import net.aoissx.mc.paperfx.db.PriceDao;
import net.md_5.bungee.api.chat.hover.content.Item;

public class BlockClickEvent implements Listener {

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e){
        Block block = e.getBlock();
        Location loc = block.getLocation();
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        String worldName = loc.getWorld().getName();

        Chest chest = new Chest(0, x, y, z, worldName);
        ChestDao chestDao = new ChestDao();
        Chest chestFromDb = chestDao.selectByLoc(chest);
        if(chestFromDb != null){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockClickEvent(PlayerInteractEvent e){
        Player player = e.getPlayer();
        Block block = e.getClickedBlock();
        if(block == null){
            return;
        }
        Location loc = block.getLocation();
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        String worldName = loc.getWorld().getName();

        Chest chest = new Chest(0, x, y, z, worldName);
        ChestDao chestDao = new ChestDao();
        Chest chestFromDb = chestDao.selectByLoc(chest);
        if(chestFromDb != null){
            Set<String> tags = player.getScoreboardTags();
            if(tags.contains(Config.RemoveTag)){
                chestDao.delete(chestFromDb);
                player.sendMessage("§a§l[PaperFX] §r§aチェストを削除しました");
            }else if(tags.contains(Config.AddTag)){
                player.sendMessage(Config.FxLogError("すでに登録されています"));
            }else if (!tags.contains(Config.UsingTag)){
                // fx start event
                player.sendMessage(Config.FxLog("FXを開始します"));
                player.addScoreboardTag(Config.UsingTag);
                e.setCancelled(true);
                Gui gui = new Gui();
                Inventory inv = gui.CreateGUI();
                int level = player.getLevel();
                gui.setLevel(inv, level);

                player.openInventory(inv);
                float volume = 1f;
                float pitch = 1f;
                loc.getWorld().playSound(loc, Sound.BLOCK_ANVIL_USE, volume, pitch);
            }
        }else{
            Set<String> tags = player.getScoreboardTags();
            if(tags.contains(Config.AddTag)){
                chestDao.insert(chest);
                player.sendMessage("§a§l[PaperFX] §r§aチェストを登録しました");
            }else if(tags.contains(Config.RemoveTag)){
                player.sendMessage(Config.FxLogError("登録されていません"));
            }
        }
    }

    @EventHandler
    public void inventoryClickEvent(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        Set<String> tags = player.getScoreboardTags();

        if(tags.contains(Config.UsingTag)){
            // player.sendMessage(Config.FxLogError("FX中はインベントリを操作できません"));
            e.setCancelled(true);
        }else{
            return;
        }

        Inventory inv = e.getInventory();
        int slot = e.getSlot();
        Inventory playerInv = player.getInventory();
        int level = player.getLevel();

        // invからpriceを取得する。
        ItemStack priceItem = inv.getItem(Config.PRICE_SLOT);
        ItemMeta priceMeta = priceItem.getItemMeta();
        String priceStr = priceMeta.getLore().get(0);
        int price = Integer.parseInt(priceStr);

        Gui gui = new Gui();

        if(slot == Config.SELL_BUTTON){
            // 売却処理
            
            // プレイヤーインベントリにFXがあるか確認する。
            ItemStack fx = new ItemStack(Config.FX_MATERIAL);
            int fxCount = playerInv.all(fx).size();
            if(fxCount == 0){
                player.sendMessage(Config.FxLogError("FX商品がありません"));
                return;
            }

            // プレイヤーインベントリからFXを一つ減らす
            playerInv.removeItem(fx);

            // レベルを増やす
            player.setLevel(level + price);

            player.sendMessage(Config.FxLog("売りました"));

        }else if(slot == Config.BUY_BUTTON){
            // 購入処理
            if(level < price){
                // 所持レベルが不足している場合の処理
                player.sendMessage(Config.FxLogError("レベルが足りません"));
                return;
            }
            // プレイヤーインベントリがいっぱいの場合の処理
            boolean isFull = playerInv.firstEmpty() == -1;
            if(isFull){
                player.sendMessage(Config.FxLogError("インベントリがいっぱいです"));
                return;
            }

            // レベルを減らす
            player.setLevel(level - price);

            // プレイヤーインベントリにFXを追加する
            ItemStack fx = new ItemStack(Config.FX_MATERIAL);
            playerInv.addItem(fx);
            
            player.sendMessage(Config.FxLog("買いました"));
        }else{
            return;
        }

        // 価格を更新する。
        PriceDao priceDao = new PriceDao();
        int newPrice = priceDao.getPrice();

        // 価格を表示する。
        ItemStack item = new ItemStack(Config.FX_PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§a§l現在の価格");
        meta.setLore(Collections.singletonList(String.valueOf(newPrice)));
        item.setItemMeta(meta);
        inv.setItem(Config.PRICE_SLOT, item);
        
        // 現在のレベルを表示する。
        int newLevel = player.getLevel();
        player.sendMessage(Config.FxLog("現在のレベル: " + newLevel));
        gui.setLevel(inv, newLevel);

        // サウンドを鳴らす。
        // Location loc = player.getLocation();
        // float volume = 1f;
        // float pitch = 1f;
        // loc.getWorld().playSound(loc, Sound.BLOCK_ANVIL_USE, volume, pitch);
        
    }

    /*
     * アイテム移動をキャンセルする。
     */
    // @EventHandler
    // public void inventoryMoveEvent(InventoryMoveItemEvent e){
    //     Player player = (Player) e.getDestination().getHolder();
    //     Set<String> tags = player.getScoreboardTags();
    //     if(tags.contains(Config.UsingTag)){
    //         e.setCancelled(true);
    //     }
    // }

    /*
     * インベントリを閉じたときに、FXを終了する。
     */
    @EventHandler
    public void inventoryCloseEvent(InventoryCloseEvent e){
        Player player = (Player) e.getPlayer();
        Set<String> tags = player.getScoreboardTags();
        if(tags.contains(Config.UsingTag)){
            player.removeScoreboardTag(Config.UsingTag);
            player.sendMessage(Config.FxLog("FXを終了します"));
        }
    }

    // @EventHandler
    // public void onInventoryInteractEvent(InventoryInteractEvent e){
    //     Player player = (Player) e.getWhoClicked();
    //     Set<String> tags = player.getScoreboardTags();
    //     if(tags.contains(Config.UsingTag)){
    //         e.setCancelled(true);
    //     }
    // }

}
