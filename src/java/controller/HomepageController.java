/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.BlogDBContext;
import dal.ServiceDBContext;
import dal.SliderDBContext;
import model.Blog;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.PageContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.List;
import model.Service;
import model.Slider;

/**
 *
 * @author admin
 *
 * thay đỏi blog slider thành phần slider và để blog làm hot post theo thanh
 * trượt ngang ở bên trái với giới hạn là 5 và có yêu cầu status là 1.
 *
 *
 */
@WebServlet(name = "HomepageController", urlPatterns = {"/homepage"})
public class HomepageController extends HttpServlet {

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
            out.println("<title>Servlet HomepageController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HomepageController at " + request.getContextPath() + "</h1>");
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
        BlogDBContext blogDB = new BlogDBContext();
        List<Blog> blogs = null;

        try {
            blogs = blogDB.getAllBlogs();
            if (blogs == null || blogs.isEmpty()) {
                request.setAttribute("errorMessage", "No blogs available at the moment.");
            } else {
                request.setAttribute("blogs", blogs);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while fetching blogs.");
        }

        List<Service> services = null;
        ServiceDBContext serviceDB = new ServiceDBContext();
        try {
            services = serviceDB.getServicesHomepage();
            if (services == null || services.isEmpty()) {
                request.setAttribute("errorMessage", "No services available at the moment.");
            } else {
                request.setAttribute("services", services);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while fetching services.");
        }
        SliderDBContext sliderDB = new SliderDBContext();
        List<Slider> sliders = null;
        try {
            sliders = sliderDB.getActiveSliders();
            if (sliders == null || sliders.isEmpty()) {
                request.setAttribute("errorMessage", "No slider available at the moment.");
            } else {
                request.setAttribute("sliders", sliders);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while fetching sliders.");
        }
        HttpSession session = request.getSession();
        // Nếu chưa có thời gian truy cập, lưu thời gian bắt đầu
        if (session.getAttribute("startTime") == null) {
            session.setAttribute("startTime", Instant.now().getEpochSecond()); // Lưu timestamp hiện tại
        }
        request.getRequestDispatcher("view/homepage.jsp").forward(request, response);
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
        ServiceDBContext serviceDB = new ServiceDBContext();
        List<Service> services = serviceDB.getServicesHomepage();

        request.setAttribute("services", services);
        request.getRequestDispatcher("view/homepage.jsp").forward(request, response);
    }

    /**
     *
     * @return
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
