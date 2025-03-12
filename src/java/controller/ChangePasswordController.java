package controller;

import dal.UserDBContext;
import model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Servlet xử lý thay đổi mật khẩu.
 */
public class ChangePasswordController extends HttpServlet {

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
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login"); // Đổi thành trang login
        } else {
            request.getRequestDispatcher("views/changepassword.jsp").forward(request, response);
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("views/changepassword.jsp").forward(request, response);
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
 @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    HttpSession session = request.getSession();
    User u = (User) session.getAttribute("user");

    if (u == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String oldpass = request.getParameter("oldpass");
    String newpass = request.getParameter("newpass");
    String renewpass = request.getParameter("renewpass");

    UserDBContext udao = new UserDBContext();
    String hashedPasswordFromDB = udao.getPasswordByUsername(u.getUsername()); // Lấy hash từ DB

    // Mã hóa mật khẩu cũ để kiểm tra
    String hashedOldPassword = hashPassword(oldpass);

    // Kiểm tra mật khẩu cũ có đúng không
    if (!hashedOldPassword.equals(hashedPasswordFromDB)) {
        request.setAttribute("mess", "Mật khẩu cũ không đúng.");
        request.getRequestDispatcher("views/changepassword.jsp").forward(request, response);
        return;
    }

    // Kiểm tra mật khẩu mới nhập lại có khớp không
    if (!newpass.equals(renewpass)) {
        request.setAttribute("mess", "Mật khẩu mới không khớp.");
        request.getRequestDispatcher("views/changepassword.jsp").forward(request, response);
        return;
    }

    // Mã hóa mật khẩu mới trước khi lưu vào database
    String hashedNewPassword = hashPassword(newpass);

    // Cập nhật mật khẩu mới vào database
    udao.changePassword(hashedNewPassword, u.getUsername());

    session.setAttribute("user", u); // Cập nhật user trong session
    response.sendRedirect(request.getContextPath() + "/homepage");
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


    /**
     *
     * @return
     */
    @Override
    public String getServletInfo() {
        return "Servlet xử lý đổi mật khẩu.";
    }
}
