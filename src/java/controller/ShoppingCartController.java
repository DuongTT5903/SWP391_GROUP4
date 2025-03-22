package controller;

import dal.ReservationDBContext;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import model.Cart;
import model.Customer;
import model.User;

/**
 * Servlet hiển thị giỏ hàng
 */
public class ShoppingCartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userID = user.getUserID();
        int currentPage = 1;
        int recordsPerPage = 6;

        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.trim().isEmpty()) {
            try {
                currentPage = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                currentPage = 1; // If error, default to page 1
            }
        }

        ReservationDBContext reservationDB = new ReservationDBContext();

        // Get customer info and save it into session
        Customer customer = reservationDB.getCustomerByID(userID);
        session.setAttribute("customer", customer);

        int totalRecords = reservationDB.getCartCount(userID);
        int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);

        // Get all cart items (both checked and unchecked)
        List<Cart> cartItems = reservationDB.getCart("", userID, 0, currentPage, recordsPerPage);
         
        // Ensure cartItems is not null
        if (cartItems == null) {
            cartItems = new ArrayList<>();  // Initialize to an empty list if null
        }
        List<Cart> checkItem = reservationDB.getCart1("", userID, 0, currentPage, recordsPerPage);
 if (checkItem == null) {
            checkItem = new ArrayList<>();  // Initialize to an empty list if null
        }
        // Save all cart items to session (keeps the cart state for all items, both checked and unchecked)
        session.setAttribute("cartItems", cartItems);
   session.setAttribute("checkItem", checkItem);
        // If no items are checked, show a message
        if (cartItems.isEmpty()) {
            request.setAttribute("cartMessage", "Giỏ hàng của bạn đang trống.");
        }

        // Initialize checkedCartItems to avoid NullPointerException
        List<Cart> checkedCartItems = new ArrayList<>();
        float total = 0;

        // Only include checked items
        for (Cart c : cartItems) {
            if (c != null && c.isCheckService()) {  // Check if the Cart object is not null and is selected
                
                if (c.getService() != null) {  // Ensure the service is not null
                    // Calculate the total for checked items
                    total += c.getService().getServicePrice() * (100 - c.getService().getSalePrice()) * 0.01 * c.getAmount();
                }
            }
        }

        // Set attributes for the JSP page
        request.setAttribute("total", total);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("cartItems", cartItems);  // Pass all items (checked and unchecked) to JSP for display
        // Pass checked items for total calculation

        // Forward to the shopping cart page
        request.getRequestDispatcher("shoppingCart.jsp").forward(request, response);
    }
}
