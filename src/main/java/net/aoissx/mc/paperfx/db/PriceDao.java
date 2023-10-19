package net.aoissx.mc.paperfx.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PriceDao extends Database{
    public PriceDao(){
        super();
    }

    public void insertPrice(int price) {
        String sql = "INSERT INTO PaperFxPrice(price) VALUES(?);";
        try (Connection conn = DriverManager.getConnection(getPath());
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, price);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getPrice() {
        String sql = "SELECT price FROM PaperFxPrice ORDER BY id DESC LIMIT 1;";
        try (Connection conn = DriverManager.getConnection(getPath());
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
