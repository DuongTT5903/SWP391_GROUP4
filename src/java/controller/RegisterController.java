package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dal.UserDBContext;
import model.User;
 // Import lớp gửi email

public class RegisterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("views/register.jsp").forward(request, response);
    }

   @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");

    String user = request.getParameter("user");
    String email = request.getParameter("email");
    String gender = request.getParameter("gender");
    String fullname = request.getParameter("fullname");
    String phone = request.getParameter("phone");
    String pass = request.getParameter("pass");

    UserDBContext account = new UserDBContext();

    try {
        // Kiểm tra tài khoản hoặc email đã tồn tại chưa
        if (account.checkEmailExisted(email) != null) {
            request.setAttribute("e", "Email đã tồn tại");
            request.getRequestDispatcher("views/register.jsp").forward(request, response);
            return;
        }
        if (account.checkAccountExisted(user) != null) {
            request.setAttribute("e", "Tài khoản đã tồn tại");
            request.getRequestDispatcher("views/register.jsp").forward(request, response);
            return;
        }
        if (user.contains(" ") || pass.contains(" ")) {
            request.setAttribute("e", "Tài khoản hoặc Mật khẩu không được chứa dấu cách");
            request.getRequestDispatcher("views/register.jsp").forward(request, response);
            return;
        }

        // Biểu thức chính quy để kiểm tra email có domain @gmail.com
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@gmail\\.com$";
        if (!email.matches(emailRegex)) {
            request.setAttribute("e", "Email không hợp lệ! Phải có domain @gmail.com.");
            request.getRequestDispatcher("views/register.jsp").forward(request, response);
            return;
        }

        // Gửi email xác thực với mã OTP
        resetService emailSender = new resetService();
        String otpCode = emailSender.sendVerificationEmail(email, fullname);

        if (otpCode != null) {
            // Lưu thông tin tạm thời vào session
            HttpSession session = request.getSession();
            session.setAttribute("otp", otpCode);
// Chuyển gender từ String thành boolean
boolean genderBoolean = "1".equals(request.getParameter("gender")); // Nếu gender = "1" thì true, ngược lại false

User newUser = new User(fullname, genderBoolean, email, user, pass, phone);

session.setAttribute("registeredUser", newUser);


            // Chuyển hướng đến trang nhập OTP
            response.sendRedirect(request.getContextPath() + "/verify");
        } else {
            request.setAttribute("e", "Lỗi khi gửi email xác thực. Vui lòng thử lại.");
            request.getRequestDispatcher("views/register.jsp").forward(request, response);
        }

    } catch (Exception ex) {
        Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, "Lỗi khi đăng ký: ", ex);
        request.setAttribute("e", "Lỗi hệ thống: " + ex.getMessage());
        request.getRequestDispatcher("views/register.jsp").forward(request, response);
    }
}

    @Override
    public String getServletInfo() {
        return "Servlet xử lý đăng ký người dùng với xác thực email";
    }
}
