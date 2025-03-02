package controller;

import dal.DAOTokenForget;
import dal.UserDBContext;
import model.TokenForgetPassword;
import model.User;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

public class requestPassword extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(requestPassword.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("view/requestPassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDBContext daoUser = new UserDBContext();
        String email = request.getParameter("email");

        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("mess", "Email không được để trống");
            request.getRequestDispatcher("view/requestPassword.jsp").forward(request, response);
            return;
        }

        User user = daoUser.getUserByEmail(email);

        if (user == null) {
            request.setAttribute("mess", "Email không tồn tại");
            request.getRequestDispatcher("view/requestPassword.jsp").forward(request, response);
            return;
        }

        resetService service = new resetService();
        String token = service.generateToken();
        String linkReset = "http://localhost:8080/SWP391_GROUP4/reset?token=" + token;

        // Lấy giá trị LocalDateTime từ service và không cần format
        LocalDateTime expiryTime = service.expireDateTime(); // Get expiryTime from service

        TokenForgetPassword newTokenForget = new TokenForgetPassword();  // Khởi tạo đối tượng rỗng
        newTokenForget.setUserId(user.getUserID());  // Gán User ID
        newTokenForget.setIsUsed(false);              // Gán giá trị isUsed
        newTokenForget.setToken(token);               // Gán token
        newTokenForget.setExpiryTime(expiryTime);     // Gán thời gian hết hạn

        DAOTokenForget daoToken = new DAOTokenForget();
        boolean insertTokenForget = daoToken.insertTokenForget(newTokenForget);

        if (!insertTokenForget) {
            String errorMessage = "❌ Insert thất bại! userID: " + user.getUserID() + 
                                  ", token: " + token + 
                                  ", expiryTime: " + expiryTime;
            LOGGER.log(Level.SEVERE, errorMessage);
    
            request.setAttribute("mess", errorMessage); // Hiển thị lỗi chi tiết trong JSP
            request.getRequestDispatcher("view/requestPassword.jsp").forward(request, response);
            return;
        }

        boolean isSend = service.sendEmail(email, linkReset, user.getName());

        if (!isSend) {
            request.setAttribute("mess", "Không thể gửi yêu cầu");
            request.getRequestDispatcher("view/requestPassword.jsp").forward(request, response);
            return;
        }

        request.setAttribute("mess", "Gửi yêu cầu thành công");
        request.getRequestDispatcher("view/requestPassword.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for handling password reset requests";
    }

    // ========================== 🛠 MAIN METHOD FOR TESTING ==========================
    public static void main(String[] args) {
        UserDBContext daoUser = new UserDBContext();
        String email = "duongthai5903@gmail.com";

        // Lấy thông tin user từ email
        User user = daoUser.getUserByEmail(email);

        if (user != null) {
            System.out.println("✅ Tìm thấy user với email: " + email);
            System.out.println("🆔 userId: " + user.getUserID());
            System.out.println("👤 Họ tên: " + user.getName());
            
            // Tạo đối tượng TokenForgetPassword để lấy expiryTime
            resetService service = new resetService();
            TokenForgetPassword tokenForgetPassword = new TokenForgetPassword(
                user.getUserID(), false, service.generateToken(), service.expireDateTime()
            );
            
            // Gọi getExpiryTime() từ đối tượng TokenForgetPassword
            System.out.println("⏳ Expiry Time: " + tokenForgetPassword.getExpiryTime());
        } else {
            System.out.println("❌ Không tìm thấy user với email: " + email);
        }
    }
}
