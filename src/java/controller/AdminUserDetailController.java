/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.UserDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Role;
import model.User;

/**
 *
 * @author admin
 */
public class AdminUserDetailController extends HttpServlet {

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
            out.println("<title>Servlet AdminUserDetailController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminUserDetailController at " + request.getContextPath() + "</h1>");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userID = Integer.parseInt(request.getParameter("id"));
        UserDBContext db = new UserDBContext();
        User user = db.getUserByIDUserDetail(userID);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/admin/userDetail.jsp").forward(request, response);
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
   @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    int userID = Integer.parseInt(request.getParameter("userID"));
    String name = request.getParameter("name");
    boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
    String email = request.getParameter("email");
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String phone = request.getParameter("phone");
    String role = request.getParameter("role");

    UserDBContext db = new UserDBContext();
    
    // Lấy thông tin user cũ
    User oldUser = db.getUserByIDUserDetail(userID);

    // Cập nhật thông tin mới
    User user = new User(userID, name, gender, email, username, password, phone, role, null);
    db.updateUserDetail(user);

    // Xây dựng nội dung email thông báo thay đổi
    String subject = "Thông báo cập nhật thông tin tài khoản";
    StringBuilder content = new StringBuilder("<h1>Xin chào " + name + ",</h1>");
    content.append("<p>Thông tin tài khoản của bạn đã được cập nhật:</p>");
    content.append("<ul>");

    if (!oldUser.getName().equals(name)) {
        content.append("<li><strong>Họ tên:</strong> ").append(oldUser.getName()).append(" → ").append(name).append("</li>");
    }
    if (!oldUser.getEmail().equals(email)) {
        content.append("<li><strong>Email:</strong> ").append(oldUser.getEmail()).append(" → ").append(email).append("</li>");
    }
    if (!oldUser.getUsername().equals(username)) {
        content.append("<li><strong>Tên đăng nhập:</strong> ").append(oldUser.getUsername()).append(" → ").append(username).append("</li>");
    }
        if (!oldUser.getPassword().equals(password)) {
        content.append("<li><strong>Pass:</strong> ").append(oldUser.getPassword()).append(" → ").append(password).append("</li>");
    }
    if (!oldUser.getPhone().equals(phone)) {
        content.append("<li><strong>Số điện thoại:</strong> ").append(oldUser.getPhone()).append(" → ").append(phone).append("</li>");
    }
    if (!oldUser.getRole().equals(role)) {
        content.append("<li><strong>Quyền hạn:</strong> ").append(oldUser.getRole()).append(" → ").append(role).append("</li>");
    }

    content.append("</ul>");
    content.append("<p>Nếu bạn không thực hiện thay đổi này, vui lòng liên hệ với quản trị viên.</p>");
    content.append("<p>Cảm ơn bạn!</p>");

    // Gửi email thông báo cập nhật
    resetService emailService = new resetService();
    emailService.sendEmail1(email, subject, content.toString());

    // Chuyển hướng về danh sách user
    response.sendRedirect(request.getContextPath() + "/admin/userList");
}

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
