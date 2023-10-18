package net.aoissx.mc.paperfx.db;

import java.sql.*;

public class Database {
    private String path = "jdbc:sqlite:plugins/paper-fx/fx.db";

    public String getPath(){
        return this.path;
    }

    public void Init(){
        String chestSql = "CREATE TABLE IF NOT EXISTS PaperFxChest(" +
                "chestId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "x INTEGER," +
                "y INTEGER," +
                "z INTEGER," +
                "world_name TEXT" +
                ");";

        String priceSql = "CREATE TABLE IF NOT EXISTS PaperFxPrice(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "price INTEGER" +
                ");";

        String initInsertSql = "INSERT INTO PaperFxPrice(price) VALUES(?);";

        try(Connection con = DriverManager.getConnection(getPath());
            Statement stmt = con.createStatement();){
            stmt.execute(chestSql);
            stmt.execute(priceSql);

            // TODO:ここにpriceテーブルの行数をカウントして０の場合のみ初期データをインサートする処理を追加

        }catch(SQLException e){
            e.printStackTrace();
        }
    }


}
