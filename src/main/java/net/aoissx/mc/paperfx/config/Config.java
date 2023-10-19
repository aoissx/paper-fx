package net.aoissx.mc.paperfx.config;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Config {
    public static final String RemoveTag = "FX_REMOVE_TAG";
    public static final String AddTag = "FX_ADD_TAG";
    public static final String UsingTag = "FX_USING_TAG";

    public static final int BUY_BUTTON = 12;
    public static final int SELL_BUTTON = 15;
    public static final int PRICE_SLOT = 0;
    public static final int LEVEL_SLOT = 9;

    public static final Material FX_MATERIAL = Material.DIAMOND_BLOCK;
    public static final Material FX_PAPER = Material.PAPER;
    public static final Material FX_LEVEL = Material.EXPERIENCE_BOTTLE;

    public static String FxLog(String msg){
        return "§a§l[PaperFX] §r§a" + msg;
    }

    public static String FxLogError(String msg){
        return "§c§l[PaperFX] §r§c" + msg;
    }

}
