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
import model.Blog;
import model.User;

/**
 *
 * @author admin
 */
public class BlogDBContext {
    
    private static Connection connection = DBContext.getConnection();

    public List<Blog> getAllBlogs() {
        List<Blog> blogs = new ArrayList<>();
        String sql = "SELECT b.BlogID, b.BlogTitle, b.BlogDetail, b.Category, b.status, b.imglink, u.UserID, u.Name " +
                     "FROM Blogs b INNER JOIN Users u ON b.AuthorID = u.UserID";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stm = conn.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                Blog blog = new Blog(
                    rs.getInt("BlogID"),
                    rs.getString("BlogTitle"),
                    rs.getString("BlogDetail"),
                    rs.getString("Category"),
                    rs.getBoolean("status"),
                    rs.getString("imglink")


                );
                blogs.add(blog);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDBContext.class.getName()).log(Level.SEVERE, "Error fetching blogs", ex);
        }
        return blogs;
    
}
}
