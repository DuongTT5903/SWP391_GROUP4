package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Blog;
import model.Post;
import model.User;

public class PostDBContext extends DBContext {

    public List<Post> getPosts(String filterCategory, String filterAuthor, String filterStatus, String searchTitle, String sortBy, int page, int pageSize) {
        List<Post> list = new ArrayList<>();
        String sql = "SELECT p.BlogID, p.BlogTitle, p.BlogDetail, p.Category, p.status, p.imglink, "
                + "u.userID, u.name FROM blogs p "
                + "INNER JOIN users u ON p.AuthorID = u.UserID WHERE 1=1";

        List<Object> params = new ArrayList<>();

        if (filterCategory != null && !filterCategory.isEmpty()) {
            sql += " AND p.Category = ?";
            params.add(filterCategory);
        }
        if (filterAuthor != null && !filterAuthor.isEmpty()) {
            sql += " AND u.name = ?";
            params.add(filterAuthor);
        }
        if (filterStatus != null && !filterStatus.isEmpty()) {
            sql += " AND p.status = ?";
            params.add(Boolean.parseBoolean(filterStatus));
        }
        if (searchTitle != null && !searchTitle.isEmpty()) {
            sql += " AND (p.BlogTitle LIKE ? OR p.BlogDetail LIKE ?)";
            params.add("%" + searchTitle + "%");
            params.add("%" + searchTitle + "%");
        }

        // Chỉ cho phép một số giá trị hợp lệ trong `sortBy` để tránh SQL Injection
        if ("BlogTitle".equals(sortBy) || "Category".equals(sortBy) || "status".equals(sortBy) || "name".equals(sortBy)) {
            sql += " ORDER BY " + sortBy;
        } else {
            sql += " ORDER BY p.BlogID"; // Mặc định sắp xếp theo ID
        }

        sql += " LIMIT ? OFFSET ?"; // MySQL dùng LIMIT trước OFFSET
        params.add(pageSize);
        params.add((page - 1) * pageSize);

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                if (params.get(i) instanceof Integer) {
                    stmt.setInt(i + 1, (Integer) params.get(i));
                } else if (params.get(i) instanceof Boolean) {
                    stmt.setBoolean(i + 1, (Boolean) params.get(i));
                } else {
                    stmt.setString(i + 1, params.get(i).toString());
                }
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User author = new User(rs.getInt("userID"), rs.getString("name"));
                Post post = new Post(
                        rs.getInt("BlogID"),
                        rs.getString("BlogTitle"),
                        rs.getString("BlogDetail"),
                        rs.getString("Category"),
                        rs.getBoolean("status"),
                        rs.getString("imglink"),
                        author
                );
                list.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT DISTINCT Category FROM blogs";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                categories.add(rs.getString("Category"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public List<String> getAuthors() {
        List<String> authors = new ArrayList<>();
        String sql = "SELECT DISTINCT u.name FROM blogs p INNER JOIN users u ON p.AuthorID = u.UserID";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                authors.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }

    public void updatePostStatus(int postId, boolean status) {
        String sql = "UPDATE blogs SET status = ? WHERE BlogID = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, status);
            stmt.setInt(2, postId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addPost(Post post) {
        String sql = "INSERT INTO blogs (BlogTitle, BlogDetail, Category, status, imglink, AuthorID) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, post.getTitle());
            stmt.setString(2, post.getDetail());
            stmt.setString(3, post.getCategory());
            stmt.setBoolean(4, post.isStatus());
            stmt.setString(5, post.getImageLink());
            stmt.setInt(6, post.getAuthor().getUserID());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu chèn thành công
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm bài viết: " + e.getMessage());
            return false; // Trả về false nếu có lỗi
        }
    }

    public int countPosts(String filterCategory, String filterAuthor, String filterStatus, String searchTitle) {
        int count = 0;
        String sql = "SELECT COUNT(*) AS total FROM blogs p INNER JOIN users u ON p.AuthorID = u.UserID WHERE 1=1";
        List<Object> params = new ArrayList<>();

        if (filterCategory != null && !filterCategory.isEmpty()) {
            sql += " AND p.Category = ?";
            params.add(filterCategory);
        }
        if (filterAuthor != null && !filterAuthor.isEmpty()) {
            sql += " AND u.name = ?";
            params.add(filterAuthor);
        }
        if ("true".equalsIgnoreCase(filterStatus)) {
            sql += " AND p.status = ?";
            params.add(true);
        } else if ("false".equalsIgnoreCase(filterStatus)) {
            sql += " AND p.status = ?";
            params.add(false);
        }
        if (searchTitle != null && !searchTitle.isEmpty()) {
            sql += " AND p.BlogTitle LIKE ?";
            params.add("%" + searchTitle + "%");
        }

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Đặt các tham số cho PreparedStatement
            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if (param instanceof Integer) {
                    stmt.setInt(i + 1, (Integer) param);
                } else if (param instanceof Boolean) {
                    stmt.setBoolean(i + 1, (Boolean) param);
                } else {
                    stmt.setString(i + 1, param.toString());
                }
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error counting posts", e);
        }
        return count;
    }

    public List<Post> getPostByPostID(int postID) {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT b.BlogID, b.BlogTitle, b.BlogDetail, b.Category, b.status, b.imglink, u.UserID, u.Name "
                + "FROM Blogs b INNER JOIN Users u ON b.AuthorID = u.UserID WHERE b.BlogID = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {

            // Thiết lập tham số cho truy vấn
            stm.setInt(1, postID);

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Post post = new Post(
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
                    post.setAuthor(user); // Setting the user to the blog
                    posts.add(post);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogDBContext.class.getName()).log(Level.SEVERE, "Error fetching blogs", ex);
        }
        return posts;
    }

    public void updatePost(Post post) {
        String sql = "UPDATE blogs SET BlogTitle = ?, BlogDetail = ?, Category = ?, status = ?, imglink = ? WHERE BlogID = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, post.getTitle());
            stmt.setString(2, post.getDetail());
            stmt.setString(3, post.getCategory());
            stmt.setBoolean(4, post.isStatus());
            stmt.setString(5, post.getImageLink());
            stmt.setInt(6, post.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating post", e);
        }
    }
}
