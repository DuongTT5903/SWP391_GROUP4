package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dal.UserDBContext;
import model.User;
import model.UserStatus; // Đảm bảo bạn có enum UserStatus

public class VerifyOTPController extends HttpServlet {
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.getRequestDispatcher("view/verify.jsp").forward(request, response);
}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String enteredOTP = request.getParameter("otp");
        HttpSession session = request.getSession();
        String generatedOTP = (String) session.getAttribute("otp");
        User registeredUser = (User) session.getAttribute("registeredUser");
int roleID=4;
        if (enteredOTP != null && generatedOTP != null && enteredOTP.equals(generatedOTP)) {
            // Kiểm tra xem registeredUser có null không để tránh lỗi NullPointerException
            if (registeredUser != null) {
                UserDBContext account = new UserDBContext();
                
                // Đăng ký tài khoản với trạng thái ACTIVE (mặc định là 1)
                account.signup(
                    registeredUser.getName(),
                    registeredUser.isGender(),
                    registeredUser.getEmail(),
                    registeredUser.getUsername(),
                    registeredUser.getPassword(),
                    registeredUser.getPhone() ,
                     roleID,
                    registeredUser.getAddress()
                        
                );

                // Xóa thông tin session sau khi đăng ký thành công
                session.removeAttribute("otp");
                session.removeAttribute("registeredUser");

                // Chuyển hướng đến trang login với thông báo thành công
                request.setAttribute("success", "Xác thực thành công! Tài khoản của bạn đã được đăng ký.");
                request.getRequestDispatcher("views/login.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Lỗi hệ thống: Không tìm thấy thông tin người dùng.");
                request.getRequestDispatcher("view/register.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Mã OTP không chính xác, vui lòng thử lại.");
            request.getRequestDispatcher("view/verify.jsp").forward(request, response);
        }
    }
}
