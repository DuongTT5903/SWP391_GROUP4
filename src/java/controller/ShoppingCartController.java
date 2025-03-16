package controller;

import dal.ReservationDBContext;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Cart;

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
        int currentPage = 1;
        int recordsPerPage = 6;
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.trim().isEmpty()) {
            try {
                currentPage = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                currentPage = 1; // Nếu lỗi, đặt mặc định là trang 1
            }
        }// Số bản ghi trên mỗi trang
        ReservationDBContext reservationDB = new ReservationDBContext();

        int totalRecords = reservationDB.getAllCart().size(); // Hàm lấy tổng số bản ghi từ DB
        int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);
        List<Cart> cartItems = reservationDB.getCart("", 0, currentPage, recordsPerPage);
        if (cartItems.isEmpty()) {
            request.setAttribute("cartMessage", "Giỏ hàng của bạn đang trống.");
        }
        int total=0;
        for (Cart c : cartItems) {
            if(c.isCheckService()){
                total+=c.getService().getServicePrice()*(100 - c.getService().getSalePrice())*10*c.getAmount();
            }
        }
        request.setAttribute("total", total);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("cartItems", cartItems);
        request.getRequestDispatcher("shoppingCart.jsp").forward(request, response);
    }

}
