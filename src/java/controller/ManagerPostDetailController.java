package controller;

import dal.PostDBContext;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Post;

public class ManagerPostDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            // Nếu không có id, chuyển hướng về danh sách bài viết
            response.sendRedirect(request.getContextPath() + "/manager/postList");
            return;
        }
        int postId = Integer.parseInt(idParam);
        PostDBContext postDAO = new PostDBContext();
        List<Post> posts = postDAO.getPostByPostID(postId);
        if (posts != null && !posts.isEmpty()) {
            Post post = posts.get(0);
            request.setAttribute("post", post);
            request.getRequestDispatcher("postDetail.jsp").forward(request, response);
        } else {
            // Nếu không tìm thấy bài viết, chuyển hướng về danh sách
            response.sendRedirect(request.getContextPath() + "/manager/postList");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        // Lấy các tham số từ form
        int postId = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String detail = request.getParameter("detail");
        String category = request.getParameter("category");
        String imageLink = request.getParameter("imageLink");
        // Checkbox: nếu tồn tại thì giá trị là true
        boolean status = request.getParameter("status") != null;
        
        Post post = new Post(postId, title, detail, category, status, imageLink);
        PostDBContext postDAO = new PostDBContext();
        postDAO.updatePost(post);
        // Sau khi cập nhật, chuyển hướng về danh sách bài viết
        response.sendRedirect(request.getContextPath() + "/manager/postList");
    }

    @Override
    public String getServletInfo() {
        return "Controller for managing post details";
    }
}
