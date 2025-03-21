package controller;

import dal.ReservationDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Reservation;
import model.ReservationDetail;

@WebServlet(name = "ReservationDetailController", urlPatterns = {"/staff/reservationdetail"})
public class ReservationDetailController extends HttpServlet {
    private final ReservationDBContext reservationDB = new ReservationDBContext();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String reservationIDParam = request.getParameter("reservationID");

        if (reservationIDParam == null || reservationIDParam.trim().isEmpty()) {
            request.setAttribute("error", "ReservationID cannot be empty!");
        } else {
            try {
                int reservationID = Integer.parseInt(reservationIDParam);
                Reservation reservation = reservationDB.getReservationById(reservationID);

                if (reservation == null) {
                    request.setAttribute("error", "Reservation not found!");
                } else {
                    // Lấy danh sách chi tiết đặt chỗ từ cơ sở dữ liệu
                    List<ReservationDetail> details = reservationDB.getReservationDetails(reservationID);
                    reservation.setDetails(details); // Gán danh sách chi tiết vào đối tượng Reservation

                    request.setAttribute("reservation", reservation);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid ReservationID!");
            }
        }

        request.getRequestDispatcher("/staff/reservationdetail.jsp").forward(request, response);
    }
}