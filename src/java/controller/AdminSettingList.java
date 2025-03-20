/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.SettingDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Role;

/**
 *
 * @author admin
 */
public class AdminSettingList extends HttpServlet {

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
            out.println("<title>Servlet AdminSettingList</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminSettingList at " + request.getContextPath() + "</h1>");
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
    SettingDBContext db = new SettingDBContext();

    // Kiểm tra xem có tham số roleID và status không
    String roleIdParam = request.getParameter("roleID");
    String statusParam = request.getParameter("status");

    if (roleIdParam != null && statusParam != null) {
        try {
            int roleID = Integer.parseInt(roleIdParam);
            boolean status = Boolean.parseBoolean(statusParam);
            db.updateRoleStatus(roleID, status); // Cập nhật trạng thái
        } catch (NumberFormatException e) {
            e.printStackTrace(); // Log lỗi nếu có
        }
    }

    // Sau khi cập nhật, lấy danh sách mới và forward về JSP
    List<Role> roles = db.getRolesWithStatus();
    request.setAttribute("roles", roles);
    request.getRequestDispatcher("/admin/roles.jsp").forward(request, response);
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
    SettingDBContext db = new SettingDBContext();

    // Lấy roleID và currentStatus từ request
    String roleIdParam = request.getParameter("roleID");
    String currentStatusParam = request.getParameter("currentStatus");

    if (roleIdParam != null && currentStatusParam != null) {
        try {
            int roleID = Integer.parseInt(roleIdParam);
            boolean currentStatus = Boolean.parseBoolean(currentStatusParam);
            boolean newStatus = !currentStatus; // Đảo trạng thái

            db.updateRoleStatus(roleID, newStatus); // Cập nhật trạng thái
        } catch (NumberFormatException e) {
            e.printStackTrace(); // Log lỗi nếu có
        }
    }

    // Sau khi cập nhật, tải lại danh sách role
    List<Role> roles = db.getRolesWithStatus();
    request.setAttribute("roles", roles);
    request.getRequestDispatcher("/admin/roles.jsp").forward(request, response);
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
