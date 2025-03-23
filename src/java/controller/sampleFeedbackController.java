/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.FeedbackDBContext;
import dal.ServiceDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import model.Feedback;
import model.Service;
import model.ServiceCategory;
import model.User;

/**
 *
 * @author yugio
 */
public class sampleFeedbackController extends HttpServlet {
    // Xử lý GET: Hiển thị form feedback
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy serviceID (nếu người dùng muốn feedback cho 1 dịch vụ cụ thể)
        String serviceIDParam = request.getParameter("serviceID");
        Service service = null;

        if (serviceIDParam != null) {
            try {
                int serviceID = Integer.parseInt(serviceIDParam);
                ServiceDBContext sdb = new ServiceDBContext();
                service = sdb.getServiceByID(serviceID);
            } catch (NumberFormatException e) {
                // serviceID không hợp lệ, có thể ghi log hoặc bỏ qua
            }
        }

        // Lấy thông tin user từ session (nếu đã đăng nhập)
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        // Lấy danh sách categories để hiển thị trên sidebar (nếu cần)
        ServiceDBContext sdb = new ServiceDBContext();
        List<ServiceCategory> categories = sdb.getServiceCategories();
        request.setAttribute("categories", categories);

        // Đưa service và user xuống JSP
        request.setAttribute("service", service);
        request.setAttribute("user", user);

        // Forward sang trang feedback.jsp
        request.getRequestDispatcher("/view/samplefeedback.jsp").forward(request, response);
    }

    // Xử lý POST: Khi user submit form feedback
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy dữ liệu từ form
        String serviceIDParam = request.getParameter("serviceID");
        String feedbackDetail = request.getParameter("feedbackDetail");
        String ratedParam = request.getParameter("rated");
        String imgLink = request.getParameter("imgLink");
        
        // Nếu muốn lưu cả thông tin liên hệ (trong trường hợp user chưa đăng nhập),
        // ta cũng lấy từ form: fullName, gender, email, mobile...
        // (nếu user đăng nhập thì thường ta lấy từ session)
        String fullName = request.getParameter("fullName");
        String genderParam = request.getParameter("gender");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        
        // Lấy user từ session (nếu có)
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Chuyển đổi serviceID, rated sang int
        int serviceID = 0;
        int rated = 0;
        try {
            if (serviceIDParam != null && !serviceIDParam.trim().isEmpty()) {
                serviceID = Integer.parseInt(serviceIDParam);
            }
            rated = Integer.parseInt(ratedParam);
        } catch (NumberFormatException e) {
            // Xử lý lỗi, ví dụ chuyển hướng trang
            response.sendRedirect("error.jsp");
            return;
        }

        // Tạo đối tượng Feedback
        Feedback feedback = new Feedback();
        feedback.setFeedbackDetail(feedbackDetail);
        feedback.setRated(rated);
        feedback.setImgLink(imgLink);
        feedback.setCreationDate(new Date()); // Ngày hiện tại
        feedback.setStatus(true);             // Cho phép hiển thị feedback

        // Nếu user đăng nhập, ta lưu userID vào CustomerID
        // (còn nếu user chưa đăng nhập, có thể lưu tạm 0 hoặc tuỳ DB)
        if (user != null) {
            feedback.setCustomerID(user.getUserID());
            feedback.setUser(user);  // Gắn thêm đối tượng user (nếu muốn)
        } else {
            // Trường hợp không đăng nhập, ta có thể
            // 1) tạo user tạm? 
            // 2) hoặc lưu contact info tạm vào Feedback (DB cần thêm cột?)
            // Tuỳ thiết kế DB, ví dụ:
            feedback.setCustomerID(0);
            // Hoặc ta tạo 1 user tạm:
            User guestUser = new User();
            guestUser.setName(fullName);
            guestUser.setGender("1".equals(genderParam));
            guestUser.setEmail(email);
            guestUser.setPhone(mobile);
            feedback.setUser(guestUser);
        }

        // Gắn service (nếu có serviceID)
        if (serviceID > 0) {
            Service service = new Service();
            service.setServiceID(serviceID);
            feedback.setServices(service);
        }

        // Lưu feedback
        FeedbackDBContext fdb = new FeedbackDBContext();
        fdb.addFeedback(feedback);

        // Sau khi lưu, chuyển hướng về trang chủ hoặc thông báo thành công
        response.sendRedirect(request.getContextPath() + "/homepage");
    }
}

