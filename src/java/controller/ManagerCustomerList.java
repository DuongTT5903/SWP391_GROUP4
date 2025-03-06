package controller;

import dal.UserDBContext;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

public class ManagerCustomerList extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ManagerCustomerList.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UserDBContext db = new UserDBContext();
            List<User> users = db.getUsers1();
            request.setAttribute("users", users);
            request.getRequestDispatcher("/manager/view/customerList.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lấy danh sách người dùng", e);
            request.setAttribute("error", "Không thể tải danh sách người dùng.");
            request.getRequestDispatcher("/manager/view/customerList.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDBContext db = new UserDBContext();
        String action = request.getParameter("action");

        try {
            if ("updateStatus".equals(action)) {
                String userIDParam = request.getParameter("userID");
                String statusParam = request.getParameter("status");

                if (userIDParam == null || statusParam == null) {
                    LOGGER.warning("Thiếu thông tin userID hoặc status khi cập nhật trạng thái.");
                    throw new IllegalArgumentException("Thiếu thông tin cần thiết!");
                }

                int userID = Integer.parseInt(userIDParam);
                boolean newStatus = "1".equals(statusParam);

                db.updateUserStatus(userID, newStatus);
                response.sendRedirect(request.getContextPath() + "/manager/customerList");
                return;
            }

            // Xử lý thêm user mới
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String roleID = request.getParameter("role");

            String errorMessage = validateInput(name, email, phone, username, password, db);
            if (errorMessage != null) {
                LOGGER.warning("Lỗi xác thực dữ liệu: " + errorMessage);
                request.setAttribute("error", errorMessage);
                request.setAttribute("name", name);
                request.setAttribute("email", email);
                request.setAttribute("phone", phone);
                request.setAttribute("username", username);
                request.setAttribute("role", roleID);

                List<User> users = db.getUsers1();
                request.setAttribute("users", users);
                request.getRequestDispatcher("/manager/view/customerList.jsp").forward(request, response);
                return;
            }

            // Tạo user mới
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPhone(phone);
            user.setUsername(username);
            user.setPassword(password);
            user.setRole(roleID);

            db.addUser(user);
            LOGGER.info("Thêm user thành công: " + username);

            response.sendRedirect(request.getContextPath() + "/manager/customerList");
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Lỗi chuyển đổi số (userID không hợp lệ): ", e);
            request.setAttribute("error", "ID người dùng không hợp lệ.");
            request.getRequestDispatcher("/manager/view/customerList.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Lỗi đầu vào: {0}", e.getMessage());
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/manager/view/customerList.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi hệ thống khi xử lý POST", e);
            request.setAttribute("error", "Lỗi trong quá trình xử lý.");
            request.getRequestDispatcher("/manager/view/customerList.jsp").forward(request, response);
        }
    }

    private String validateInput(String name, String email, String phone, String username, String password, UserDBContext db) {
        if (name == null || name.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            phone == null || phone.trim().isEmpty() ||
            username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            LOGGER.warning("Xác thực thất bại: Thiếu thông tin bắt buộc.");
            return "Vui lòng điền đầy đủ thông tin.";
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            LOGGER.warning("Xác thực thất bại: Email không hợp lệ - " + email);
            return "Email không hợp lệ.";
        }

        if (!phone.matches("^\\d{10,11}$")) {
            LOGGER.warning("Xác thực thất bại: Số điện thoại không hợp lệ - " + phone);
            return "Số điện thoại không hợp lệ (phải có 10-11 chữ số).";
        }

        if (db.isUserExists(username, email)) {
            LOGGER.warning("Xác thực thất bại: Tài khoản hoặc email đã tồn tại - " + username);
            return "Tài khoản hoặc email đã tồn tại!";
        }

        return null;
    }
}
