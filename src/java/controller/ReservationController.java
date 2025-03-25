package controller;

import dal.CartDBContext;
import dal.ReservationDBContext;
import model.Cart;
import model.Customer;
import model.Reservation;
import model.ReservationDetail;
import model.Service;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<Cart> checkItem = (List<Cart>) session.getAttribute("checkItem");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        if (checkItem == null) {
            checkItem = new ArrayList<>();
        }

        session.setAttribute("checkItem", checkItem);

        String search = request.getParameter("search") != null ? request.getParameter("search") : "";
        int userID = user.getUserID();

        ReservationDBContext reservationDB = new ReservationDBContext();
        Customer customer = reservationDB.getCustomerByID(userID);
        session.setAttribute("customer", customer);

        int categoryID = parseInteger(request.getParameter("category"), 0);
        int currentPage = parseInteger(request.getParameter("page"), 1);
        int recordsPerPage = 6;

        float totalPrice = 0;
        for (Cart c : checkItem) {
            Service service = c.getService();
            if (service != null) {
                totalPrice += c.getService().getServicePrice() * (100 - c.getService().getSalePrice()) * 0.01 * c.getAmount();
            }
        }

        session.setAttribute("totalPrice", totalPrice);

        if (checkItem.isEmpty()) {
            request.setAttribute("cartMessage", "Giỏ hàng của bạn đang trống.");
        }

        int totalRecords = checkItem.size();
        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

        session.setAttribute("currentPage", currentPage);
        session.setAttribute("totalPages", totalPages);
        session.setAttribute("checkItem", checkItem);

        request.getRequestDispatcher("reservation.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Map<String, String> errors = new HashMap<>();

        Customer customer = (Customer) session.getAttribute("customer");
        List<Cart> checkItem = (List<Cart>) session.getAttribute("checkItem");

        if (checkItem == null || checkItem.isEmpty()) {
            response.sendRedirect("cart.jsp");
            return;
        }

        List<ReservationDetail> reservationDetails = new ArrayList<>();

        // Convert Cart to ReservationDetail and add to reservationDetails list
        for (Cart c : checkItem) {
            ReservationDetail detail = new ReservationDetail();

            Service service = c.getService();
            int amount = c.getAmount();

            detail.setService(service);
            detail.setAmount(amount);

            reservationDetails.add(detail);
        }

        Reservation reservation = new Reservation();
        reservation.setReservationDetails(reservationDetails);

        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        int paymentMethod = parseInteger(request.getParameter("payment"), -1);

        if (paymentMethod == -1) {
            errors.put("payment", "Phương thức thanh toán không hợp lệ.");
        }

        reservation.setCustomerName(name);
        reservation.setEmail(email);
        reservation.setAddress(address);
        reservation.setPhone(phone);
        reservation.setUserID(customer.getUser().getUserID());
        reservation.setPaymentMethod(paymentMethod);

        CartDBContext cartDB = new CartDBContext();

        int reservationID = cartDB.addReservation(reservation); // Insert Reservation

        if (reservationID > 0) {
            for (ReservationDetail detail : reservationDetails) {
                detail.setRevationID(reservationID); // Set the ReservationID for each detail
                cartDB.addReservationDetail(detail); // Insert ReservationDetail
            }

            if (cartDB.hasExpiredServices(reservationID)) {
                errors.put("expired", "Có dịch vụ trong giỏ hàng đã hết hạn.");
            }

            if (errors.isEmpty()) {
                float totalPrice = (Float) session.getAttribute("totalPrice");
                String totalPriceStr = String.valueOf(totalPrice);

                boolean success = cartDB.checkoutReservation(
                        reservationID, totalPriceStr, name, phone, email, address
                );

                
                if (success) {
                    cartDB.removeCheckedItems(customer.getUser().getUserID(), checkItem);
                    session.removeAttribute("checkItem");
                    session.removeAttribute("totalPrice");
                   
                    session.setAttribute("reservationID", reservationID);
                    session.setAttribute("totalPrice", totalPrice);

                    // Chuyển hướng đến trang thanh toán
                    response.sendRedirect(request.getContextPath() + "/payment");
                    return;
                } else {
                    errors.put("checkout", "Thanh toán thất bại, vui lòng thử lại.");
                }
            }
        } else {
            errors.put("reservation", "Không thể tạo đơn đặt chỗ.");
        }

        request.setAttribute("errors", errors);
        request.getRequestDispatcher("reservation.jsp").forward(request, response);
    }

    private int parseInteger(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static void main(String[] args) {
        // Main method to handle console interaction (if needed)
    }
}
