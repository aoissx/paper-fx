package net.aoissx.mc.paperfx;

import net.aoissx.mc.paperfx.commands.Fx;
import net.aoissx.mc.paperfx.db.Database;
import net.aoissx.mc.paperfx.db.PriceDao;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Paper_fx extends JavaPlugin {
    @Override
    public void onEnable() {
        // Plugin startup logic
        // Database
        Database db = new Database();
        db.Init();
        PriceDao priceDao = new PriceDao();
        int price = priceDao.getPrice();
        getLogger().info("price: " + price);
        // Commands
        Objects.requireNonNull(getCommand("fx")).setExecutor(new Fx());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
