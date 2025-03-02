/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ServiceDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Service;

/**
 *
 * @author LOQ
 */
public class ManagerServiceController extends HttpServlet {

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
            out.println("<title>Servlet ManagerServiceController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManagerServiceController at " + request.getContextPath() + "</h1>");
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
        ServiceDBContext s = new ServiceDBContext();
        String service = request.getParameter("service");

        if (service == null) {
            service = "listservice";
        }

        // Lấy danh sách dịch vụ
        if (service.equalsIgnoreCase("listservice")) {
            List<Service> list = s.getServices();
            request.setAttribute("list", list);
            request.getRequestDispatcher("/manager/view/managerlistservice.jsp").forward(request, response);
        }

        // Xử lý cập nhật status
        if (service.equalsIgnoreCase("editStatus")) {
            try {
                // Lấy tham số từ request
                int serviceID = Integer.parseInt(request.getParameter("serviceID"));
                boolean newStatus = Boolean.parseBoolean(request.getParameter("editStatus"));

                // Cập nhật trạng thái trong DB
                boolean success = s.updateServiceStatus(serviceID, newStatus);

                List<Service> list = s.getServices();
                request.setAttribute("list", list);
                request.getRequestDispatcher("/manager/view/managerlistservice.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                // Xử lý lỗi nếu tham số không hợp lệ
                response.getWriter().println("Invalid serviceID or status format!");
            }
        }

        if (service.equalsIgnoreCase("searchById")) {
            String id_raw = request.getParameter("searchID");
            int id = Integer.parseInt(id_raw);
            List<Service> list = new ArrayList<>();
            list = s.getListServiceByID(id);
            request.setAttribute("searchID", id);
            request.setAttribute("list", list);
            request.getRequestDispatcher("/manager/view/managerlistservice.jsp").forward(request, response);
        }

        if (service.equalsIgnoreCase("editRequest")) {
            String id_raw = request.getParameter("serviceID");
            int id = Integer.parseInt(id_raw);
            Service serviceObj = s.getServiceByID(id);
            request.setAttribute("service", serviceObj);
            request.getRequestDispatcher("/manager/view/editservice.jsp").forward(request, response);
        }
        
         if (service.equalsIgnoreCase("addRequest")) {
            request.getRequestDispatcher("/manager/view/addservice.jsp").forward(request, response);
        }

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
