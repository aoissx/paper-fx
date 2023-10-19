package net.aoissx.mc.paperfx.db;

import java.sql.*;

public class Database {
    private String path = "jdbc:sqlite:plugins/paper-fx/fx.db";

    public String getPath(){
        return this.path;
    }

    public void Init(int defaultPrice){
        String chestSql = "CREATE TABLE IF NOT EXISTS PaperFxChest(" +
                "chestId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "x INTEGER," +
                "y INTEGER," +
                "z INTEGER," +
                "world_name TEXT" +
                ");";

        String priceSql = "CREATE TABLE IF NOT EXISTS PaperFxPrice(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "price INTEGER," +
                "created_at TEXT NOT NULL DEFAULT (DATETIME('now', 'localtime'))"+
                ");";

        String initInsertSql = "INSERT INTO PaperFxPrice(price) VALUES(?);";

        try(Connection con = DriverManager.getConnection(getPath());
            Statement stmt = con.createStatement();){
            stmt.execute(chestSql);
            stmt.execute(priceSql);

            // Priceテーブルの行数を検索
            String countSql = "SELECT COUNT(*) FROM PaperFxPrice;";
            PreparedStatement countStmt = con.prepareStatement(countSql);
            ResultSet countRs = countStmt.executeQuery();
            int count = countRs.getInt(1);
            if(count == 0){
                // 初期データをインサート
                PreparedStatement initInsertStmt = con.prepareStatement(initInsertSql);
                initInsertStmt.setInt(1, defaultPrice);
                initInsertStmt.executeUpdate();
                initInsertStmt.close();
            }
            countRs.close();
            countStmt.close();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }


}
