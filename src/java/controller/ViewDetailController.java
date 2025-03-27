/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.FeedbackDBContext;
import dal.ServiceDBContext;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Feedback;
import model.ImgDetail;
import model.Service;
import model.User;

/**
 *
 * @author LOQ
 */
@WebServlet(name = "ViewDetailController", urlPatterns = {"/viewDetailProduct"})
public class ViewDetailController extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        String serviceIDStr = request.getParameter("id");
        try {
            int serviceID = Integer.parseInt(serviceIDStr);

            ServiceDBContext dao = new ServiceDBContext();
            
            FeedbackDBContext feedDao = new FeedbackDBContext();
            Service service = dao.getServiceByID(serviceID);
            
//            User user = dao.get6t
            
            List<ImgDetail> imgs = dao.listImgByServiceId(serviceID); 
            
            int countFeedBack = feedDao.getCountFeedbackByServiceID(serviceID);
            
            List<Feedback> feedBack = feedDao.getFeedbacksByServiceID1(serviceID);
            int getTotalAmountByServiceID=feedDao.getTotalAmountByServiceID(serviceID);
            double getAverageRatingByServiceID=feedDao.getAverageRatingByServiceID(serviceID);
            
                          

            request.setAttribute("service", service);
            request.setAttribute("imgList", imgs);
            request.setAttribute("count", countFeedBack);
            request.setAttribute("feedBack", feedBack);
            request.setAttribute("total", getTotalAmountByServiceID);
            request.setAttribute("totalRated", getAverageRatingByServiceID);
            
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/view/viewDetailProduct.jsp");
            dispatcher.forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid service ID");
        }
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
 @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
    try {
        // Lấy dữ liệu từ form
        int serviceID = Integer.parseInt(request.getParameter("id"));
        int rated = Integer.parseInt(request.getParameter("rated"));
        String feedbackDetail = request.getParameter("feedbackDetail");

        // Lấy user từ session (người dùng phải đăng nhập mới được đánh giá)
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

//        if (currentUser == null) {
//            response.sendRedirect("/login"); // Nếu chưa đăng nhập, chuyển hướng đến trang login
//            return;
//        }
        FeedbackDBContext feedbackDB = new FeedbackDBContext();
        ServiceDBContext serviceDBContext = new ServiceDBContext();
        Service services = serviceDBContext.getServiceById(serviceID);

        // Tạo đối tượng Feedback
        Feedback feedback = new Feedback(feedbackDetail, rated, services, currentUser);
        
        // Gọi DBContext để lưu vào database
        feedbackDB.addFeedback(feedback);

        // Chuyển hướng về trang chi tiết sản phẩm sau khi đánh giá xong
        response.sendRedirect("viewDetailProduct?id=" + serviceID);

    } catch (NumberFormatException e) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input data");
    }
}

    // </editor-fold>

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    // </editor-fold>
}
