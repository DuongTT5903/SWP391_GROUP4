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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import model.Feedback;
import model.ImgDetail;
import model.Service;

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
            
            List<Feedback> feedBack = feedDao.getFeedbackByServiceID(serviceID);
            
            
                          

            request.setAttribute("service", service);
            request.setAttribute("imgList", imgs);
            request.setAttribute("count", countFeedBack);
            request.setAttribute("feedBack", feedBack);
            
            
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
        processRequest(request, response);
    }
    // </editor-fold>

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    // </editor-fold>
}
