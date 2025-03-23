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

    /**
     * Lấy danh sách tất cả roles cùng trạng thái
     */
    public List<Role> getRolesWithStatus() {
        PreparedStatement stm = null;
        ResultSet rs = null;
        Connection conn = null;
        List<Role> roles = new ArrayList<>();

        try {
            String sql = "SELECT roles.RoleID, roles.RoleName, RoleStatus.Status " +
                         "FROM roles " +
                         "LEFT JOIN RoleStatus ON roles.RoleID = RoleStatus.RoleID";
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
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "Lỗi khi đóng tài nguyên", ex);
            }
        }

        return roles;
    }

    /**
     * Lấy danh sách roles có trạng thái = 1 (Hoạt động)
     */
    public List<Role> getRolesWithStatus1() {
        PreparedStatement stm = null;
        ResultSet rs = null;
        Connection conn = null;
        List<Role> roles = new ArrayList<>();

        try {
            String sql = "SELECT roles.RoleID, roles.RoleName, RoleStatus.Status " +
                         "FROM roles " +
                         "JOIN RoleStatus ON roles.RoleID = RoleStatus.RoleID " +
                         "WHERE RoleStatus.Status = 1";
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
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "Lỗi khi đóng tài nguyên", ex);
            }
        }

        return roles;
    }

    /**
     * Cập nhật trạng thái của role
     */
    public void updateRoleStatus(int roleID, boolean status) {
        String sql = "INSERT INTO RoleStatus (RoleID, Status) VALUES (?, ?) " +
                     "ON DUPLICATE KEY UPDATE Status = VALUES(Status)";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setInt(1, roleID);
            stm.setBoolean(2, status);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SettingDBContext.class.getName()).log(Level.SEVERE, "Lỗi cập nhật trạng thái role", ex);
        }
    }

    /**
     * Lấy thông tin role theo ID
     */
    public Role getRoleById(int roleID) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        Connection conn = null;
        Role role = null;

        try {
            String sql = "SELECT roles.RoleID, roles.RoleName, RoleStatus.Status " +
                         "FROM roles " +
                         "LEFT JOIN RoleStatus ON roles.RoleID = RoleStatus.RoleID " +
                         "WHERE roles.RoleID = ?";
            conn = DBContext.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setInt(1, roleID);
            rs = stm.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("RoleID");
                String roleName = rs.getString("RoleName");
                boolean statusValue = rs.getBoolean("Status");

                role = new Role(id, roleName);
                RoleStatus status = new RoleStatus(statusValue);
                role.setStatus(status);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lấy role theo ID", ex);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "Lỗi khi đóng tài nguyên", ex);
            }
        }

        return role;
    }

    /**
     * Thêm role mới
     */
    public void addRole(Role role) {
        Connection conn = null;
        PreparedStatement stmRole = null;
        PreparedStatement stmStatus = null;

        try {
            conn = DBContext.getConnection();
            conn.setAutoCommit(false); // Bắt đầu transaction

            // Thêm role vào bảng roles
            String sqlRole = "INSERT INTO roles (RoleName) VALUES (?)";
            stmRole = conn.prepareStatement(sqlRole, PreparedStatement.RETURN_GENERATED_KEYS);
            stmRole.setString(1, role.getRoleName());
            stmRole.executeUpdate();

            // Lấy RoleID vừa được tạo
            ResultSet generatedKeys = stmRole.getGeneratedKeys();
            int roleID;
            if (generatedKeys.next()) {
                roleID = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Không thể lấy RoleID sau khi thêm role.");
            }

            // Thêm trạng thái mặc định (status = true) vào bảng RoleStatus
            String sqlStatus = "INSERT INTO RoleStatus (RoleID, Status) VALUES (?, ?)";
            stmStatus = conn.prepareStatement(sqlStatus);
            stmStatus.setInt(1, roleID);
            stmStatus.setBoolean(2, true); // Mặc định trạng thái là true (Hoạt động)
            stmStatus.executeUpdate();

            conn.commit(); // Commit transaction
        } catch (SQLException ex) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback nếu có lỗi
                } catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, "Lỗi khi rollback", e);
                }
            }
            LOGGER.log(Level.SEVERE, "Lỗi khi thêm role", ex);
        } finally {
            try {
                if (stmRole != null) stmRole.close();
                if (stmStatus != null) stmStatus.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "Lỗi khi đóng tài nguyên", ex);
            }
        }
    }

    /**
     * Cập nhật role
     */
    public void updateRole(Role role) {
        PreparedStatement stm = null;
        Connection conn = null;

        try {
            String sql = "UPDATE roles SET RoleName = ? WHERE RoleID = ?";
            conn = DBContext.getConnection();
            stm = conn.prepareStatement(sql);
            stm.setString(1, role.getRoleName());
            stm.setInt(2, role.getRoleID());
            stm.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Lỗi khi cập nhật role", ex);
        } finally {
            try {
                if (stm != null) stm.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "Lỗi khi đóng tài nguyên", ex);
            }
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