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

public class BlogDetailController extends HttpServlet {

    private final BlogDBContext blogDB = new BlogDBContext();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String blogIDParam = request.getParameter("blogID");

        if (blogIDParam == null || blogIDParam.trim().isEmpty()) {
            request.setAttribute("error", "BlogID không được để trống!");
        } else {
            try {
                int blogID = Integer.parseInt(blogIDParam);
                List<Blog> blogs = blogDB.getBlogByBlogID(blogID);

                if (blogs == null || blogs.isEmpty()) {
                    request.setAttribute("error", "Không tìm thấy blog!");
                } else {
                    request.setAttribute("blogs", blogs);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "BlogID không hợp lệ!");
            }
        }

        request.getRequestDispatcher("view/viewBlog.jsp").forward(request, response);
    }
}
