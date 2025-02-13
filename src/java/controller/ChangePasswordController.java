package controller;

import dal.UserDBContext;
import model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet xử lý thay đổi mật khẩu.
 */
public class ChangePasswordController extends HttpServlet {

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("views/changepassword.jsp").forward(request, response);
    }

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
        String currentPassword = udao.getPasswordByUsername(u.getUsername()); // Lấy mật khẩu từ DB

        if (!oldpass.equals(currentPassword)) {
            request.setAttribute("mess", "Mật khẩu cũ không đúng.");
            request.getRequestDispatcher("views/changepassword.jsp").forward(request, response);
        } else if (!newpass.equals(renewpass)) {
            request.setAttribute("mess", "Mật khẩu mới không khớp.");
            request.getRequestDispatcher("views/changepassword.jsp").forward(request, response);
        } else {
            udao.changePassword(newpass, u.getUsername());
            session.setAttribute("user", u); // Cập nhật user trong session
            response.sendRedirect(request.getContextPath() + "/homepage");
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet xử lý đổi mật khẩu.";
    }
}
