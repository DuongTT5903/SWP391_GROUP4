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
import java.time.Instant;
import java.util.List;
import model.Service;

/**
 *
 * @author admin
 */
public class ServiceListController extends HttpServlet {

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
        int currentPage = 1;
        int recordsPerPage = 6; // Số bản ghi trên mỗi trang

        // Lấy số trang từ request (nếu có)
        if (request.getParameter("page") != null) {
            currentPage = Integer.parseInt(request.getParameter("page"));
        }

        String search = request.getParameter("search") != null ? request.getParameter("search") : "";
        String categoryStr = request.getParameter("category");

        int categoryID = 0; // Giá trị mặc định

        if (categoryStr != null && !categoryStr.trim().isEmpty()) {
            try {
                categoryID = Integer.parseInt(categoryStr);
            } catch (NumberFormatException e) {
                System.err.println("Lỗi: category không hợp lệ - " + categoryStr);
            }
        }

        String t = request.getParameter("sort");
        String sort;
        String type;
        if (t == null) {
            sort = "ASC";
            type = "serviceid";
        } else {
            switch (t) {
                case "servicename1" -> {
                    sort = "ASC";
                    type = "servicename";
                }
                case "servicename2" -> {
                    sort = "DESC";
                    type = "servicename";
                }
                case "price1" -> {
                    sort = "ASC";
                    type = "serviceprice";
                }
                case "price2" -> {
                    sort = "DESC";
                    type = "serviceprice";
                }
                case "saleprice1" -> {
                    sort = "ASC";
                    type = "saleprice";
                }
                case "saleprice2" -> {
                    sort = "DESC";
                    type = "saleprice";
                }
                default -> {
                    sort = "ASC";
                    type = "serviceid";
                }
            }
        }
        ServiceDBContext serviceDAO = new ServiceDBContext();

        List<Service> services = serviceDAO.getServices(search, categoryID, currentPage, recordsPerPage, type, sort);
        int totalRecords = services.size(); // Hàm lấy tổng số bản ghi từ DB
        int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);  
        // Nếu chưa có thời gian truy cập, lưu thời gian bắt đầu
       
        // Gửi dữ liệu qua JSP
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("services", services);
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
        processRequest(request, response);
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
