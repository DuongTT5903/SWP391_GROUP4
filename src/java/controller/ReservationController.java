/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ReservationDBContext;
import dal.ServiceDBContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import model.Customer;
import model.ReservationDetail;
import model.Service;
import model.User;

/**
 *
 * @author trung
 */
public class ReservationController extends HttpServlet {

    private static final int PAGE_SIZE = 6;

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
        String search = request.getParameter("search") != null ? request.getParameter("search") : "";
        int categoryID = request.getParameter("category") != null ? Integer.parseInt(request.getParameter("category")) : 0;
        int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
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
            request.getRequestDispatcher("view/reservation.jsp").forward(request, response);
            return;
        }

        List<ReservationDetail> cartItems = new ArrayList<>();
        List<ReservationDetail> cart = new ArrayList<>();
        ReservationDBContext reservationDB = new ReservationDBContext();
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
                ReservationDetail reservationDetail = new ReservationDetail(0, 0, service, amount, numberOfPeople);
                if (service != null) {
                    cart.add(reservationDetail);
                    total += service.getServicePrice() * (100 - service.getSalePrice()) * 10 * amount * numberOfPeople;
                }
            } catch (NumberFormatException e) {
                System.err.println("Lỗi parse dữ liệu giỏ hàng: " + item);
            }

        }
        cartItems = reservationDB.searchService(cart, search, categoryID, page, PAGE_SIZE);
        if (cartItems.isEmpty()) {
            request.setAttribute("cartMessage", "Không tím thấy kết quả.");
        }
        request.setAttribute("total", total);
        request.setAttribute("cartItems", cartItems);
        request.getRequestDispatcher("view/reservation.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Default behavior: Load service list
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String description = request.getParameter("description") != null ? request.getParameter("description") : "";
        int gender = Integer.parseInt(request.getParameter("gender"));
        String PTTT1 = request.getParameter("payment");
        int PTTT = (PTTT1 != null && !PTTT1.isEmpty()) ? Integer.parseInt(PTTT1) : -1;
        String cardName = request.getParameter("cardName");
        String cardNumber = request.getParameter("cardNumber");
        String CVV = request.getParameter("CVV");
        String expirationDate = request.getParameter("expirationDate");
        LocalDate date = null;
        if (expirationDate != null && !expirationDate.isEmpty()) {
            date = LocalDate.parse(expirationDate);
        }
        LocalDate today = LocalDate.now();
        ReservationDBContext reservationDB = new ReservationDBContext();
        Customer customer;
        if (user == null) {
            customer = null;
        } else {
            customer = reservationDB.getCustomerByID(user.getUserID());
        }

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
            request.getRequestDispatcher("view/reservation.jsp").forward(request, response);
            return;
        }
        List<ReservationDetail> cart = new ArrayList<>();
        int total = 0;
        ServiceDBContext serviceDAO = new ServiceDBContext();
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
                ReservationDetail reservationDetail = new ReservationDetail(0, 0, service, amount, numberOfPeople);
                if (service != null) {
                    cart.add(reservationDetail);
                    total += service.getServicePrice() * (100 - service.getSalePrice()) / 100 * amount * numberOfPeople;
                }
            } catch (NumberFormatException e) {
                System.err.println("Lỗi parse dữ liệu giỏ hàng: " + item);
            }
        }

        int status=0;
        if (PTTT == 0) {
            status=0;
        } else {
            status=1;
        }
        if (user == null) {
            reservationDB.addReservation(name, email, address, phone, today, 0, PTTT, total, gender, description,status);
        } else {
            reservationDB.addReservation(name, email, address, phone, today, user.getUserID(), PTTT, total, gender, description,status);
        }
        if (PTTT == 0) {

            reservationDB.addCardInfo(reservationDB.ReservationID(),cardName, cardNumber, CVV, date);
        }
        for (ReservationDetail c : cart) {
            reservationDB.addReservationDetail(reservationDB.ReservationID(), c.getService().getServiceID(), c.getAmount(), c.getNumberOfPerson());
        }if (cookies != null) {
        for (Cookie cookie : cookies) {
            if ("cart".equals(cookie.getName())) {
                cookie.setMaxAge(0);
                cookie.setPath("/");              
                break;
            }
        }
    }
        Cookie cartCookie = new Cookie("cart", null);
         // Đảm bảo xóa trên toàn bộ ứng dụng
        response.addCookie(cartCookie);
        
        request.setAttribute("customer", customer);
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<script>alert('Thanh toán thành công!'); window.location='" + request.getContextPath() + "/homepage';</script>");

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
