package net.aoissx.mc.paperfx.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChestDao extends Database{

    public ChestDao(){
        super();
    }
    
    public boolean insert(Chest chest){
        String sql = "INSERT INTO PaperFxChest(x, y, z, world_name) VALUES(?, ?, ?, ?);";
        try(Connection con = DriverManager.getConnection(getPath());
            PreparedStatement stmt = con.prepareStatement(sql);){
            stmt.setInt(1, chest.getX());
            stmt.setInt(2, chest.getY());
            stmt.setInt(3, chest.getZ());
            stmt.setString(4, chest.getWorldName());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Chest chest){
        String sql = "DELETE FROM PaperFxChest WHERE chestId = ?;";
        try(Connection con = DriverManager.getConnection(getPath());
            PreparedStatement stmt = con.prepareStatement(sql);){
            stmt.setInt(1, chest.getChestId());
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Chest chest){
        String sql = "UPDATE PaperFxChest SET x = ?, y = ?, z = ?, world_name = ? WHERE chestId = ?;";
        try(Connection con = DriverManager.getConnection(getPath());
            PreparedStatement stmt = con.prepareStatement(sql);){
            stmt.setInt(1, chest.getX());
            stmt.setInt(2, chest.getY());
            stmt.setInt(3, chest.getZ());
            stmt.setString(4, chest.getWorldName());
            stmt.setInt(5, chest.getChestId());
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public Chest select(int chestId){
        String sql = "SELECT * FROM PaperFxChest WHERE chestId = ?;";
        try(Connection con = DriverManager.getConnection(getPath());
            PreparedStatement stmt = con.prepareStatement(sql);){
            stmt.setInt(1, chestId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                int z = rs.getInt("z");
                String worldName = rs.getString("world_name");
                Chest chest = new Chest(chestId, x, y, z, worldName);
                return chest;
            }else{
                return null;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public Chest selectByLoc(Chest chest){
        String sql = "SELECT * FROM PaperFxChest WHERE x = ? AND y = ? AND z = ? AND world_name = ?;";
        try(Connection con = DriverManager.getConnection(getPath());
            PreparedStatement stmt = con.prepareStatement(sql);){
            stmt.setInt(1, chest.getX());
            stmt.setInt(2, chest.getY());
            stmt.setInt(3, chest.getZ());
            stmt.setString(4, chest.getWorldName());
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                int chestId = rs.getInt("chestId");
                Chest result = new Chest(chestId, chest.getX(), chest.getY(), chest.getZ(), chest.getWorldName());
                return result;
            }else{
                return null;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
