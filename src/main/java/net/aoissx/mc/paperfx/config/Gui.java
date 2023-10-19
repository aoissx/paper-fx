package net.aoissx.mc.paperfx.config;

import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import net.aoissx.mc.paperfx.db.PriceDao;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.chat.TextComponent;

// import net.kyori.adventure.text.TextComponent;

public class Gui {
    /*
     * 初期Guiを作成する。
     */
    public Inventory CreateGUI(){
        Inventory inv = Bukkit.createInventory(null, 9*3, "PaperFX");

        // 背景を設定する。
        inv = fillChest(inv);
        //　売買ボタンを設定する。
        inv = setBuySellButton(inv);
        // 現在の価格を表示する。
        PriceDao priceDao = new PriceDao();
        int price = priceDao.getPrice();
        inv = setPrice(inv, price);
        
        return inv;
    }

    /*
     * 背景を設定する。
     */
    private Inventory fillChest(Inventory inv){
        ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        int size = inv.getSize();
        for(int i = 0; i < size; i++){
            inv.setItem(i, item);
        }
        return inv;
    }

    /*
     * 売買ボタンを設定
     */
    private Inventory setBuySellButton(Inventory inv){
        ItemStack buy = new ItemStack(Material.CHEST);
        ItemMeta buymeta = buy.getItemMeta();
        buymeta.setDisplayName("§a§l買う");
        buy.setItemMeta(buymeta);
        ItemStack sell = new ItemStack(Material.CHEST);
        ItemMeta sellmeta = sell.getItemMeta();
        sellmeta.setDisplayName("§a§l売る");
        sell.setItemMeta(sellmeta);

        inv.setItem(Config.BUY_BUTTON, buy);
        inv.setItem(Config.SELL_BUTTON, sell);
        return inv;
    }

    /*
     * 現在の価格を表示する。
     */
    private Inventory setPrice(Inventory inv, int price) {
        ItemStack item = new ItemStack(Config.FX_PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§a§l現在の価格");
        meta.setLore(Collections.singletonList("§a§l" + price));
        item.setItemMeta(meta);
        inv.setItem(Config.PRICE_SLOT, item);
        return inv;
    }

    /*
     * プレイヤーのレベルを表示する。
     */
    public void setLevel(Inventory inv, int level){
        ItemStack item = new ItemStack(Config.FX_LEVEL);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§a§l現在のレベル");
        meta.setLore(Collections.singletonList("§a§l" + level));
        item.setItemMeta(meta);
        inv.setItem(4, item);
    }
}
