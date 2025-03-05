package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import dal.UserDBContext;

public class VerifyOTPController extends HttpServlet {
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.getRequestDispatcher("view/verify.jsp").forward(request, response);
}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String enteredOTP = request.getParameter("otp");
        String sessionOTP = (String) session.getAttribute("otp");
        String user = (String) session.getAttribute("registeredUser");

        if (enteredOTP != null && enteredOTP.equals(sessionOTP)) {
            // Kích hoạt tài khoản trong database
            UserDBContext db = new UserDBContext();
            db.activateAccount(user);

            // Xóa session OTP sau khi xác thực thành công
            session.removeAttribute("otp");
            session.removeAttribute("registeredUser");

            request.setAttribute("message", "Xác thực thành công! Vui lòng đăng nhập.");
            request.getRequestDispatcher("views/login.jsp").forward(request, response);
        } else {
            request.setAttribute("e", "Mã OTP không đúng! Vui lòng thử lại.");
            request.getRequestDispatcher("view/verify.jsp").forward(request, response);
        }
    }
}
