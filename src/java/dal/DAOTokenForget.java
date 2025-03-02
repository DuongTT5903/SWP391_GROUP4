package dal;

import dal.DBContext;
import model.TokenForgetPassword;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;
import java.util.logging.Level;

public class DAOTokenForget extends DBContext { // Inheriting DBContext to get connection

    private static final Logger LOGGER = Logger.getLogger(DAOTokenForget.class.getName());

    public String getFormatDate(LocalDateTime myDateObj) {
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return myDateObj.format(myFormatObj);
    }

    // Method to insert tokenForgetPassword into the database
    public boolean insertTokenForget(TokenForgetPassword tokenForget) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();  // Ensure this method returns a valid connection
            if (connection == null || connection.isClosed()) {
                throw new SQLException("Connection is not available");
            }

            String sql = "INSERT INTO tokenForgetPassword (token, expiryTime, isUsed, userId)\n" +
"VALUES(?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, tokenForget.getToken());
            statement.setObject(2, tokenForget.getExpiryTime());  // Set expiryTime as Object
            statement.setBoolean(3, tokenForget.getIsUsed());
            statement.setInt(4, tokenForget.getUserId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;  // Return true if rows were affected
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error inserting tokenForgetPassword", e);
            return false;
        } finally {
            // Close resources safely
            try {
                if (statement != null) statement.close();
                if (connection != null && !connection.isClosed()) connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error closing resources", e);
            }
        }
    }

  public TokenForgetPassword getTokenPassword(String token) {
    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
        connection = getConnection();  // Ensure this method returns a valid connection
        if (connection == null || connection.isClosed()) {
            throw new SQLException("Connection is not available");
        }

        String sql = "SELECT id, token, expiryTime, isUsed, userId FROM tokenForgetPassword WHERE token = ?";
        ps = connection.prepareStatement(sql);
        ps.setString(1, token);
        
        rs = ps.executeQuery();
        
        if (rs.next()) {
            return new TokenForgetPassword(
                rs.getInt("id"),
                rs.getInt("userId"),
                rs.getBoolean("isUsed"),
                rs.getString("token"),
                rs.getTimestamp("expiryTime").toLocalDateTime()
            );
        }
    } catch (SQLException e) {
        LOGGER.log(Level.SEVERE, "Error fetching tokenForgetPassword", e);
    } finally {
        // Close resources safely
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (connection != null && !connection.isClosed()) connection.close();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error closing resources", e);
        }
    }
    return null;
}


    // Method to update the 'isUsed' status of a token
    public void updateStatus(TokenForgetPassword token) {
        String sql = "UPDATE tokenForgetPassword SET isUsed = ? WHERE token = ?";
        try (Connection connection = getConnection();
             PreparedStatement st = connection.prepareStatement(sql)) {
            st.setBoolean(1, token.isIsUsed());
            st.setString(2, token.getToken());
            st.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating tokenForgetPassword", e);
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        DAOTokenForget dao = new DAOTokenForget();

        // Test database connection
        if (DBContext.getConnection() == null) {
            System.out.println("❌ Kết nối database thất bại!");
            return;
        } else {
            System.out.println("✅ Kết nối database thành công!");
        }

        // Create a test token
        TokenForgetPassword newToken = new TokenForgetPassword();
        newToken.setToken("c2ed5baa-031a-495e-96b5-43ba29b48cb2");
        newToken.setExpiryTime(LocalDateTime.parse("2025-03-01T04:15:00.048186500"));
        newToken.setIsUsed(false);
        newToken.setUserId(5);  // Make sure this ID exists in your 'users' table

        try {
            // Debug information before inserting
            System.out.println("========== 🛠 DEBUG INFO ==========");
            System.out.println("🆔 Token: " + newToken.getToken());
            System.out.println("🕒 Expiry Time: " + newToken.getExpiryTime());
            System.out.println("🔄 isUsed: " + newToken.isIsUsed());
            System.out.println("👤 userId: " + newToken.getUserId());
            System.out.println("===================================");
            

            // Check connection before insert
            if (dao.getConnection() == null) {
                System.out.println("🚨 Database connection is null!");
                return;
            } else {
                System.out.println("✅ Database connection is active!");
            }

            // Insert token
            boolean isInserted = dao.insertTokenForget(newToken);
            if (isInserted) {
                System.out.println("✅ Chèn token thành công!");
            } else {
                System.out.println("❌ Chèn token thất bại!");
            }
        } catch (Exception e) {
            // Print full stack trace if any error occurs
            System.out.println("🚨 Lỗi khi chèn token:");
            e.printStackTrace();
        }
    }
}
