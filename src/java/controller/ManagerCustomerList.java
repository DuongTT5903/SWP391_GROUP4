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
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.List;
import model.User;

/**
 *
 * @author admin
 */
public class ManagerCustomerList extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AdminUserList</title>");
            out.println("</head>");
            out.println("<body>");
          
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            UserDBContext db = new UserDBContext();
            List<User> users = db.getUsers1();
            request.setAttribute("users", users);
        } catch (Exception e) {

            request.setAttribute("error", "Could not load user list. Please try again later.");
        }
        request.getRequestDispatcher("/manager/view/customerList.jsp").forward(request, response);
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
        UserDBContext db = new UserDBContext();
        String action = request.getParameter("action");

        // Handle user status update first
        if ("updateStatus".equals(action)) {
            int userID = Integer.parseInt(request.getParameter("userID"));
            boolean newStatus = request.getParameter("status").equals("1");
            db.updateUserStatus(userID, newStatus);

            response.sendRedirect(request.getContextPath() + "/manager/view/customerList.jsp");
            return; // Stop further execution
        }

        // Handle user creation
        
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String roleID = request.getParameter("role");

        String errorMessage;
        errorMessage = validateInput( name, email, phone, username, password, db);
        if (errorMessage != null) {
            // Preserve form input to show back in case of an error
            request.setAttribute("error", errorMessage);
            request.setAttribute("name", name);
            request.setAttribute("email", email);
            request.setAttribute("phone", phone);
            request.setAttribute("username", username);
            request.setAttribute("role", roleID);

            // Reload user list for display
            List<User> users = db.getUsers();
            request.setAttribute("users", users);

            request.getRequestDispatcher("/manager/view/customerList.jsp").forward(request, response);
            return; // Stop execution when there is an error
        }

        // Create a new user
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(roleID);

        try {
            db.addUser(user);
            String subject = "Chào mừng bạn đến với hệ thống!";
            String content = "<h1>Xin chào " + name + ",</h1>"
                    + "<p>Bạn đã được thêm vào hệ thống với tài khoản: <strong>" + username + "</strong></p>"
                    + "<p>Vui lòng đăng nhập và đổi mật khẩu để bảo mật tài khoản.</p>"
                    + "<p>Cảm ơn bạn đã tham gia!</p>";

            resetService emailService = new resetService();
            emailService.sendEmail1(email, subject, content);

            response.sendRedirect(request.getContextPath() + "/manager/customerList");
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi trong quá trình thêm người dùng.");
            request.getRequestDispatcher("/manager/view/customerList.jsp").forward(request, response);
        }
    }

    private String validateInput( String name, String email, String phone, String username, String password, UserDBContext db) {
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || username.isEmpty() || password.isEmpty()) {
            return "Vui lòng điền đầy đủ thông tin.";
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
        if (password != null && !password.isEmpty() && password.length() < 1) {
            return "Mật khẩu phải có ít nhất 6 ký tự.";
        }
        
        if (db.isUserExistsUE( username, email)) {
            return "Tài khoản hoặc email đã tồn tại!";
        }
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
