/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dal.SliderDBContext;
import java.io.IOException;
import model.Slider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;

public class ManagerSliderDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy slideID từ request
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            // Không có id, chuyển hướng về danh sách slider
            response.sendRedirect(request.getContextPath() + "/manager/sliderList");
            return;
        }

        int sliderId = Integer.parseInt(idParam);
        SliderDBContext sliderDAO = new SliderDBContext();
        Slider slider = sliderDAO.getSliderById(sliderId);

        if (slider != null) {
            request.setAttribute("slider", slider);
            // Chuyển sang trang JSP để hiển thị chi tiết
            request.getRequestDispatcher("sliderDetail.jsp").forward(request, response);
        } else {
            // Không tìm thấy slider, quay về danh sách
            response.sendRedirect(request.getContextPath() + "/manager/sliderList");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // Lấy dữ liệu từ form
        int sliderId = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String backLink = request.getParameter("backLink");
        String img = request.getParameter("img");
        // Checkbox status: nếu người dùng tick -> true, không tick -> null
        boolean status = (request.getParameter("status") != null);
//        String notes = request.getParameter("notes"); // nếu có

        // Tạo đối tượng Slider mới (hoặc gán giá trị cho đối tượng cũ)
        Slider slider = new Slider();
        slider.setSlideID(sliderId);
        slider.setTitle(title);
        slider.setBackLink(backLink);
        slider.setImg(img);
        slider.setStatus(status);
//        slider.setNotes(notes); // nếu có cột notes

        // Cập nhật DB
        SliderDBContext sliderDAO = new SliderDBContext();
        sliderDAO.updateSlider(slider);

        // Quay về danh sách slider
        response.sendRedirect(request.getContextPath() + "/manager/sliderList");
    }

    @Override
    public String getServletInfo() {
        return "Controller for managing slider details";
    }
}
