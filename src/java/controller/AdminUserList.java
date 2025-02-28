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
import model.User;

/**
 *
 * @author admin
 */
public class AdminUserList extends HttpServlet {

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
            out.println("<h1>Servlet AdminUserList at " + request.getContextPath() + "</h1>");
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
        UserDBContext db = new UserDBContext();
        List<User> users = db.getUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/admin/userList.jsp").forward(request, response);
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
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String roleID = request.getParameter("role");
        UserDBContext db = new UserDBContext();

        String errorMessage = validateInput(name, email, phone, username, password, db);
         if (errorMessage != null) {
        // Lưu lại thông tin nhập vào để giữ nguyên dữ liệu trên form
        request.setAttribute("error", errorMessage);
        request.setAttribute("name", name);
        request.setAttribute("email", email);
        request.setAttribute("phone", phone);
        request.setAttribute("username", username);
        request.setAttribute("role", roleID);
        
        // Hiển thị danh sách người dùng lại để tránh lỗi
        List<User> users = db.getUsers();
        request.setAttribute("users", users);
        
        request.getRequestDispatcher("/admin/userList.jsp").forward(request, response);
        return; // Dừng xử lý khi có lỗi
    }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(roleID);

        try {
            db.addUser(user);
            response.sendRedirect(request.getContextPath() + "/admin/userList");
        } catch (IOException e) {
            request.setAttribute("error", "Lỗi trong quá trình thêm người dùng.");
            request.getRequestDispatcher("/admin/userList.jsp").forward(request, response);
        }
    }

    private String validateInput(String name, String email, String phone, String username, String password, UserDBContext db) {
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || username.isEmpty() || password.isEmpty()) {
            return "Vui lòng điền đầy đủ thông tin.";
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return "Email không hợp lệ.";
        }
        if (db.isUserExists(username, email)) {
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
