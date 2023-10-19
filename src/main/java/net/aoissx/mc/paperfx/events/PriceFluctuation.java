package net.aoissx.mc.paperfx.events;

import java.util.Random;

import org.bukkit.Bukkit;

import net.aoissx.mc.paperfx.db.PriceDao;

public class PriceFluctuation implements Runnable {
    private final PriceDao priceDao;
    private final Random random;

    private int min;
    private int max;
    private int range;

    public PriceFluctuation(int min, int max, int range) {
        this.priceDao = new PriceDao();
        this.random = new Random();
        this.max = max;
        this.min = min;
        this.range = range;
    }

    @Override
    public void run() {
        int currentPrice = priceDao.getPrice();

        // 現在の値から-rangeから+rangeの範囲内でランダムに変動させる
        int newPrice = currentPrice + random.nextInt(range * 2) - range;

        // minとmaxの範囲内に収まるようにする
        if (newPrice < min) {
            newPrice = min;
        } else if (newPrice > max) {
            newPrice = max;
        }
        priceDao.insertPrice(newPrice);

        // 値段を変動させたことを通知する
        Bukkit.broadcastMessage("§a§l[PaperFX] §r§a価格が変動しました。");
        // currentPriceとnewPriceを通知する
        Bukkit.broadcastMessage("§a§l[PaperFX] §r§a" + currentPrice + " -> " + newPrice);
    }
}