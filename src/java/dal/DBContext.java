package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yugio
 */
public class DBContext {

    /**
     *
     */
    protected static Connection connection;

    // Ensure that the method is static and public

    /**
     *
     * @return
     */
    public static Connection getConnection() {
        try {
            // Check if the connection is null or closed
            if (connection == null || connection.isClosed()) {
                String user = "root"; // Database username
                String pass = "123456"; // Database password
                String url = "jdbc:mysql://localhost:3306/childrencare?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";

                // Load MySQL JDBC driver (MySQL 8.x)
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, user, pass);
                System.out.println("Connection successful!");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, "MySQL Driver not found.", ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, "Database connection error.", ex);
        }
        return connection;
    }
}
