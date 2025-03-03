package controller;

import dal.BlogDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Blog;

@WebServlet(name = "BlogListController", urlPatterns = {"/BlogListController"})
public class BlogListController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BlogDBContext blogDB = new BlogDBContext();
        
        // Lấy tham số search từ request
        String searchQuery = request.getParameter("search");
        
        List<Blog> blogs;
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            // Nếu có search, tìm kiếm blog theo tên
            blogs = blogDB.searchBlogsByTitle(searchQuery);
        } else {
            // Nếu không có search, lấy tất cả blog
            blogs = blogDB.getAllBlogs();
        }

        request.setAttribute("blogs", blogs);
        request.setAttribute("search", searchQuery); // Giữ lại giá trị tìm kiếm trên giao diện
        request.getRequestDispatcher("view/blogList.jsp").forward(request, response);
    }
}
