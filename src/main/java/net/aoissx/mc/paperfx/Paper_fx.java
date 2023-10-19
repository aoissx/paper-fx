package net.aoissx.mc.paperfx;

import net.aoissx.mc.paperfx.commands.Fx;
import net.aoissx.mc.paperfx.db.Database;
import net.aoissx.mc.paperfx.db.PriceDao;
import net.aoissx.mc.paperfx.events.BlockClickEvent;
import net.aoissx.mc.paperfx.events.PriceFluctuation;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Objects;

public final class Paper_fx extends JavaPlugin {

    private BukkitTask task;
    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("PaperFX is enabled.");
        // config.yamlを作成
        saveDefaultConfig();
        // config.yamlを読み込む
        reloadConfig();
        // config.yamlから値を取得
        int defaultPrice = getConfig().getInt("default");
        int min = getConfig().getInt("min");
        int max = getConfig().getInt("max");
        int range = getConfig().getInt("range");
        int interval = getConfig().getInt("interval");
        // PriceFluctuationを起動
        PriceFluctuation priceFluctuation = new PriceFluctuation(min, max, range);
        task = getServer().getScheduler().runTaskTimerAsynchronously(this, priceFluctuation, 0, 20*interval);
        // Database
        Database db = new Database();
        db.Init(defaultPrice);
        PriceDao priceDao = new PriceDao();
        int price = priceDao.getPrice();
        getLogger().info("price: " + price);
        // Commands
        Objects.requireNonNull(getCommand("fx")).setExecutor(new Fx());

        // Events
        getServer().getPluginManager().registerEvents(new BlockClickEvent(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("PaperFX is disabled.");
        task.cancel();
    }
}
