package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
            sql += " AND p.BlogTitle LIKE ?";
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
}
