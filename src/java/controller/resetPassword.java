package controller;

import dal.DAOTokenForget;
import dal.UserDBContext;
import model.TokenForgetPassword;
import model.User;
import java.io.IOException;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Servlet for handling password reset requests
 */

public class resetPassword extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(resetPassword.class.getName());
    private final DAOTokenForget DAOToken = new DAOTokenForget();
    private final UserDBContext DAOUser = new UserDBContext();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = request.getParameter("token");
        HttpSession session = request.getSession();

        if (token == null) {
            request.getRequestDispatcher("view/requestPassword.jsp").forward(request, response);
            return;
        }

        TokenForgetPassword tokenForgetPassword = DAOToken.getTokenPassword(token);
        resetService service = new resetService();

        if (tokenForgetPassword == null) {
            request.setAttribute("mess", "Token không hợp lệ");
            request.getRequestDispatcher("view/requestPassword.jsp").forward(request, response);
            return;
        }
        if (tokenForgetPassword.isIsUsed()) {
            request.setAttribute("mess", "Token đã được sử dụng");
            request.getRequestDispatcher("view/requestPassword.jsp").forward(request, response);
            return;
        }
        if (service.isExpireTime(tokenForgetPassword.getExpiryTime())) {
            request.setAttribute("mess", "Token đã hết hạn");
            request.getRequestDispatcher("view/requestPassword.jsp").forward(request, response);
            return;
        }

      User user = DAOUser.getUserById1(tokenForgetPassword.getUserId());
if (user == null) {
    request.setAttribute("mess", "User not found for this token");
    request.getRequestDispatcher("view/requestPassword.jsp").forward(request, response);
    return;
}
request.setAttribute("email", user.getEmail());
session.setAttribute("token", tokenForgetPassword.getToken());
request.getRequestDispatcher("view/resetPassword.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");

        if (!password.equals(confirmPassword)) {
            request.setAttribute("mess", "Mật khẩu xác nhận phải trùng với mật khẩu mới");
            request.setAttribute("email", email);
            request.getRequestDispatcher("view/resetPassword.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        String tokenStr = (String) session.getAttribute("token");

        if (tokenStr == null) {
            request.setAttribute("mess", "Yêu cầu không hợp lệ");
            request.getRequestDispatcher("view/requestPassword.jsp").forward(request, response);
            return;
        }

        TokenForgetPassword tokenForgetPassword = DAOToken.getTokenPassword(tokenStr);
        resetService service = new resetService();

        if (tokenForgetPassword == null) {
            request.setAttribute("mess", "Token không hợp lệ");
            request.getRequestDispatcher("view/requestPassword.jsp").forward(request, response);
            return;
        }
        if (tokenForgetPassword.isIsUsed()) {
            request.setAttribute("mess", "Token đã được sử dụng");
            request.getRequestDispatcher("view/requestPassword.jsp").forward(request, response);
            return;
        }
        if (service.isExpireTime(tokenForgetPassword.getExpiryTime())) {
            request.setAttribute("mess", "Token đã hết hạn");
            request.getRequestDispatcher("view/requestPassword.jsp").forward(request, response);
            return;
        }

        // Cập nhật mật khẩu người dùng
       String hashedPassword = hashPassword(password);
DAOUser.updatePassword1(email, hashedPassword);


        // Đánh dấu token đã sử dụng
        tokenForgetPassword.setIsUsed(true);
        DAOToken.updateStatus(tokenForgetPassword);

        // Xóa token khỏi session và chuyển hướng về trang chủ
        session.removeAttribute("token");
        request.setAttribute("mess", "reset password done");
        request.getRequestDispatcher("view/requestPassword.jsp").forward(request, response);
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
    @Override
    public String getServletInfo() {
        return "Servlet for handling password reset requests";
    }
}
