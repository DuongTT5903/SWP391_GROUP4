package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Role;
import model.RoleStatus;

public class SettingDBContext {

    private static final Logger LOGGER = Logger.getLogger(SettingDBContext.class.getName());

    public List<Role> getRolesWithStatus() {
        PreparedStatement stm = null;
        ResultSet rs = null;
        Connection conn = null;
        List<Role> roles = new ArrayList<>();

        try {
            String sql = "SELECT roles.RoleID, roles.RoleName, RoleStatus.Status " +
                         "FROM roles " +
                         "JOIN RoleStatus ON roles.RoleID = RoleStatus.RoleID";
            conn = DBContext.getConnection();
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                int roleID = rs.getInt("RoleID");
                String roleName = rs.getString("RoleName");
                boolean statusValue = rs.getBoolean("Status");

                Role role = new Role(roleID, roleName);
                RoleStatus status = new RoleStatus(statusValue);
                role.setStatus(status);

                roles.add(role);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lấy danh sách roles", ex);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
                if (conn != null) conn.close(); // Đảm bảo đóng connection
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "Lỗi khi đóng tài nguyên", ex);
            }
        }

        return roles;
    }
    
      public List<Role> getRolesWithStatus1() {
        PreparedStatement stm = null;
        ResultSet rs = null;
        Connection conn = null;
        List<Role> roles = new ArrayList<>();

        try {
            String sql = "SELECT roles.RoleID, roles.RoleName, RoleStatus.Status " +
                         "FROM roles " +
                         "JOIN RoleStatus ON roles.RoleID = RoleStatus.RoleID where RoleStatus.status=1";
            conn = DBContext.getConnection();
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                int roleID = rs.getInt("RoleID");
                String roleName = rs.getString("RoleName");
                boolean statusValue = rs.getBoolean("Status");

                Role role = new Role(roleID, roleName);
                RoleStatus status = new RoleStatus(statusValue);
                role.setStatus(status);

                roles.add(role);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lấy danh sách roles", ex);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
                if (conn != null) conn.close(); // Đảm bảo đóng connection
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "Lỗi khi đóng tài nguyên", ex);
            }
        }

        return roles;
    }
    public void updateRoleStatus(int roleID, boolean status) {
    String sql = "INSERT INTO RoleStatus (RoleID, Status) VALUES (?, ?) "
               + "ON DUPLICATE KEY UPDATE Status = VALUES(Status)";

    try (Connection conn = DBContext.getConnection();
         PreparedStatement stm = conn.prepareStatement(sql)) {

        stm.setInt(1, roleID);
        stm.setBoolean(2, status);

        stm.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(SettingDBContext.class.getName()).log(Level.SEVERE, "Lỗi cập nhật trạng thái role", ex);
    }
}

    public static void main(String[] args) {
        SettingDBContext dbContext = new SettingDBContext();
        
        // Gọi phương thức getRolesWithStatus()
        List<Role> roles = dbContext.getRolesWithStatus();
        
        // Kiểm tra kết quả
        if (roles.isEmpty()) {
            System.out.println("Không có vai trò nào trong hệ thống!");
        } else {
            for (Role role : roles) {
                System.out.println("Role ID: " + role.getRoleID());
                System.out.println("Role Name: " + role.getRoleName());
                System.out.println("Role Status: " + (role.getStatus() != null ? role.getStatus().getStatus() : "Unknown"));
                System.out.println("----------------------");
            }
        }
}
}