package controller;

import dal.ReservationDBContext;

import java.io.IOException;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class AdminDashboardController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy dữ liệu trạng thái đặt chỗ
        ReservationDBContext reservationDB = new ReservationDBContext();
        Map<Integer, Integer> statusCounts = reservationDB.getReservationStatusCount();

        // Lấy dữ liệu đánh giá dịch vụ
        Map<String, Double> serviceRatings = reservationDB.getServiceRatings();

        // Lấy dữ liệu số lượng đặt chỗ trong 7 ngày qua
        Map<String, Integer> reservationTrends = reservationDB.getReservationsLast7Days();

        // Lấy số lượt đặt chỗ mới
        int newReservations = reservationDB.getNewReservationsCount();

        // Lấy dữ liệu doanh thu theo danh mục
    ;
        Map<String, Double> revenueByCategory = reservationDB.getRevenueByCategory();

        // Đẩy dữ liệu lên request
        request.setAttribute("statusCounts", statusCounts);
        request.setAttribute("serviceRatings", serviceRatings);
        request.setAttribute("reservationTrends", reservationTrends);
        request.setAttribute("newReservations", newReservations);
        request.setAttribute("revenueByCategory", revenueByCategory);

        // Chuyển dữ liệu sang JSP
        request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
    }
}
