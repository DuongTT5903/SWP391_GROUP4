package controller;

import dal.MedicalExaminationDBContext;
import dal.ServiceDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.MedicalExamination;
import model.Service;


public class MedicalExaminationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        MedicalExaminationDBContext db = new MedicalExaminationDBContext();
        ServiceDBContext serviceDB = new ServiceDBContext();

        if (path.equals("/staff/examination/list")) {
            // Handle filter
            String dateParam = request.getParameter("date");
            String serviceIdParam = request.getParameter("serviceId");
            String medicineName = request.getParameter("medicineName");

            List<MedicalExamination> examinations;
            if (dateParam != null && !dateParam.isEmpty()) {
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateParam);
                    examinations = db.getExaminationsByDate(date);
                } catch (ParseException e) {
                    examinations = db.getAllExaminations();
                }
            } else if (serviceIdParam != null && !serviceIdParam.isEmpty()) {
                examinations = db.getExaminationsByService(Integer.parseInt(serviceIdParam));
            } else if (medicineName != null && !medicineName.isEmpty()) {
                examinations = db.getExaminationsByMedicineName(medicineName);
            } else {
                examinations = db.getAllExaminations();
            }

            List<Service> services = serviceDB.getActiveMedicalServices();

            request.setAttribute("examinations", examinations);
            request.setAttribute("services", services);
            request.getRequestDispatcher("/staff/examinationlist.jsp").forward(request, response);

        } else if (path.equals("/staff/examination/detail")) {
            int id = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("examination", db.getExaminationById(id));
            request.getRequestDispatcher("/staff/examinationdetail.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        
        if (path.equals("/staff/examination/edit")) {
            handleEditPrescription(request, response);
        }
    }

    private void handleEditPrescription(HttpServletRequest request, HttpServletResponse response) 
        throws IOException {
    response.setContentType("application/json");
    PrintWriter out = response.getWriter();
    
    try {
        int id = Integer.parseInt(request.getParameter("id"));
        String prescription = request.getParameter("prescription");
        
        // Lấy thông tin examination hiện có từ database
        MedicalExamination exam = new MedicalExaminationDBContext().getExaminationById(id);
        if (exam == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.print("{\"status\":\"error\",\"message\":\"Examination not found\"}");
            return;
        }
        
        // Chỉ cập nhật prescription, giữ nguyên các trường khác
        exam.setPrescription(prescription != null ? prescription.trim() : "");
        
        boolean success = new MedicalExaminationDBContext().updateExamination(exam);
        
        if (success) {
            out.print("{\"status\":\"success\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"status\":\"error\",\"message\":\"Update failed\"}");
        }
    } catch (Exception e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        out.print("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
        e.printStackTrace();
    } finally {
        out.flush();
        out.close();
    }
}
}