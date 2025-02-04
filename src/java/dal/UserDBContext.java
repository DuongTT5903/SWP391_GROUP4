package dal;

import java.sql.*;
import model.User;

public class UserDBContext {

    // Phương thức lấy người dùng từ cơ sở dữ liệu theo username và password
    public User getUserByUsername(String username, String password) {
        User user = null;
        String query = "SELECT userID,name,gender,email,username,password,phone,roleID,imageURL FROM users WHERE Username = ? AND Password = ?";

        // Use try-with-resources to ensure proper closing of resources
        try (Connection conn = DBContext.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set parameters for the query
            stmt.setString(1, username);
            stmt.setString(2, password);

            // Execute the query
            ResultSet rs = stmt.executeQuery();

            // Process the result
            if (rs.next()) {
                user = new User(
                    rs.getInt("userID"),
                    rs.getString("name"),
                    rs.getBoolean("gender"),
                    rs.getString("email"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("phone"),
                    rs.getString("roleID"), // Change to match your User class field
                        rs.getString("imageURL")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle error if any
        }

        return user;
    }
       public String getRoleIDByUsernameAndPassword(String username, String password) {
        String roleID = null;
        String query = "SELECT roleID FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                roleID = rs.getString("roleID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roleID;
    }

}
    