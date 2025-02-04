/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.User;
import model.Role;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDBContext {

    private static Connection connection = DBContext.getConnection();

    public List<User> getUsers() {
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        
        try {
            String sql = "SELECT u.UserID, u.Name, u.Gender, u.Email, u.Username, u.Password, u.Phone, r.RoleName, u.ImageURL " +
                         "FROM Users u " +
                         "INNER JOIN Roles r ON u.RoleID = r.RoleID";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            
            while (rs.next()) {
                int userID = rs.getInt("UserID");
                String name = rs.getString("Name");
                boolean gender = rs.getBoolean("Gender");
                String email = rs.getString("Email");
                String username = rs.getString("Username");
                String password = rs.getString("Password");
                String phone = rs.getString("Phone");
                String roleName = rs.getString("RoleName");
                String imageURL = rs.getString("ImageURL");
                
                User user = new User(userID, name, gender, email, username, password, phone, roleName, imageURL);
                users.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, "Error fetching users", ex);
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

        return users;
    }
    public User getUserByID(int userID) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        User user = null;

        try {
            String sql = "SELECT u.UserID, u.Name, u.Gender, u.Email, u.Username, u.Password, u.Phone, r.RoleName, u.ImageURL " +
                         "FROM Users u " +
                         "INNER JOIN Roles r ON u.RoleID = r.RoleID " +
                         "WHERE u.UserID = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, userID);
            rs = stm.executeQuery();

            if (rs.next()) {
                String name = rs.getString("Name");
                boolean gender = rs.getBoolean("Gender");
                String email = rs.getString("Email");
                String username = rs.getString("Username");
                String password = rs.getString("Password");
                String phone = rs.getString("Phone");
                String roleName = rs.getString("RoleName");
                String imageURL = rs.getString("ImageURL");

                user = new User(userID, name, gender, email, username, password, phone, roleName, imageURL);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, "Error fetching user by ID", ex);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, "Error closing resources", ex);
            }
        }

        return user;
    }
    public void updateUser(User user) {
        PreparedStatement stm = null;

        try {
            String sql = "UPDATE Users SET Name = ?, Gender = ?, Email = ?, Phone = ?, RoleID = (SELECT RoleID FROM Roles WHERE RoleName = ?) " +
                         "WHERE UserID = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, user.getName());
            stm.setBoolean(2, user.isGender());
            stm.setString(3, user.getEmail());
            stm.setString(4, user.getPhone());
            stm.setString(5, user.getRole());
            stm.setInt(6, user.getUserID());

            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, "Error updating user", ex);
        } finally {
            try {
                if (stm != null) stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, "Error closing resources", ex);
            }
        }
    }
        public Role getRole(int roleID) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        Role role = null;

        try {
            String sql = "SELECT RoleID, RoleName FROM Roles WHERE RoleID = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, roleID);
            rs = stm.executeQuery();

            if (rs.next()) {
                String roleName = rs.getString("RoleName");
                role = new Role(roleID, roleName);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, "Error fetching role by ID", ex);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, "Error closing resources", ex);
            }
        }

        return role;
    }
        public List<Role> getRoles() {
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<Role> roles = new ArrayList<>();
        
        try {
            String sql = "SELECT RoleID, RoleName FROM Roles";
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
                if (rs != null) rs.close();
                if (stm != null) stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, "Error closing resources", ex);
            }
        }

        return roles;
    }
}