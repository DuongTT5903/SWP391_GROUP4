package dal;

import model.Role;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDBContext {

    // Using the static connection from DBContext
    private static Connection connection = DBContext.getConnection();

    public List<Role> getRoles() {
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<Role> roles = new ArrayList<>();
        
        try {
            String sql = "SELECT RoleID, RoleName FROM roles";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            
            while (rs.next()) {
                int roleID = rs.getInt("RoleID");
                String roleName = rs.getString("RoleName");
                Role role = new Role(roleID, roleName);
                roles.add(role);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, "Error fetching roles", ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, "Error closing resources", ex);
            }
        }

        return roles;
    }
}
