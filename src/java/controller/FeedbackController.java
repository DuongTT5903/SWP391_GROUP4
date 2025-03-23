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
import model.Feedback;
import model.Service;
import model.User;

public class FeedbackController extends HttpServlet {

    // Xử lý cả GET và POST nếu cần
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* Một trang hiển thị đơn giản nếu cần debug */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Feedback</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Feedback at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // Xử lý GET: Lấy thông tin của service nếu có, sau đó chuyển sang trang feedback.jsp
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String serviceIDParam = request.getParameter("serviceID");
        if (serviceIDParam != null) {
            try {
                int serviceID = Integer.parseInt(serviceIDParam);
                ServiceDBContext sdb = new ServiceDBContext();
                Service service = sdb.getServiceByID(serviceID);
                request.setAttribute("service", service);
            } catch (NumberFormatException e) {
                // Nếu serviceID không hợp lệ, có thể thêm thông báo lỗi hoặc bỏ qua.
            }
        }
        request.getRequestDispatcher("/view/feedback.jsp").forward(request, response);
    }

    // Xử lý POST: Khi người dùng submit form feedback
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy dữ liệu từ form
        String serviceIDParam = request.getParameter("serviceID");
        String detail = request.getParameter("feedbackDetail");
        String ratedParam = request.getParameter("rated");
        String imgLink = request.getParameter("imgLink");

        // Lấy thông tin user từ session (phải đảm bảo user đã đăng nhập)
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Kiểm tra các tham số bắt buộc
        if (serviceIDParam == null || serviceIDParam.trim().isEmpty()
                || ratedParam == null || ratedParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/error.jsp?error=Missing+parameters");
            return;
        }
        int serviceID;
        int rated;
        try {
            serviceID = Integer.parseInt(serviceIDParam);
        } catch (NumberFormatException e) {
            // Nếu serviceID không hợp lệ
            response.sendRedirect(request.getContextPath() + "/error.jsp?error=Invalid+serviceID");
            return;
        }

        try {
            rated = Integer.parseInt(ratedParam);
            // Kiểm tra giá trị rated có nằm trong khoảng 1-5 không
            if (rated < 1 || rated > 5) {
                response.sendRedirect(request.getContextPath() + "/error.jsp?error=Rating+must+be+between+1+and+5");
                return;
            }
        } catch (NumberFormatException e) {
            // Nếu rated không hợp lệ
            response.sendRedirect(request.getContextPath() + "/error.jsp?error=Invalid+rating");
            return;
        }

        // Tạo đối tượng Feedback và thiết lập thông tin
        Feedback feedback = new Feedback();
        feedback.setFeedbackDetail(detail);
        feedback.setCustomerID(user.getUserID()); // Lấy mã người dùng từ session
        feedback.setRated(rated);
        feedback.setImgLink(imgLink);

        // Tạo đối tượng Service chỉ với serviceID (các thông tin khác không cần thiết khi add feedback)
        Service service = new Service();
        service.setServiceID(serviceID);
        feedback.setServices(service);

        // Thiết lập ngày tạo là ngày hiện tại và trạng thái là true (đã duyệt hoặc hiển thị)
        feedback.setCreationDate(new Date());
        feedback.setStatus(true);

        // Sử dụng FeedbackDBContext để thêm feedback vào database
        FeedbackDBContext fdb = new FeedbackDBContext();
        fdb.addFeedback(feedback);

        // Sau khi thêm thành công, chuyển hướng về trang chủ hoặc trang chi tiết dịch vụ.
        // Ví dụ: chuyển về trang homepage.
        response.sendRedirect(request.getContextPath() + "/homepage");
    }

    @Override
    public String getServletInfo() {
        return "FeedbackController cho phép người dùng thêm phản hồi mới.";
    }
}
