package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.UserDBContext;
import model.User;

/**
 *
 * @author yugio
 */
public class RegisterController extends HttpServlet {

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
        request.getRequestDispatcher("views/register.jsp").forward(request, response);
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
           if (user.contains(" ") || pass.contains(" ") ) {
    // Nếu có khoảng trắng, trả về thông báo lỗi
            request.setAttribute("e", "Tài khoản hoặc Mật khẩu không được chứa dấu cách");
            request.getRequestDispatcher("views/register.jsp").forward(request, response);
                return;
    
           }
           // Biểu thức chính quy để kiểm tra email có domain là @gmail.com
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@gmail\\.com$";

        if (!email.matches(emailRegex)) {
            request.setAttribute("e","Email không hợp lệ! Phải có domain @gmail.com.");
             request.getRequestDispatcher("views/register.jsp").forward(request, response);
                return;
        }

            // Nếu tài khoản & email chưa tồn tại, tiến hành đăng ký
            account.signup(fullname, gender, email, user, pass, phone);
            request.setAttribute("message", "Đăng ký thành công! Vui lòng đăng nhập.");
            request.getRequestDispatcher("views/register.jsp").forward(request, response);

        } catch (Exception ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, "Lỗi khi đăng ký: ", ex);
            request.setAttribute("e", "Lỗi hệ thống: " + ex.getMessage()); // Gán nội dung lỗi vào e
            request.getRequestDispatcher("views/register.jsp").forward(request, response);
        }

    }



    /**
     *
     * @return
     */
    @Override
    public String getServletInfo() {
        return "Servlet xử lý đăng ký người dùng";
    }
}
