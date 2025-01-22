package dal;

import model.Blog;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BlogDBContext {

    private static Connection connection = DBContext.getConnection();

    public List<Blog> getAllBlogs() {
        List<Blog> blogs = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            if (connection == null || connection.isClosed()) {
                System.out.println("Database connection is null or closed.");
            } else {
                System.out.println("Database connection is open.");
            }

            String sql = "SELECT b.BlogID, b.BlogTitle, b.BlogDetail, b.Category, b.status, b.imglink, u.UserID, u.Name " +
                         "FROM Blogs b INNER JOIN Users u ON b.AuthorID = u.UserID";
            stm = connection.prepareStatement(sql);
            System.out.println("Executing query: " + sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                int blogID = rs.getInt("BlogID");
                String blogTitle = rs.getString("BlogTitle");
                String blogDetail = rs.getString("BlogDetail");
                String category = rs.getString("Category");
                boolean status = rs.getBoolean("status");
                String imageLink = rs.getString("imglink");

                int authorID = rs.getInt("UserID");
                String authorName = rs.getString("Name");
                User author = new User();
                author.setUserID(authorID);
                author.setName(authorName);

                Blog blog = new Blog(blogID, blogTitle, blogDetail, category, status, imageLink, author);
                blogs.add(blog);
                System.out.println("Blog added: " + blogTitle);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDBContext.class.getName()).log(Level.SEVERE, "Error fetching blogs", ex);
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(BlogDBContext.class.getName()).log(Level.SEVERE, "Error closing resources", ex);
                ex.printStackTrace();
            }
        }

        return blogs;
    }
}
