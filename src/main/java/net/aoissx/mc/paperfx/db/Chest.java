package net.aoissx.mc.paperfx.db;

public class Chest {
    private int chestId;
    private int x;
    private int y;
    private int z;
    private String worldName;

    public Chest(int chestId, int x, int y, int z, String worldName){
        this.chestId = chestId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.worldName = worldName;
    }

    public int getChestId(){
        return this.chestId;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getZ(){
        return this.z;
    }

    public String getWorldName(){
        return this.worldName;
    }
}
