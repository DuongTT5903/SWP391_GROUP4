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
import java.util.List;
import model.Service;
import model.ServiceCategory;

/**
 *
 * @author yugio
 */
public class sampleSearchServiceController extends HttpServlet {

    private ServiceDBContext serviceDB;

    @Override
    public void init() throws ServletException {
        serviceDB = new ServiceDBContext();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy từ khóa tìm kiếm
        String query = request.getParameter("q");
        // Gọi searchServices1(...) hoặc searchServices(...) tuỳ bạn
        // categoryID = 0 -> tìm tất cả danh mục, page=1, pageSize=10 (tuỳ)
        List<Service> services = serviceDB.searchServices1(query, 0, 1, 10);

        // Lấy luôn danh mục để hiển thị sidebar
        List<ServiceCategory> categories = serviceDB.getServiceCategories();

        // Đưa kết quả vào request
        request.setAttribute("services", services);
        request.setAttribute("categories", categories);

        // Forward đến feedback.jsp (cùng trang hiển thị form Feedback)
        request.getRequestDispatcher("/view/samplefeedback.jsp").forward(request, response);
    }
}

