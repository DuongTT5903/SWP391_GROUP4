/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.FeedbackDBContext;
import dal.ServiceDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Date;
import model.Feedback;
import model.Service;
import model.User;

/**
 *
 * @author yugio
 */
public class FeedbackController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet Feedback</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Feedback at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
     protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy serviceID từ tham số (nếu có)
        String serviceIDParam = request.getParameter("serviceID");
        if (serviceIDParam != null) {
            try {
                int serviceID = Integer.parseInt(serviceIDParam);
                // Lấy thông tin service để hiển thị tên dịch vụ cho người dùng
                ServiceDBContext sdb = new ServiceDBContext();
                Service service = sdb.getServiceByID(serviceID);
                request.setAttribute("service", service);
            } catch (NumberFormatException e) {
                // serviceID không hợp lệ => bỏ qua hoặc báo lỗi
            }
        }
        // Forward sang feedback.jsp
        request.getRequestDispatcher("/view/feedback.jsp").forward(request, response);
    }

    // Xử lý khi người dùng submit form feedback
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy dữ liệu từ form
        String serviceIDParam = request.getParameter("serviceID");
        String detail = request.getParameter("feedbackDetail");
        String ratedParam = request.getParameter("rated");
        String imgLink = request.getParameter("imgLink");

        // Lấy user từ session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        int serviceID = 0;
        int rated = 0;
        try {
            serviceID = Integer.parseInt(serviceIDParam);
            rated = Integer.parseInt(ratedParam);
        } catch (NumberFormatException e) {
            // Xử lý nếu parse lỗi
        }

        // Tạo đối tượng Feedback
        Feedback feedback = new Feedback();
        feedback.setFeedbackDetail(detail);
        feedback.setCustomerID(user.getUserID()); // hoặc CustomerID tuỳ logic
        feedback.setRated(rated);
        feedback.setImgLink(imgLink);
        Service s = new Service();
        s.setServiceID(serviceID);
        feedback.setServices(s);
        feedback.setCreationDate(new Date());
        feedback.setStatus(true);

        // Gọi DBContext để lưu
        FeedbackDBContext fdb = new FeedbackDBContext();
        fdb.addFeedback(feedback);

        // Chuyển hướng sau khi gửi feedback
        // Có thể về trang chi tiết dịch vụ / trang cảm ơn
        response.sendRedirect(request.getContextPath() + "/homepage");
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
