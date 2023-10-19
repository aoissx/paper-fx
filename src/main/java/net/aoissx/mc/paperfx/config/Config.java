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

    public static String FxLog(String msg){
        return "§a§l[PaperFX] §r§a" + msg;
    }

    public static String FxLogError(String msg){
        return "§c§l[PaperFX] §r§c" + msg;
    }

}
