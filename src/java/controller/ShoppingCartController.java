package controller;

import dal.ServiceDBContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Service;

/**
 * Servlet hiển thị giỏ hàng
 */
public class ShoppingCartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cartData = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("cart".equals(cookie.getName())) {
                    cartData = cookie.getValue();
                    break;
                }
            }
        }

        // Nếu giỏ hàng trống, gửi danh sách rỗng và hiển thị thông báo
        if (cartData.isEmpty()) {
            request.setAttribute("cartItems", new ArrayList<>()); // Danh sách rỗng
            request.setAttribute("cartMessage", "Giỏ hàng của bạn đang trống.");
            request.getRequestDispatcher("view/shoppingCart.jsp").forward(request, response);
            return;
        }

        // Tách chuỗi cartData thành danh sách serviceID
        List<Service> cartItems = new ArrayList<>();
        ServiceDBContext serviceDAO = new ServiceDBContext(); // DAO để truy vấn thông tin dịch vụ
        for (String item : cartData.split("-")) {
            try {
                int serviceID = Integer.parseInt(item);
                Service service = serviceDAO.getServiceByID(serviceID);
                if (service != null) {
                    cartItems.add(service);
                }
            } catch (NumberFormatException e) {
                // Bỏ qua nếu dữ liệu không hợp lệ
                System.err.println("Lỗi parse serviceID: " + item);
            }
        }

        // Nếu không có dịch vụ hợp lệ, hiển thị giỏ hàng trống
        if (cartItems.isEmpty()) {
            request.setAttribute("cartMessage", "Giỏ hàng của bạn đang trống.");
        }

        request.setAttribute("cartItems", cartItems);
        request.getRequestDispatcher("view/shoppingCart.jsp").forward(request, response);
    }
}
