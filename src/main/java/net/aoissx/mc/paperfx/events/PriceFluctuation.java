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
    private int median;

    public PriceFluctuation(int min, int max, int range) {
        this.priceDao = new PriceDao();
        this.random = new Random();
        this.max = max;
        this.min = min;
        this.range = range;
        this.median = (max + min) / 2;
    }

    @Override
    public void run() {
        int currentPrice = priceDao.getPrice();

        // 現在の値から-rangeから+rangeの範囲内でランダムに変動させる
        int newPrice = decidePrice(currentPrice);

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

    /*
     * 価格の変動値を設定する。
     */
    private int decidePrice(int currentPrice) {
        int diff = random.nextInt(range+1) - range/2; // 変動値
        int randomValue = random.nextInt(100); // 0~99の乱数
        int width = (max-min)/4; // ボーダー
        // 中央値から離れているほど確率を低くする
        if(currentPrice < median-width && diff < 0){
            if(randomValue < 50){
                diff += random.nextInt(range+1)-range/2;
            }
            if(randomValue < 90){
                diff = -diff;
            }
        }else if(currentPrice > median+width && diff > 0){
            if(randomValue < 50){
                diff += random.nextInt(range+1)-range/2;
            }
            if(randomValue < 90){
                diff = -diff;
            }
        }
        return currentPrice + diff;
    }
}