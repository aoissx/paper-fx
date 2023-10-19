package net.aoissx.mc.paperfx.events;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import net.aoissx.mc.paperfx.config.Config;
import net.aoissx.mc.paperfx.config.Gui;
import net.aoissx.mc.paperfx.db.Chest;
import net.aoissx.mc.paperfx.db.ChestDao;

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
                player.openInventory(inv);
                float volume = 1f;
                float pitch = 1f;
                loc.getWorld().playSound(loc, Sound.BLOCK_ANVIL_USE, volume, pitch);
            }
        }
    }

    @EventHandler
    public void inventoryClickEvent(InventoryInteractEvent e){

    }

    @EventHandler
    public void inventoryCloseEvent(InventoryCloseEvent e){
        Player player = (Player) e.getPlayer();
        Set<String> tags = player.getScoreboardTags();
        if(tags.contains(Config.UsingTag)){
            player.removeScoreboardTag(Config.UsingTag);
            player.sendMessage(Config.FxLog("FXを終了します"));
        }
    }

}
