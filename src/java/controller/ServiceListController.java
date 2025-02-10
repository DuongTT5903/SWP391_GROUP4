/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ServiceDBContext;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Service;

/**
 *
 * @author admin
 */
public class ServiceListController extends HttpServlet {
 private static final int PAGE_SIZE = 6; 
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
            out.println("<title>Servlet StaffServiceController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StaffServiceController at " + request.getContextPath() + "</h1>");
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
         String search = request.getParameter("search") != null ? request.getParameter("search") : "";
        int categoryID = request.getParameter("category") != null ? Integer.parseInt(request.getParameter("category")) : 0;
        int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

        ServiceDBContext serviceDAO = new ServiceDBContext();
        List<Service> services = serviceDAO.getServices(search, categoryID, page, PAGE_SIZE);

        request.setAttribute("services", services);
        request.setAttribute("currentPage", page);
        request.setAttribute("search", search);
        request.setAttribute("categoryID", categoryID);

        request.getRequestDispatcher("/view/serviceList.jsp").forward(request, response);
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
    // Default behavior: Load service list
       ServiceDBContext s=new ServiceDBContext();
    List<Service> services = s.getServices(); // Fetch services from DAO
    request.setAttribute("services", services); // Set it in the request scope
   request.getRequestDispatcher("/view/serviceList.jsp").forward(request, response);
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
