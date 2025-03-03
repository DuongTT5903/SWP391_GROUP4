/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ServiceDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author trung
 */
public class AddCart extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddCart</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddCart at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String serviceID = request.getParameter("serviceID");

    if (serviceID == null || serviceID.trim().isEmpty()) {
        response.sendRedirect(request.getHeader("Referer"));
        return;
    }

    // Get cart data from cookies
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

    // Update quantity if the service is already in the cart
    String newCartData = new String();
    boolean found = false;
    for (String item : cartData.split("-")) {
        if (item.isEmpty()) continue;

        String[] parts = item.split("/");
        if (parts.length < 2) continue;
        String[] info=parts[0].split("~");
        String existingServiceID = info[1];
        int quantity = Integer.parseInt(info[2]);
        int numberOfPeople = Integer.parseInt(parts[1]);

        if (existingServiceID.equals(serviceID)) {
            quantity += 1; // Increment quantity
            found = true;
        }
        newCartData += "~"+existingServiceID+"~"+quantity +"/"+numberOfPeople+"-";
    }

    // If service is not in the cart, add it with default values
    if (!found) {
        newCartData+="~"+serviceID+"~"+"1/1";
    }

    // Set new cart cookie
    Cookie cartCookie = new Cookie("cart", newCartData);
    cartCookie.setMaxAge(60 * 60 * 24);
    
    response.addCookie(cartCookie);

    // Redirect back
    String referer = request.getHeader("Referer");
    if (referer != null) {
        response.sendRedirect(referer);
    } else {
        response.sendRedirect(request.getContextPath() + "/serviceList");
    }
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
