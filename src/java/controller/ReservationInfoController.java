package controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import dal.ReservationDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Reservation;
import model.ReservationDetail;

/**
 *
 * @author trung
 */
@WebServlet(name = "ReservationInfoController", urlPatterns = {"/customer/reservationInfo"})
public class ReservationInfoController extends HttpServlet {

 private final ReservationDBContext reservationDB = new ReservationDBContext();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int reservationID = Integer.parseInt(request.getParameter("reservationID"));

        // Lấy thông tin đặt chỗ từ ReservationDBContext
        ReservationDBContext dbContext = new ReservationDBContext();
        Reservation reservation = dbContext.getReservationById(reservationID);
        List<ReservationDetail> reservationDetails = dbContext.getReservationDetails(reservationID);

        // Lấy vai trò của người dùng hiện tại từ session
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role"); // Giả sử role được lưu trong session

        // Đặt thông tin vào request để truyền sang JSP
        request.setAttribute("reservation", reservation);
        request.setAttribute("reservationDetails", reservationDetails);
        request.setAttribute("userRole", role); // Truyền vai trò của người dùng sang JSP
        request.getRequestDispatcher("/customer/reservationInfo.jsp").forward(request, response);
    }

}
