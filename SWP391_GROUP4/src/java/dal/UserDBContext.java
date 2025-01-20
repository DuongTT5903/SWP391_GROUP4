package dal;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import model.Role;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

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

public void signUp(User c) {
    try {
        String sql = """
                     INSERT INTO Customers 
                     (fullname, email, phonenumber, username, password, role) 
                     VALUES (?, ?, ?, ?, ?, ?)
                     """;

        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, c.getName());
        st.setString(2, c.getEmail());
        st.setString(3, c.getPhone());
        st.setString(4, c.getUsername());
        st.setString(5, hashString(c.getPassword()));  // Mã hóa mật khẩu nếu cần
        st.setString(6, c.getRole());
        st.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();  // In lỗi ra console để dễ dàng debug
    }
}

    public void updateCustomer(User c, String fullname, String email, String phonename) {
    try {
        String sql = "UPDATE Customers "
                   + "SET fullname = ?, "
                   + "    email = ?, "
                   + "    phonenumber = ? "
                   + "WHERE id = ?";
        
        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, fullname);
        st.setString(2, email);
        st.setString(3, phonename);
        st.setInt(4, c.getUserID());
        st.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();  // Hiển thị lỗi để dễ dàng tìm nguyên nhân
    }
}

    public ArrayList<User> getCustomers() {
    ArrayList<User> list = new ArrayList<>();
    try {
        String sql = """
                     SELECT id, 
                            fullname, 
                            email, 
                            phonenumber, 
                            username, 
                            password, 
                            role 
                     FROM Customers
                     """;

        PreparedStatement st = connection.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        
        while (rs.next()) {
            User c = new User();
            c.setUserID(rs.getInt("id"));  // Gán ID nếu User có setUserID
            c.setName(rs.getString("fullname"));
            c.setEmail(rs.getString("email"));
            c.setPhone(rs.getString("phonenumber"));
            c.setUsername(rs.getString("username"));
            c.setPassword(rs.getString("password"));
            c.setRole(rs.getString("role"));
            list.add(c);
        }
    } catch (SQLException e) {
        e.printStackTrace();  // Hiển thị lỗi để hỗ trợ debug
    }
    return list;
}


    public User login(String user, String pass) {
    User c = new User();  // Khởi tạo là null thay vì một đối tượng rỗng
    try {
        String sql = """
                    SELECT UserID, Name, email, Phone, RoleID
                                          FROM Users
                                          WHERE username = ? AND password = ?  
                     """;

        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, user);
        st.setString(2, pass);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            c = new User();
            c.setUserID(rs.getInt("id"));
            c.setName(rs.getString("fullname"));
            c.setEmail(rs.getString("email"));
            c.setPhone(rs.getString("phonenumber"));
            c.setUsername(rs.getString("username"));
            c.setPassword(rs.getString("password"));
            c.setRole(rs.getString("role"));
        }
    } catch (SQLException e) {
        e.printStackTrace();  // Hiển thị lỗi để dễ dàng tìm nguyên nhân
    }
    return null;
}


   public void changePassword(int id, String pass) {
    try {
        String sql = """
                     UPDATE Customers
                     SET password = ?
                     WHERE id = ?
                     """;

        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, hashString(pass));  // Mã hóa mật khẩu trước khi lưu
        st.setInt(2, id);
        st.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();  // Hiển thị lỗi để hỗ trợ gỡ lỗi
    }
}

    public User checkCustomer(String email, String Phone, String username) {
    User c = new User(); // Khởi tạo là null thay vì một đối tượng mới
    try {
        String sql = """
                     SELECT id, fullname, email, phonenumber, username, password, role
                     FROM Customers
                     WHERE email = ? AND phonenumber = ? AND username = ?
                     """;

        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, email);
        st.setString(2, Phone);
        st.setString(3, username);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            c = new User();
            c.setUserID(rs.getInt("id"));
            c.setName(rs.getString("fullname"));
            c.setEmail(rs.getString("email"));
            c.setPhone(rs.getString("phonenumber"));
            c.setUsername(rs.getString("username"));
            c.setPassword(rs.getString("password"));
            c.setRole(rs.getString("role"));
        }
    } catch (SQLException e) {
        e.printStackTrace();  // In lỗi ra console để hỗ trợ debug
    }
    return c;
}


    public String hashString(String input) {
        try {
            // Tạo đối tượng MessageDigest với thuật toán SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Băm chuỗi đầu vào
            byte[] hash = digest.digest(input.getBytes());

            // Chuyển đổi mảng byte thành chuỗi hex
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
