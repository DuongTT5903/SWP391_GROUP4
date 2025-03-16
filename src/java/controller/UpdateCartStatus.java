/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ReservationDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author trung
 */
public class UpdateCartStatus extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userID = Integer.parseInt(request.getParameter("userID"));
        int serviceID = Integer.parseInt(request.getParameter("serviceID"));
        boolean status = request.getParameter("checkService") != null; // Checkbox chỉ gửi nếu checked

        ReservationDBContext  reservationDB = new  ReservationDBContext();
        reservationDB.updateStatus(userID, status, serviceID);

        response.sendRedirect(request.getHeader("Referer")); // Quay lại trang trước
    }
}