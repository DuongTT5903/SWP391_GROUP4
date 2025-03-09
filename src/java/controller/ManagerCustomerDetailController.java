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
public class ManagerCustomerDetailController extends HttpServlet {

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
        request.getRequestDispatcher("/manager/view/customerDetail.jsp").forward(request, response);
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
    try {
        int userID = Integer.parseInt(request.getParameter("userID"));
        String name = request.getParameter("name");
        boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String role = request.getParameter("role");

        UserDBContext db = new UserDBContext();
        User oldUser = db.getUserByIDUserDetail(userID);

        if (oldUser == null) {
            request.setAttribute("error", "Người dùng không tồn tại.");
            request.getRequestDispatcher("/manager/view/customerDetail.jsp").forward(request, response);
            return;
        }

        // Kiểm tra dữ liệu nhập vào
        String error = validateInput(userID, name, email, phone, username, password, db);
        if (error != null) {
            request.setAttribute("error", error);
            request.setAttribute("user", oldUser); // Giữ dữ liệu cũ để hiển thị lại
            request.getRequestDispatcher("/manager/view/customerDetail.jsp").forward(request, response);
            return;
        }

        // Nếu mật khẩu rỗng, giữ nguyên mật khẩu cũ
        if (password == null || password.trim().isEmpty()) {
            password = oldUser.getPassword();
        }

        User user = new User(userID, name, gender, email, username, password, phone, role, null);
        db.updateUserDetail(user);

        // Gửi email thông báo cập nhật (không hiển thị mật khẩu)
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
        if (!oldUser.getPhone().equals(phone)) {
            content.append("<li><strong>Số điện thoại:</strong> ").append(oldUser.getPhone()).append(" → ").append(phone).append("</li>");
        }
        if (!oldUser.getRole().equals(role)) {
            content.append("<li><strong>Quyền hạn:</strong> ").append(oldUser.getRole()).append(" → ").append(role).append("</li>");
        }

        content.append("</ul>");
        content.append("<p>Nếu bạn không thực hiện thay đổi này, vui lòng liên hệ với quản trị viên.</p>");
        content.append("<p>Cảm ơn bạn!</p>");

        resetService emailService = new resetService();
        emailService.sendEmail1(email, subject, content.toString());

        response.sendRedirect(request.getContextPath() + "/manager/customerList");

    } catch (NumberFormatException e) {
        request.setAttribute("error", "ID người dùng không hợp lệ.");
        request.getRequestDispatcher("/manager/view/customerDetail.jsp").forward(request, response);
    } catch (Exception e) {
        request.setAttribute("error", "Có lỗi xảy ra khi cập nhật thông tin.");
        request.getRequestDispatcher("/manager/view/customerDetail.jsp").forward(request, response);
    }
}


    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    private String validateInput(int userID,String name, String email, String phone, String username, String password, UserDBContext db) {
    if (name == null || name.trim().isEmpty()) {
        return "Tên không được để trống.";
    }

    if (email == null || email.trim().isEmpty()) {
        return "Email không được để trống.";
    } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
        return "Email không hợp lệ.";
    }

    if (phone == null || phone.trim().isEmpty()) {
        return "Số điện thoại không được để trống.";
    } else if (!phone.matches("^\\d{10,11}$")) {
        return "Số điện thoại không hợp lệ (phải có 10-11 chữ số).";
    }

    if (username == null || username.trim().isEmpty()) {
        return "Tên đăng nhập không được để trống.";
    } else if (!username.matches("^[A-Za-z0-9_]+$")) {
        return "Tên đăng nhập chỉ được chứa chữ, số và dấu gạch dưới.";
    }

    if (password != null && !password.isEmpty() && password.length() < 1) {
        return "Mật khẩu phải có ít nhất 6 ký tự.";
    }

    if (db.isUserExists(userID, username, email)) {
        return "Tài khoản hoặc email đã tồn tại!";
    }

    return null;
}

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
