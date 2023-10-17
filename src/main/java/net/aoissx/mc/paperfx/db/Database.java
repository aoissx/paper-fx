package net.aoissx.mc.paperfx.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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

        try(Connection con = DriverManager.getConnection(getPath());
            Statement stmt = con.createStatement()){
            stmt.execute(chestSql);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


}
