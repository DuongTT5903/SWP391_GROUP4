package controller;

import dal.BlogDBContext;
import model.Blog;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomepageController", urlPatterns = {"/home"})
public class HomepageController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BlogDBContext blogDB = new BlogDBContext();
        List<Blog> blogs = null;

        try {
            blogs = blogDB.getAllBlogs();
            if (blogs == null || blogs.isEmpty()) {
                request.setAttribute("errorMessage", "No blogs available at the moment.");
            } else {
                request.setAttribute("blogs", blogs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while fetching blogs.");
        }

        request.getRequestDispatcher("view/homepage.jsp").forward(request, response);
    }
}
