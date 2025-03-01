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

    /**
     *
     * @return
     */
    public List<Blog> getAllBlogs() {
        List<Blog> blogs = new ArrayList<>();
        String sql = "SELECT b.BlogID, b.BlogTitle, b.BlogDetail, b.Category, b.status, b.imglink, u.UserID, u.Name "
                + "FROM Blogs b INNER JOIN Users u ON b.AuthorID = u.UserID WHERE b.status = 1";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

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
//    public List<Blog> getAllPosts() {
//        List<Blog> posts = new ArrayList<>();
//        String sql = "SELECT b.BlogID, b.BlogTitle, b.BlogDetail, b.Category, b.status, b.imglink, u.UserID, u.Name "
//                + "FROM Blogs b INNER JOIN Users u ON b.AuthorID = u.UserID";
//        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {
//           
//            while (rs.next()) {
//                User author = new User(rs.getInt("UserID"), rs.getString("Name"));
//                Blog post = new Blog(
//                        rs.getInt("BlogID"),
//                        rs.getString("BlogTitle"),
//                        rs.getString("BlogDetail"),
//                        rs.getString("Category"),
//                        rs.getBoolean("status"),
//                        rs.getString("imglink"),
//                         author
//                );
//                
//                
//                posts.add(post);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return posts;
//    }

    /**
     *
     * @param blogID
     * @return
     */
//    public Blog getBlogById(int blogID) {
//        String sql = "SELECT b.BlogID, b.BlogTitle, b.BlogDetail, b.Category, b.status, b.imglink, u.UserID, u.Name "
//                + "FROM Blogs b INNER JOIN Users u ON b.AuthorID = u.UserID WHERE BlogID=?";
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setInt(1, blogID);
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    return new Blog(
//                            rs.getInt("BlogID"),
//                            rs.getString("BlogTitle"),
//                            rs.getString("BlogDetail"),
//                            rs.getString("Category"),
//                            rs.getBoolean("status"),
//                            rs.getString("imglink"),
//                            new User(rs.getInt("AuthorID"))
//                    );
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    public Blog getBlogById(int blogID) {
        String sql = "SELECT b.BlogID, b.BlogTitle, b.BlogDetail, b.Category, b.status, b.imglink, u.UserID, u.Name "
                   + "FROM Blogs b INNER JOIN Users u ON b.AuthorID = u.UserID WHERE b.BlogID = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, blogID);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    User author = new User(rs.getInt("UserID"), rs.getString("Name"));
                    return new Blog(
                            rs.getInt("BlogID"),
                            rs.getString("BlogTitle"),
                            rs.getString("BlogDetail"),
                            rs.getString("Category"),
                            rs.getBoolean("status"),
                            rs.getString("imglink"),
                            author
                    );
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public List<Blog> getBlogByBlogID(int blogID) {
    List<Blog> blogs = new ArrayList<>();
    String sql = "SELECT b.BlogID, b.BlogTitle, b.BlogDetail, b.Category, b.status, b.imglink, u.UserID, u.Name " +
                 "FROM Blogs b INNER JOIN Users u ON b.AuthorID = u.UserID WHERE b.BlogID = ?";

    try (Connection conn = DBContext.getConnection();
         PreparedStatement stm = conn.prepareStatement(sql)) {
        
        // Thiết lập tham số cho truy vấn
        stm.setInt(1, blogID);
        
        try (ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                Blog blog = new Blog(
                        rs.getInt("BlogID"),
                        rs.getString("BlogTitle"),
                        rs.getString("BlogDetail"),
                        rs.getString("Category"),
                        rs.getBoolean("status"),
                        rs.getString("imglink")
                );
                User user = new User(
                        rs.getInt("UserID"),
                        rs.getString("Name")
                );
                blog.setAuthor(user); // Setting the user to the blog
                blogs.add(blog);
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(BlogDBContext.class.getName()).log(Level.SEVERE, "Error fetching blogs", ex);
    }
    return blogs;
}
}
