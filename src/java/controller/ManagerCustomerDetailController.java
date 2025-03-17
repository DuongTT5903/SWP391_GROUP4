package controller;

import dal.UserDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 
 */
public class ManagerCustomerDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int userID = Integer.parseInt(request.getParameter("id"));
            UserDBContext db = new UserDBContext();
            User user = db.getUserByIDUserDetail(userID);

            if (user == null) {
                request.setAttribute("error", "Người dùng không tồn tại.");
            } else {
                request.setAttribute("user", user);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID người dùng không hợp lệ.");
        }
        request.getRequestDispatcher("/manager/view/customerDetail.jsp").forward(request, response);
    }

   @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    try {
        int userID = Integer.parseInt(request.getParameter("userID"));
        String name = request.getParameter("name");
        String genderStr = request.getParameter("gender");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String newPassword = request.getParameter("password");
        String phone = request.getParameter("phone");
        String role = request.getParameter("role");

        boolean gender = "true".equals(genderStr);
        UserDBContext db = new UserDBContext();
        User oldUser = db.getUserByIDUserDetail(userID);

        if (oldUser == null) {
            request.setAttribute("error", "Người dùng không tồn tại.");
            request.getRequestDispatcher("/manager/view/customerDetail.jsp").forward(request, response);
            return;
        }

        // Kiểm tra lỗi đầu vào
        String error = validateInput(userID, name, email, phone, username, newPassword, db);
        if (error != null) {
            request.setAttribute("error", error);
            request.setAttribute("user", oldUser);
            request.getRequestDispatcher("/manager/view/customerDetail.jsp").forward(request, response);
            return;
        }

        // Nếu không nhập mật khẩu mới, giữ nguyên mật khẩu cũ
        if (newPassword == null || newPassword.trim().isEmpty()) {
            newPassword = oldUser.getPassword();
        } else {
            newPassword = hashPassword(newPassword);
        }

        // Cập nhật thông tin người dùng
        User user = new User(userID, name, gender, email, username, newPassword, phone, role, null);
        db.updateUserDetail(user);

        // === GỬI EMAIL THÔNG BÁO ===
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

        // Gửi email
        resetService emailService = new resetService();
        emailService.sendEmail1(email, subject, content.toString());

        // Chuyển hướng về danh sách người dùng sau khi cập nhật thành công
        response.sendRedirect(request.getContextPath() + "/manager/customerList");

    } catch (NumberFormatException e) {
        request.setAttribute("error", "ID người dùng không hợp lệ.");
        request.getRequestDispatcher("/manager/view/customerDetail.jsp").forward(request, response);
    } catch (Exception e) {
        request.setAttribute("error", "Có lỗi xảy ra khi cập nhật thông tin.");
        request.getRequestDispatcher("/manager/view/customerDetail.jsp").forward(request, response);
    }
}


    private String validateInput(int userID, String name, String email, String phone, String username, String password, UserDBContext db) {
        if (name == null || name.trim().isEmpty()) return "Tên không được để trống.";
        if (email == null || email.trim().isEmpty()) return "Email không được để trống.";
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) return "Email không hợp lệ.";
        if (phone == null || phone.trim().isEmpty()) return "Số điện thoại không được để trống.";
        if (!phone.matches("^\\d{10,11}$")) return "Số điện thoại không hợp lệ.";
        if (username == null || username.trim().isEmpty()) return "Tên đăng nhập không được để trống.";
        if (!username.matches("^[A-Za-z0-9_]+$")) return "Tên đăng nhập chỉ được chứa chữ, số và dấu gạch dưới.";
        if (password != null && !password.isEmpty() && password.length() < 3) return "Mật khẩu phải có ít nhất 6 ký tự.";

        String userIDst = Integer.toString(userID);
        if (db.isUserExists(userIDst, username, email)) return "Tài khoản hoặc email đã tồn tại!";

        return null;
    }

    private String hashPassword(String password) {
        String salt = "RANDOM_SALT";
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
}
