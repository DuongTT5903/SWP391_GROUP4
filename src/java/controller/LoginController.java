package controller;

import dal.UserDBContext;
import model.User;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

/**
 *
 * @author yugio
 */
public class LoginController extends HttpServlet {

    // Tạo một instance của UserDBContext để tương tác với database
    private UserDBContext userDBContext = new UserDBContext();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Hiển thị trang đăng nhập
        request.getRequestDispatcher("views/login.jsp").forward(request, response);
    }

@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Lấy username và password từ form
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    // Mã hóa mật khẩu trước khi kiểm tra trong database
    String hashedPassword = hashPassword(password);

    User user = userDBContext.getUserByUsername(username, hashedPassword);

    if (user != null) {
        // Đăng nhập thành công, lưu user vào session
        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        String roleID = userDBContext.getRoleIDByUsernameAndPassword(username, hashedPassword);
        session.setAttribute("roleID", roleID);

        // Điều hướng dựa vào roleID
        switch (roleID) {
            case "1":
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                break;
            case "2":
                response.sendRedirect(request.getContextPath() + "/manager/customerList");
                break;
            case "3":
                response.sendRedirect(request.getContextPath() + "/staff/reservationlist");
                break;
            case "4":
                response.sendRedirect(request.getContextPath() + "/homepage");
                break;
            default:
                // Nếu role không hợp lệ, quay lại trang login với thông báo lỗi
                request.setAttribute("e", "Tài khoản không có quyền truy cập");
                request.getRequestDispatcher("views/login.jsp").forward(request, response);
                break;
        }
    } else {
        // Đăng nhập thất bại, báo lỗi
        request.setAttribute("e", "Sai tài khoản hoặc mật khẩu");
        request.getRequestDispatcher("views/login.jsp").forward(request, response);
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
}
