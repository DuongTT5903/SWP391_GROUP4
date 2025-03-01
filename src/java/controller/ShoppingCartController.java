package controller;

import dal.ServiceDBContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ReservationDetail;
import model.Service;

/**
 * Servlet hiển thị giỏ hàng
 */
public class ShoppingCartController extends HttpServlet {

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

        // If cart is empty, show an empty message
        if (cartData.isEmpty()) {
            request.setAttribute("cartItems", new ArrayList<>());            
            request.setAttribute("cartMessage", "Giỏ hàng của bạn đang trống.");
            request.getRequestDispatcher("view/shoppingCart.jsp").forward(request, response);
            return;
        }
        
        List<ReservationDetail> cartItems = new ArrayList<>();
        ServiceDBContext serviceDAO = new ServiceDBContext();
        int total = 0;
        for (String item : cartData.split("-")) {
            try {
                String[] parts = item.split("/");
                if (parts.length < 2) {
                    continue;
                }
                String[] info = parts[0].split("~");
                int serviceID = Integer.parseInt(info[1]);
                int amount = Integer.parseInt(info[2]);
                int numberOfPeople = Integer.parseInt(parts[1]);
                
                Service service = serviceDAO.getServiceByID(serviceID);
                
                if (service != null) {
                    ReservationDetail reservationDetail = new ReservationDetail(0, 0, service, amount, numberOfPeople);
                    cartItems.add(reservationDetail);
                    total += service.getServicePrice() * (100 - service.getSalePrice()) * 10 * amount * numberOfPeople;
                }
            } catch (NumberFormatException e) {
                System.err.println("Lỗi parse dữ liệu giỏ hàng: " + item);
            }
            
        }
        
        if (cartItems.isEmpty()) {
            request.setAttribute("cartMessage", "Giỏ hàng của bạn đang trống.");
        }
        request.setAttribute("total", total);
        request.setAttribute("cartItems", cartItems);
        request.getRequestDispatcher("view/shoppingCart.jsp").forward(request, response);
    }
    
}
