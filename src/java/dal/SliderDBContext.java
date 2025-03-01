/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Slider;
import model.User;

/**
 *
 * @author admin
 */
public class SliderDBContext {

    Connection conn = new DBContext().getConnection();

    public List<Slider> getActiveSliders() {

        List<Slider> sliders = new ArrayList<>();
        String sql = "SELECT SlideID, title, backLink, img, status, authorID FROM slider WHERE Status = 1";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Slider slider = new Slider(
                            rs.getInt("SlideID"),
                            rs.getString("Title"),
                            rs.getString("BackLink"),
                            rs.getString("Img"),
                            rs.getBoolean("Status"),
                            new User(rs.getInt("AuthorID")) // Giả sử User có constructor nhận ID
                    );
                    sliders.add(slider);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, "Error fetching users with pagination", ex);
        }
        return sliders;
    }
}

