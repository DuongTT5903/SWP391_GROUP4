/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author trung
 */
public class UpdateCart extends HttpServlet {

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
            out.println("<title>Servlet UpdateCart</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateCart at " + request.getContextPath() + "</h1>");
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
        String updateID = request.getParameter("UpdateID");
        String update = request.getParameter("update");
        // Kiểm tra nếu deleteID null hoặc rỗng thì bỏ qua xử lý
        if (updateID == null || updateID.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/shoppingCart");
            return;
        }

        String cartData = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("cart")) {
                    cartData = cookie.getValue();
                    break;
                }
            }
        }
        String newCartData = new String();
        for (String item : cartData.split("-")) {
            if (item.isEmpty()) {
                continue;
            }

            String[] parts = item.split("/");
            if (parts.length < 2) {
                continue;
            }
            String[] info = parts[0].split("~");
            String existingServiceID = info[1];
            int quantity = Integer.parseInt(info[2]);
            int numberOfPeople = Integer.parseInt(parts[1]);

            if (existingServiceID.equals(updateID)) {
                if (update.equals("increase")) {
                    quantity += 1;
                } else if (update.equals("decrease")) {
                     if(quantity!=1)
                    {
                   quantity -= 1;
                    }
                } else if (update.equals("increasePersons")) {
                    numberOfPeople += 1;
                } else if (update.equals("decreasePersons")) {
                    if(numberOfPeople!=1)
                    {
                    numberOfPeople -= 1;
                    }
                }
            }
            newCartData += "~" + existingServiceID + "~" + quantity + "/" + numberOfPeople + "-";

        }
        // Nếu giỏ hàng trống, chuyển hướng luôn
        if (cartData.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/shoppingCart");
            return;
        }

        ArrayList<String> cartItems = new ArrayList<>(Arrays.asList(cartData.split("-")));
        Cookie cartCookie = new Cookie("cart", newCartData);
        cartCookie.setMaxAge(cartItems.isEmpty() ? 0 : 60 * 60 * 24);
        response.addCookie(cartCookie);

        // Chuyển hướng về trang giỏ hàng
        response.sendRedirect(request.getContextPath() + "/shoppingCart");
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
