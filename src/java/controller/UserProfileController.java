package controller;

import dal.UserDBContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

/**
 *
 * @author yugio
 */
public class UserProfileController extends HttpServlet {

    private UserDBContext userDB = new UserDBContext(); // Khai báo biến toàn cục

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

        String service = request.getParameter("service");
        if (service == null) {
            service = "profile";
        }

        if (service.equalsIgnoreCase("profile")) {
            HttpSession session = request.getSession(false);
            User user = null;
            if (session != null) {
                user = (User) session.getAttribute("user");
            }

            if (user != null) {
                request.setAttribute("user", user);
                request.getRequestDispatcher("view/userprofile.jsp").forward(request, response);
            } else {
                response.sendRedirect("login.jsp"); // hoặc trang thông báo lỗi
            }
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String service = request.getParameter("service");

        if ("edit".equals(service)) {
            // Lấy dữ liệu từ request
            String idParam = request.getParameter("id");
            int id = Integer.parseInt(idParam);
            String name = request.getParameter("name");
            String genderParam = request.getParameter("gender");
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");

            boolean gender = Boolean.parseBoolean(genderParam); // Chuyển đổi giới tính

            // Lấy user từ session
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            if (user != null) {

                user.setName(name);
                user.setUsername(username);
                user.setGender(gender);
                user.setEmail(email);
                user.setPhone(phone);
                user.setUserID(id);

                userDB.updateUser1(user);
                response.sendRedirect(request.getContextPath() + "/userProfile");

            } else {
                response.sendRedirect(request.getContextPath() + "/userProfile");
            }
        }
    }

    /**
     *
     * @return
     */
    @Override
    public String getServletInfo() {
        return "User profile controller";
    }
}
