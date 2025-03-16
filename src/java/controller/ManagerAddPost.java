/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.PostDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import model.Post;
import model.User;

/**
 *
 * @author yugio
 */
public class ManagerAddPost extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ManagerAddPost</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManagerAddPost at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Thiết lập encoding cho request để xử lý tiếng Việt
        request.setCharacterEncoding("UTF-8");

        // Lấy dữ liệu từ form
        String title = request.getParameter("title");
        String detail = request.getParameter("detail");
        String category = request.getParameter("category");
        String imageLink = request.getParameter("imageLink");
        // Nếu checkbox "status" được chọn thì có giá trị, nếu không thì null
        String statusget = request.getParameter("status");
        boolean status = true;
        if(statusget == "hide"){
            status = false;
        }else{
            status = true;
        }

        // Lấy thông tin user từ session để làm tác giả
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            // Nếu chưa đăng nhập, chuyển hướng đến trang login
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        User author = (User) session.getAttribute("user");

        // Tạo đối tượng Post mới và set các trường
        Post post = new Post(title, detail, category, imageLink, status, author);

        // Gọi DBContext để thêm bài viết mới vào bảng blogs
        PostDBContext postDB = new PostDBContext();
       
        boolean success = postDB.addPost(post);

        // Sau khi thêm thành công, chuyển hướng về trang danh sách bài viết
        if (success) {
            response.sendRedirect(request.getContextPath() + "/manager/postList");
        } else {
            request.setAttribute("error", "Failed to add post. Please try again.");
            request.getRequestDispatcher("addPost.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Nếu yêu cầu GET, chuyển tiếp đến trang addPost.jsp để hiển thị form
        request.getRequestDispatcher("addPost.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
