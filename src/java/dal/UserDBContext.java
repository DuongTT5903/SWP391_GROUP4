package dal;

import model.User;
import model.Role;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yugio
 */
public class UserDBContext {

    /**
     *
     * @param pass
     * @param Username
     */
    public void changePassword(String pass, String Username) {
        String sql = "UPDATE Users SET Password = ? WHERE Username = ?";
        try {
            int result = 0;
            Connection conn = new DBContext().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);

            st.setString(1, pass);
            st.setString(2, Username);
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     *
     * @param username
     * @return
     */
    public String getPasswordByUsername(String username) {
        String password = null;
        String sql = "SELECT password FROM users WHERE username = ?";

        try {
            Connection conn = new DBContext().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, username);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                password = rs.getString("password");
            }

            rs.close();
            st.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return password;
    }

    /**
     *
     * @param username
     * @return
     * @throws SQLException
     */
    public User checkAccountExisted(String username) throws SQLException {
        String sql = "SELECT * FROM Users WHERE Username = ?";
        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Tạo đối tượng User mới và thiết lập giá trị cho các thuộc tính
                    User user = new User();
                    user.setUserID(rs.getInt("UserID"));
                    user.setName(rs.getString("Name"));
                    user.setGender(rs.getBoolean("Gender"));
                    user.setEmail(rs.getString("Email"));
                    user.setUsername(rs.getString("Username"));
                    user.setPassword(rs.getString("Password"));
                    user.setPhone(rs.getString("Phone"));

                    return user;
                }
            }
        } catch (SQLException ex) {
            // Xử lý ngoại lệ hoặc ghi log chi tiết
            System.out.println("Lỗi khi kiểm tra tài khoản tồn tại: " + ex.getMessage());
            throw ex;
        }
        return null;
    }

    /**
     *
     * @param email
     * @return
     * @throws Exception
     */
    public User checkEmailExisted(String email) throws Exception {
        try {
            // Mở kết nối
            Connection conn = new DBContext().getConnection();
            String sql = "SELECT * FROM Users WHERE Email = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Tạo đối tượng User mới và thiết lập giá trị cho các thuộc tính
                User user = new User();
                user.setUserID(rs.getInt("UserID"));
                user.setName(rs.getString("Name"));
                user.setGender(rs.getBoolean("Gender"));
                user.setEmail(rs.getString("Email"));
                user.setUsername(rs.getString("Username"));
                user.setPassword(rs.getString("Password"));
                user.setPhone(rs.getString("Phone"));

                return user;
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi khi kiểm tra email tồn tại: " + ex.getMessage());
            // Bạn có thể ghi log lỗi chi tiết hơn hoặc ném ngoại lệ để xử lý ở cấp cao hơn
            throw ex;
        }
        return null;
    }

    /**
     *
     * @param name
     * @param gender
     * @param email
     * @param user
     * @param pass
     * @param phone
     */
    public void signup(String name, String gender, String email, String user, String pass, String phone) {
        try {
            // Connect to MySQL database
            Connection conn = new DBContext().getConnection();
            String sql = "INSERT INTO Users\n"
                    + "           (Name, Gender, Email, Username, Password, Phone, RoleID)\n"
                    + "     VALUES\n"
                    + "           (?, ?, ?, ?, ?, ?, 4)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, gender);
            ps.setString(3, email);
            ps.setString(4, user);
            ps.setString(5, pass);
            ps.setString(6, phone);
            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    private static Connection connection = DBContext.getConnection();

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public User getUserByUsername(String username, String password) {
        User user = null;
        String query = "SELECT userID, name, gender, email, username, phone, roleID, imageURL "
                + "FROM users WHERE BINARY username = ? AND BINARY password = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getInt("userID"),
                        rs.getString("name"),
                        rs.getBoolean("gender"),
                        rs.getString("email"),
                        rs.getString("username"),
                        null, // Không lưu password vào đối tượng User
                        rs.getString("phone"),
                        rs.getString("roleID"),
                        rs.getString("imageURL")
                );
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, "Error fetching user", e);
        }

        return user;
    }

    /**
     *
     * @return
     */
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.userID, u.name, u.gender, u.email, u.username, u.phone, r.roleName, u.imageURL "
                + "FROM Users u INNER JOIN Roles r ON u.roleID = r.roleID";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                        rs.getInt("userID"),
                        rs.getString("name"),
                        rs.getBoolean("gender"),
                        rs.getString("email"),
                        rs.getString("username"),
                        null, // Không lưu mật khẩu
                        rs.getString("phone"),
                        rs.getString("roleName"),
                        rs.getString("imageURL")
                );
                users.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, "Error fetching users", ex);
        }
        return users;
    }

    /**
     *
     * @param userID
     * @return
     */
    public User getUserByID(int userID) {
        User user = null;
        String sql = "SELECT u.userID, u.name, u.gender, u.email, u.username, u.phone, r.roleName, u.imageURL "
                + "FROM Users u INNER JOIN Roles r ON u.roleID = r.roleID WHERE u.userID = ?";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, userID);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                            userID,
                            rs.getString("name"),
                            rs.getBoolean("gender"),
                            rs.getString("email"),
                            rs.getString("username"),
                            null,
                            rs.getString("phone"),
                            rs.getString("roleName"),
                            rs.getString("imageURL")
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, "Error fetching user by ID", ex);
        }
        return user;
    }

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public String getRoleIDByUsernameAndPassword(String username, String password) {
        String roleID = null;
        String query = "SELECT roleID FROM users WHERE BINARY username = ? AND BINARY password = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                roleID = rs.getString("roleID");
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, "Error fetching roleID", e);
        }
        return roleID;
    }

    /**
     *
     * @param roleID
     * @return
     */
    public Role getRole(int roleID) {
        Role role = null;
        String sql = "SELECT roleID, roleName FROM Roles WHERE roleID = ?";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, roleID);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    role = new Role(roleID, rs.getString("roleName"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, "Error fetching role by ID", ex);
        }
        return role;
    }

    /**
     *
     * @return
     */
    public List<Role> getRoles() {
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT roleID, roleName FROM Roles";

        try (PreparedStatement stm = connection.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                roles.add(new Role(rs.getInt("roleID"), rs.getString("roleName")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, "Error fetching roles", ex);
        }
        return roles;
    }

    /**
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<User> getUsers(int pageIndex, int pageSize) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.userID, u.name, u.gender, u.email, u.username, u.phone, r.roleName, u.imageURL "
                + "FROM Users u INNER JOIN Roles r ON u.roleID = r.roleID "
                + "LIMIT ? OFFSET ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setInt(1, pageSize);
            stm.setInt(2, (pageIndex - 1) * pageSize);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    User user = new User(
                            rs.getInt("userID"),
                            rs.getString("name"),
                            rs.getBoolean("gender"),
                            rs.getString("email"),
                            rs.getString("username"),
                            null, // Không lưu mật khẩu
                            rs.getString("phone"),
                            rs.getString("roleName"),
                            rs.getString("imageURL")
                    );
                    users.add(user);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, "Error fetching users with pagination", ex);
        }
        return users;
    }

    /**
     *
     * @return
     */
    public int getTotalUserCount() {
        String sql = "SELECT COUNT(*) AS total FROM Users";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, "Error fetching total user count", ex);
        }
        return 0;
    }

    /**
     *
     * @param user
     */
    public void addUser(User user) {
        String sql = "INSERT INTO Users (name, gender, email, username, password, phone, roleID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, user.getName());
            stm.setBoolean(2, user.isGender());
            stm.setString(3, user.getEmail());
            stm.setString(4, user.getUsername());
            stm.setString(5, user.getPassword());
            stm.setString(6, user.getPhone());
            stm.setInt(7, Integer.parseInt(user.getRole())); // Chuyển đổi roleID thành số nguyên

            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, "Error adding new user", ex);
        }
    }

    /**
     *
     * @param userID
     * @return
     */
    public User getUserByIDUserDetail(int userID) {
        User user = null;
        String sql = "SELECT u.userID, u.name, u.gender, u.email, u.username, u.password, u.phone, r.roleName "
                + "FROM Users u INNER JOIN Roles r ON u.roleID = r.roleID WHERE u.userID = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, userID);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                            userID,
                            rs.getString("name"),
                            rs.getBoolean("gender"),
                            rs.getString("email"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("phone"),
                            rs.getString("roleName"),
                            null
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, "Error fetching user by ID", ex);
        }
        return user;
    }

    /**
     *
     * @param user
     */
    public void updateUser(User user) {
        String sql = "UPDATE Users SET name = ?, gender = ?, email = ?, phone = ?, roleID = (SELECT roleID FROM Roles WHERE roleName = ?) "
                + "WHERE userID = ?";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, user.getName());
            stm.setBoolean(2, user.isGender());
            stm.setString(3, user.getEmail());
            stm.setString(4, user.getPhone());
            stm.setString(5, user.getRole());
            stm.setInt(6, user.getUserID());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, "Error updating user", ex);
        }
    }

    /**
     *
     * @param user
     */
    public void updateUserDetail(User user) {
        String sql = "UPDATE Users SET name = ?, gender = ?, email = ?, username = ?, password = ?, phone = ?, roleID = (SELECT roleID FROM Roles WHERE roleName = ?) "
                + "WHERE userID = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, user.getName());
            stm.setBoolean(2, user.isGender());
            stm.setString(3, user.getEmail());
            stm.setString(4, user.getUsername());
            stm.setString(5, user.getPassword());
            stm.setString(6, user.getPhone());
            stm.setString(7, user.getRole());
            stm.setInt(8, user.getUserID());

            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, "Error updating user", ex);
        }
    }

    /**
     *
     * @param updateU
     */
    public void updateUser1(User updateU) {
        String sql = "UPDATE Users SET name = ?, username = ?, gender = ?, email = ?, phone = ? WHERE userID = ?";

        try (Connection conn = new DBContext().getConnection(); // Lấy kết nối mới
                 PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setString(1, updateU.getName());
            stm.setString(2, updateU.getUsername());
            stm.setBoolean(3, updateU.isGender());
            stm.setString(4, updateU.getEmail());
            stm.setString(5, updateU.getPhone());
            stm.setInt(6, updateU.getUserID());

            int rowsUpdated = stm.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("No user was updated. Check if the userID exists.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, "Error updating user", ex);
            throw new RuntimeException("Database update failed", ex);
        }
    }

    /**
     *
     * @param username
     * @param email
     * @return
     */
    public boolean isUserExists(String username, String email) {
    try (Connection conn = DBContext.getConnection()) {
        String sql = "SELECT COUNT(*) FROM Users WHERE username = ? OR email = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.setString(2, email);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0; // Nếu số lượng > 0 thì user/email đã tồn tại
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}


}
