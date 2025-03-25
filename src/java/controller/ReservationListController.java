package controller;

import dal.ReservationDBContext;
import model.Reservation;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ReservationListController", urlPatterns = {"/staff/reservationlist"})
public class ReservationListController extends HttpServlet {

    private ReservationDBContext reservationDAO = new ReservationDBContext();

    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    try {
        // Lấy các tham số từ request
        String search = request.getParameter("search");
        String status = request.getParameter("status");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String sortBy = request.getParameter("sortBy");

        // Phân trang
        int page = 1;
        int pageSize = 10;
        try {
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }
        } catch (NumberFormatException e) {
            page = 1; // Mặc định về trang 1 nếu có lỗi
        }

        // Lấy danh sách đặt chỗ từ DAO
        List<Reservation> reservations = reservationDAO.getReservations(
                (status != null && !status.isEmpty()) ? Integer.parseInt(status) : null, // Bỏ qua nếu status rỗng
                (fromDate != null && !fromDate.isEmpty()) ? fromDate : null, // Bỏ qua nếu fromDate rỗng
                (toDate != null && !toDate.isEmpty()) ? toDate : null, // Bỏ qua nếu toDate rỗng
                null, // staffId (nếu có)
                (search != null && !search.isEmpty()) ? search : null, // Bỏ qua nếu search rỗng
                sortBy,
                page,
                pageSize
        );

        // Đếm tổng số đặt chỗ để phân trang
        int totalReservations = reservationDAO.countReservations(
                (status != null && !status.isEmpty()) ? Integer.parseInt(status) : null,
                (fromDate != null && !fromDate.isEmpty()) ? fromDate : null,
                (toDate != null && !toDate.isEmpty()) ? toDate : null,
                null, // staffId (nếu có)
                (search != null && !search.isEmpty()) ? search : null
        );
        int totalPages = (int) Math.ceil((double) totalReservations / pageSize);

        // Đặt dữ liệu vào request attribute
        request.setAttribute("reservations", reservations);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("search", search);
        request.setAttribute("status", status);
        request.setAttribute("fromDate", fromDate);
        request.setAttribute("toDate", toDate);
        request.setAttribute("sortBy", sortBy);

        // Chuyển tiếp đến JSP
        request.getRequestDispatcher("/staff/reservationlist.jsp").forward(request, response);
    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
        request.getRequestDispatcher("/staff/reservationlist.jsp").forward(request, response);
    }
}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Xử lý các thao tác POST (nếu có)
            String action = request.getParameter("action");
            String reservationIdParam = request.getParameter("reservationId");
            String currentPage = request.getParameter("page");

            if (action != null && reservationIdParam != null && !reservationIdParam.isEmpty()) {
                int reservationId = Integer.parseInt(reservationIdParam);
                if ("approve".equalsIgnoreCase(action)) {
                    reservationDAO.updateReservationStatus(reservationId, "Approved");
                } else if ("reject".equalsIgnoreCase(action)) {
                    reservationDAO.updateReservationStatus(reservationId, "Rejected");
                }
                // Chuyển hướng lại để tránh lặp lại thao tác khi refresh trang
                response.sendRedirect(request.getContextPath() + "/staff/reservationlist?page=" + (currentPage != null ? currentPage : "1"));
                return;
            }

            // Nếu không có action, chuyển hướng về trang danh sách
            response.sendRedirect(request.getContextPath() + "/staff/reservationlist");
        } catch (Exception e) {
            e.printStackTrace();
            // Hiển thị thông báo lỗi trên trang hiện tại
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/staff/reservationlist.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Controller for managing reservations list";
    }
}