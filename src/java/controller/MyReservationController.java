/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ReservationDBContext;
import model.Reservation;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import model.User;

/**
 *
 * @author trung
 */@WebServlet(name = "MyReservationController", urlPatterns = {"/customer/myReservation"})
public class MyReservationController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
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
                    status != null ? Integer.parseInt(status) : null,
                    fromDate,
                    toDate,
                    null,
                    user.getUserID(),// staffId (nếu có)
                    search,
                    sortBy,
                    page,
                    pageSize
            );

            // Đếm tổng số đặt chỗ để phân trang
            int totalReservations = reservationDAO.countReservations(
                    status != null ? Integer.parseInt(status) : null,
                    fromDate,
                    toDate,
                    null,
                    user.getUserID(),// staffId (nếu có)
                    search
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
            request.getRequestDispatcher("/customer/myReservation.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
