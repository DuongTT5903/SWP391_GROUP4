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
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import model.Customer;
import model.ReservationDetail;
import model.Service;
import model.User;

/**
 *
 * @author trung
 */
public class ReservationController extends HttpServlet {

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

    // Kiểm tra categoryID, tránh lỗi khi parse số
    int categoryID = 0;
    String categoryParam = request.getParameter("category");
    if (categoryParam != null && !categoryParam.trim().isEmpty()) {
        try {
            categoryID = Integer.parseInt(categoryParam);
        } catch (NumberFormatException e) {
            categoryID = 0; // Nếu lỗi, đặt mặc định là 0
        }
    }

    // Kiểm tra số trang, tránh lỗi khi parse số
    int currentPage = 1;
    int recordsPerPage = 6;
    String pageParam = request.getParameter("page");
    if (pageParam != null && !pageParam.trim().isEmpty()) {
        try {
            currentPage = Integer.parseInt(pageParam);
        } catch (NumberFormatException e) {
            currentPage = 1; // Nếu lỗi, đặt mặc định là trang 1
        }
    }

    // Lấy dữ liệu từ cookie giỏ hàng
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

    // Nếu giỏ hàng trống, hiển thị thông báo
    if (cartData.isEmpty()) {
        request.setAttribute("cartItems", new ArrayList<>());
        request.setAttribute("cartMessage", "Giỏ hàng của bạn đang trống.");
        request.getRequestDispatcher("view/reservation.jsp").forward(request, response);
        return;
    }

    List<ReservationDetail> cartItems ;
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
            if (info.length < 3) {
                continue;
            }

            // Kiểm tra và parse serviceID, amount, numberOfPeople
            int serviceID = Integer.parseInt(info[1]);
            int amount = Integer.parseInt(info[2]);
            int numberOfPeople = Integer.parseInt(parts[1]);

            Service service = serviceDAO.getServiceByID(serviceID);
            if (service != null) {
                ReservationDetail reservationDetail = new ReservationDetail(0, 0, service, amount, numberOfPeople);
                cart.add(reservationDetail);
                total += service.getServicePrice() * (100 - service.getSalePrice()) * 10 * amount * numberOfPeople;
            }
        } catch (NumberFormatException e) {
            System.err.println("Lỗi parse dữ liệu giỏ hàng: " + item);
        }
    }

    // Lọc dịch vụ dựa trên tìm kiếm
    cartItems = reservationDB.searchService(cart, search, categoryID, currentPage, recordsPerPage);
    if (cartItems.isEmpty()) {
        request.setAttribute("cartMessage", "Không tìm thấy kết quả.");
    }

    int totalRecords = cart.size();
    int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);

    // Gửi dữ liệu qua JSP
    request.setAttribute("currentPage", currentPage);
    request.setAttribute("totalPages", totalPages);
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
        ReservationDBContext reservationDB = new ReservationDBContext();
        Customer customer;
        if (user == null) {
            customer = null;
        } else {
            customer = reservationDB.getCustomerByID(user.getUserID());
        }
        Map<String, String> errors = new HashMap<>();
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String gender = request.getParameter("gender");
        String note = request.getParameter("note");
        String payment = request.getParameter("payment");

        // Kiểm tra lỗi nhập liệu
        if (name == null || name.length() < 6 || name.length() > 50) {
            errors.put("name", "Họ và tên phải từ 6 đến 50 ký tự.");
        }

        if (address == null || address.length() < 40 || address.length() > 200) {
            errors.put("address", "Địa chỉ phải từ 40 đến 200 ký tự.");
        }

        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        if (email == null || !email.matches(emailPattern)) {
            errors.put("email", "Email không hợp lệ. Vui lòng nhập email đúng định dạng (ví dụ: example@email.com).");
        }

        if (phone == null || !phone.matches("^0[3789][0-9]{8}$")) {
            errors.put("phone", "Số điện thoại phải bắt đầu bằng 03, 07, 08 hoặc 09 và có tổng cộng 10 chữ số.");
        }
        if (note != null && note.length() > 500) {
            errors.put("note", "Ghi chú không được vượt quá 500 ký tự.");
        }
        if (payment == null) {
            errors.put("payment", "Vui lòng chọn phương thức thanh toán.");
        } else if ("0".equals(payment)) { // Nếu chọn thẻ tín dụng
            String cardName = request.getParameter("cardName");
            String cardNumber = request.getParameter("cardNumber");
            String CVV = request.getParameter("CVV");
            String expirationDate = request.getParameter("expirationDate");

            if (cardName == null || cardName.trim().length() < 6) {
                errors.put("cardName", "Tên chủ thẻ phải có ít nhất 6 ký tự.");
            }

            if (cardNumber == null || !cardNumber.matches("\\d{13,19}")) {
                errors.put("cardNumber", "Số thẻ phải có từ 13 đến 19 chữ số.");
            }

            if (CVV == null || !CVV.matches("\\d{3,4}")) {
                errors.put("CVV", "CVV phải có 3-4 chữ số.");
            }

            if (expirationDate == null || expirationDate.trim().isEmpty()) {
                errors.put("expirationDate", "Vui lòng nhập ngày hết hạn.");
            }
            session.setAttribute("cardName", cardName);
            session.setAttribute("cardNumber", cardNumber);
            session.setAttribute("CVV", CVV);
            request.setAttribute("expirationDate", expirationDate);
        }
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("view/reservation.jsp").forward(request, response);
            return;
        }
        if (session.getAttribute("startTime") == null) {
            session.setAttribute("startTime", Instant.now().getEpochSecond()); // Lưu timestamp hiện tại
        }
        request.setAttribute("customer", customer);
        session.setAttribute("name", name);
        session.setAttribute("address", address);
        session.setAttribute("email", email);
        session.setAttribute("phone", phone);
        session.setAttribute("gender", gender);
        session.setAttribute("note", note);
        response.setContentType("text/html;charset=UTF-8");
        response.sendRedirect(request.getContextPath() + "/reservation_complete");

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
