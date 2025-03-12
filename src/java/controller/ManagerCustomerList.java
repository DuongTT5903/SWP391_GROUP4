package controller;

import dal.UserDBContext;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

public class ManagerCustomerList extends HttpServlet {

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDBContext db = new UserDBContext();
        String action = request.getParameter("action");

        if ("updateStatus".equals(action)) {
            int userID = Integer.parseInt(request.getParameter("userID"));
            boolean newStatus = request.getParameter("status").equals("1");
            db.updateUserStatus(userID, newStatus);
            response.sendRedirect(request.getContextPath() + "/manager/customerList");
            return;
        }

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String roleID = request.getParameter("role");

        String errorMessage = validateInput(name, email, phone, username, password, db);
        if (errorMessage != null) {
            request.setAttribute("error", errorMessage);
            request.setAttribute("name", name);
            request.setAttribute("email", email);
            request.setAttribute("phone", phone);
            request.setAttribute("username", username);
            request.setAttribute("role", roleID);

            List<User> users = db.getUsers1();
            request.setAttribute("users", users);

            request.getRequestDispatcher("/manager/view/customerList.jsp").forward(request, response);
            return;
        }

        String hashedPassword = hashPassword(password);

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setUsername(username);
        user.setPassword(hashedPassword);
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

 private String hashPassword(String password) {
    String salt = "RANDOM_SALT"; // Nên lưu salt riêng cho từng user
    try {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = md.digest((salt + password).getBytes(StandardCharsets.UTF_8));
        BigInteger number = new BigInteger(1, hashedBytes);
        StringBuilder hexString = new StringBuilder(number.toString(16));

        while (hexString.length() < 64) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException("Lỗi khi mã hóa mật khẩu", e);
    }
}

    private String validateInput(String name, String email, String phone, String username, String password, UserDBContext db) {
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || username.isEmpty() || password.isEmpty()) {
            return "Vui lòng điền đầy đủ thông tin.";
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return "Email không hợp lệ.";
        }
        if (!phone.matches("^\\d{10,11}$")) {
            return "Số điện thoại không hợp lệ (phải có 10-11 chữ số).";
        }
        if (password.length() < 6) {
            return "Mật khẩu phải có ít nhất 6 ký tự.";
        }
        if (db.isUserExistsUE(username, email)) {
            return "Tài khoản hoặc email đã tồn tại!";
        }
        return null;
    }

    @Override
    public String getServletInfo() {
        return "Servlet quản lý danh sách khách hàng";
    }
}
