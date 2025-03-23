package controller;

import dal.MedicalExaminationDBContext;
import dal.ServiceDBContext;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.MedicalExamination;
import model.Reservation;
import model.Service;

public class MedicalExaminationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        MedicalExaminationDBContext db = new MedicalExaminationDBContext();
        ServiceDBContext serviceDB = new ServiceDBContext();

        if (path.equals("/staff/examination/list")) {
            String dateParam = request.getParameter("date");
            String serviceIdParam = request.getParameter("serviceId");
            String medicineName = request.getParameter("medicineName");

            List<MedicalExamination> examinations;
            if (dateParam != null && !dateParam.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = sdf.parse(dateParam);
                    examinations = db.getExaminationsByDate(date);
                } catch (ParseException e) {
                    examinations = db.getAllExaminations();
                }
            } else if (serviceIdParam != null && !serviceIdParam.isEmpty()) {
                int serviceId = Integer.parseInt(serviceIdParam);
                examinations = db.getExaminationsByService(serviceId);
            } else if (medicineName != null && !medicineName.isEmpty()) {
                examinations = db.getExaminationsByMedicineName(medicineName);
            } else {
                examinations = db.getAllExaminations();
            }

            List<Service> services = serviceDB.getServices("", 0, 1, Integer.MAX_VALUE);
            request.setAttribute("examinations", examinations);
            request.setAttribute("services", services);
            request.getRequestDispatcher("/staff/examination/list.jsp").forward(request, response);

        } else if (path.equals("/staff/examination/detail")) {
            int id = Integer.parseInt(request.getParameter("id"));
            MedicalExamination examination = db.getExaminationById(id);
            request.setAttribute("examination", examination);
            request.getRequestDispatcher("/staff/examination/detail.jsp").forward(request, response);

        } else if (path.equals("/staff/examination/add")) {
            String reservationIdParam = request.getParameter("reservationId");
            if (reservationIdParam == null || reservationIdParam.trim().isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Reservation ID is required");
                return;
            }

            try {
                int reservationId = Integer.parseInt(reservationIdParam);
                MedicalExamination exam = new MedicalExamination();
                Reservation reservation = new Reservation();
                reservation.setReservationID(reservationId);
                exam.setReservation(reservation);
                request.setAttribute("examination", exam);
                request.getRequestDispatcher("/staff/examination/add.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Reservation ID");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        MedicalExaminationDBContext db = new MedicalExaminationDBContext();

        if (path.equals("/staff/examination/add")) {
            int reservationId = Integer.parseInt(request.getParameter("reservationId"));
            String prescription = request.getParameter("prescription");

            // Kiểm tra prescription rỗng hoặc chỉ chứa dấu cách
            if (prescription == null || prescription.trim().isEmpty()) {
                MedicalExamination exam = new MedicalExamination();
                Reservation reservation = new Reservation();
                reservation.setReservationID(reservationId);
                exam.setReservation(reservation);
                request.setAttribute("examination", exam);
                request.setAttribute("error", "Prescription cannot be empty or contain only spaces.");
                request.getRequestDispatcher("/staff/examination/add.jsp").forward(request, response);
                return;
            }

            MedicalExamination exam = new MedicalExamination();
            Reservation reservation = new Reservation();
            reservation.setReservationID(reservationId);
            exam.setReservation(reservation);
            exam.setPrescription(prescription);
            exam.setCreationDate(new Date());
            exam.setDoctorId(3); // Giả sử DoctorID là 3 (Staff), bạn có thể lấy từ session

            db.addExamination(exam);
            response.sendRedirect(request.getContextPath() + "/staff/examination/list");
        }
    }
}