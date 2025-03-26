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

@WebServlet(name = "MyReservationController", urlPatterns = {"/customer/myReservation"})
public class MyReservationController extends HttpServlet {

    private ReservationDBContext reservationDAO = new ReservationDBContext();

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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy các tham số từ request
            String search = request.getParameter("search");
            String status = request.getParameter("status");
            String acceptStatus = request.getParameter("acceptStatus"); // Thêm acceptStatus
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
                page = 1;
            }

            // Lấy danh sách đặt chỗ từ DAO (đã cập nhật thêm acceptStatus)
            List<Reservation> reservations = reservationDAO.getReservations(
                    (status != null && !status.isEmpty()) ? Integer.parseInt(status) : null,
                    (acceptStatus != null && !acceptStatus.isEmpty()) ? Integer.parseInt(acceptStatus) : null,
                    (fromDate != null && !fromDate.isEmpty()) ? fromDate : null,
                    (toDate != null && !toDate.isEmpty()) ? toDate : null,
                    null, // staffId
                    (search != null && !search.isEmpty()) ? search : null,
                    (sortBy != null && !sortBy.isEmpty()) ? sortBy : null,
                    page,
                    pageSize
            );

            // Đếm tổng số đặt chỗ (đã cập nhật thêm acceptStatus)
            int totalReservations = reservationDAO.countReservations(
                    (status != null && !status.isEmpty()) ? Integer.parseInt(status) : null,
                    (acceptStatus != null && !acceptStatus.isEmpty()) ? Integer.parseInt(acceptStatus) : null,
                    (fromDate != null && !fromDate.isEmpty()) ? fromDate : null,
                    (toDate != null && !toDate.isEmpty()) ? toDate : null,
                    null, // staffId
                    (search != null && !search.isEmpty()) ? search : null
            );
            
            int totalPages = (int) Math.ceil((double) totalReservations / pageSize);

            // Đặt dữ liệu vào request attribute
            request.setAttribute("reservations", reservations);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("search", search);
            request.setAttribute("status", status);
            request.setAttribute("acceptStatus", acceptStatus); // Thêm acceptStatus
            request.setAttribute("fromDate", fromDate);
            request.setAttribute("toDate", toDate);
            request.setAttribute("sortBy", sortBy);

            // Chuyển tiếp đến JSP
            request.getRequestDispatcher("/customer/myReservation.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}