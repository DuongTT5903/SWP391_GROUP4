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
    private static final long serialVersionUID = 1L;
    private ReservationDBContext reservationDAO = new ReservationDBContext();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");

        try {
            // Lấy các tham số từ request
            String search = request.getParameter("search");
            String status = request.getParameter("status");
            String acceptStatus = request.getParameter("acceptStatus");
            String fromDate = request.getParameter("fromDate");
            String toDate = request.getParameter("toDate");
            
            // Phân trang
            int page = 1;
            int pageSize = 10;
            if (request.getParameter("page") != null) {
                try {
                    page = Integer.parseInt(request.getParameter("page"));
                } catch (NumberFormatException e) {
                    page = 1;
                }
            }

            // Xử lý thay đổi trạng thái (POST request)
            if ("POST".equalsIgnoreCase(request.getMethod())) {
                String reservationIdParam = request.getParameter("reservationId");
                String newStatus = request.getParameter("newStatus");
                String newAcceptStatus = request.getParameter("newAcceptStatus");
                
                if (reservationIdParam != null) {
                    try {
                        int reservationId = Integer.parseInt(reservationIdParam);
                        boolean updateSuccess = false;
                        
                        if (newStatus != null) {
                            // Update reservation status (Active/Inactive)
                            System.out.println("DEBUG - Updating reservation status ID: " + reservationId 
                                + " to status: " + newStatus);
                            updateSuccess = reservationDAO.updateReservationStatus(reservationId, newStatus);
                        } 
                        else if (newAcceptStatus != null) {
                            // Update payment status (AcceptStatus)
                            System.out.println("DEBUG - Updating payment status ID: " + reservationId 
                                + " to acceptStatus: " + newAcceptStatus);
                            updateSuccess = reservationDAO.updateAcceptStatus(reservationId, Integer.parseInt(newAcceptStatus));
                        }
                        
                        System.out.println("DEBUG - Update result: " + updateSuccess);
                        
                        // Redirect với các tham số filter
                        StringBuilder redirectUrl = new StringBuilder(request.getContextPath() + "/staff/reservationlist?page=" + page);
                        if (search != null) redirectUrl.append("&search=").append(search);
                        if (status != null) redirectUrl.append("&status=").append(status);
                        if (acceptStatus != null) redirectUrl.append("&acceptStatus=").append(acceptStatus);
                        if (fromDate != null) redirectUrl.append("&fromDate=").append(fromDate);
                        if (toDate != null) redirectUrl.append("&toDate=").append(toDate);
                        
                        response.sendRedirect(redirectUrl.toString());
                        return;
                        
                    } catch (NumberFormatException e) {
                        System.err.println("ERROR - Invalid reservation ID format: " + reservationIdParam);
                        request.setAttribute("errorMessage", "Invalid reservation ID format");
                    }
                }
            }

            // Lấy danh sách đặt chỗ từ DAO với acceptStatus
            List<Reservation> reservations = reservationDAO.getReservations(
                    (status != null && !status.isEmpty()) ? Integer.parseInt(status) : null,
                    (acceptStatus != null && !acceptStatus.isEmpty()) ? Integer.parseInt(acceptStatus) : null,
                    (fromDate != null && !fromDate.isEmpty()) ? fromDate : null,
                    (toDate != null && !toDate.isEmpty()) ? toDate : null,
                    null, // staffId
                    (search != null && !search.isEmpty()) ? search : null,
                    null, // sortBy
                    page,
                    pageSize
            );

            // Đếm tổng số đặt chỗ để phân trang
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
            request.setAttribute("acceptStatus", acceptStatus);
            request.setAttribute("fromDate", fromDate);
            request.setAttribute("toDate", toDate);

            // Chuyển tiếp đến JSP
            request.getRequestDispatcher("/staff/reservationlist.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/staff/reservationlist.jsp").forward(request, response);
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
        return "Controller for managing reservations list";
    }
}