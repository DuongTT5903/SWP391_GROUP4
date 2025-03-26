package controller;

import dal.CartDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class VNPayReturnController extends HttpServlet {
    private CartDBContext cartDBContext = new CartDBContext();
    private resetService emailService = new resetService(); // Gọi service email

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            Map<String, String> fields = new HashMap<>();
            for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
                String fieldName = params.nextElement();
                String fieldValue = request.getParameter(fieldName);
                if (fieldValue != null && !fieldValue.isEmpty()) {
                    fields.put(fieldName, fieldValue);
                }
            }

            // Lấy thông tin đơn hàng
            String orderId = request.getParameter("vnp_OrderInfo");
            boolean transSuccess = "00".equals(request.getParameter("vnp_TransactionStatus"));

            // Cập nhật trạng thái đặt chỗ
            cartDBContext.updateReservationStatus(Integer.parseInt(orderId), transSuccess ? 1 : 0);

            // Nếu giao dịch thành công, gửi email
         if (transSuccess) {
    // Lấy email khách hàng từ database
    String email = cartDBContext.getCustomerEmail(Integer.parseInt(orderId));

    if (email != null && !email.isEmpty()) {
        String subject = "Xác nhận thanh toán thành công - Đơn hàng " + orderId;
        String content = "<h1>Xin chào,</h1>" +
                "<p>Chúng tôi đã nhận được thanh toán của bạn.</p>" +
                "<p>Mã đơn hàng: <strong>" + orderId + "</strong></p>" +
                "<p>Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi!</p>";

        boolean isSent = emailService.sendEmail1(email, subject, content);

        if (isSent) {
            System.out.println("Email xác nhận đã được gửi thành công đến " + email);
        } else {
            System.out.println("Gửi email thất bại.");
        }
    } else {
        System.out.println("Không tìm thấy email của khách hàng.");
    }
}


            // Chuyển đến trang kết quả
            request.setAttribute("transResult", transSuccess);
            request.getRequestDispatcher("/view/payment_result.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "VNPay Return Controller";
    }
}
