package controller;

import dal.PostDBContext;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Post;

public class ManagerPostListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PostDBContext postDAO = new PostDBContext();
        // Lấy danh sách động từ DB
        List<String> categories = postDAO.getCategories();
        List<String> authors = postDAO.getAuthors();

        // Lấy các tham số lọc từ request
        String filterCategory = request.getParameter("category");
        String filterAuthor = request.getParameter("author");
        String filterStatus = request.getParameter("status");
        String searchTitle = request.getParameter("title");
        String sortBy = request.getParameter("sortBy");

        // Xử lý phân trang an toàn
        int page = 1;
        int pageSize = 5;
        try {
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }
        } catch (NumberFormatException e) {
            page = 1; // Nếu có lỗi, mặc định về trang 1
        }

        // Gọi DAO để lấy danh sách bài viết
        List<Post> posts = postDAO.getPosts(filterCategory, filterAuthor, filterStatus, searchTitle, sortBy, page, pageSize);

        // Đưa dữ liệu lên JSP
        request.setAttribute("categories", categories);
        request.setAttribute("authors", authors);
        request.setAttribute("posts", posts);
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", pageSize);
        request.getRequestDispatcher("postList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Hiện tại chưa có logic xử lý POST, có thể thêm chức năng thêm bài viết sau này
        response.sendRedirect("postList.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Controller for managing posts list";
    }
}
