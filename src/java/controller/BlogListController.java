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



public class BlogListController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BlogDBContext blogDB = new BlogDBContext();
        List<Blog> blogs = blogDB.getAllBlogs();

        request.setAttribute("blogs", blogs);
        request.getRequestDispatcher("view/blogList.jsp").forward(request, response);
    }
}

