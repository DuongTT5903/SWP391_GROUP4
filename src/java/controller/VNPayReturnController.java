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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            Map<String, String> fields = new HashMap<>();
            for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
                String fieldName = params.nextElement();
                String fieldValue = request.getParameter(fieldName); // Không encode
                if (fieldValue != null && !fieldValue.isEmpty()) {
                    fields.put(fieldName, fieldValue);
                }
            }

            // Lấy thông tin đơn hàng
            String orderId = request.getParameter("vnp_OrderInfo");
            boolean transSuccess = "00".equals(request.getParameter("vnp_TransactionStatus"));

            // Cập nhật trạng thái đặt chỗ mà KHÔNG kiểm tra chữ ký
            cartDBContext.updateReservationStatus(Integer.parseInt(orderId), transSuccess ? 1 : 0);

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
