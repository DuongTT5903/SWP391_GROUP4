package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.MedicalExamination;
import model.Reservation;
import model.Service;

public class MedicalExaminationDBContext extends DBContext {

    private static Connection connection = DBContext.getConnection();

    /**
     * Lấy tất cả các lần khám bệnh
     */
    public List<MedicalExamination> getAllExaminations() {
        List<MedicalExamination> examinations = new ArrayList<>();
        // Map để lưu danh sách dịch vụ cho từng lần khám
        Map<Integer, MedicalExamination> examMap = new HashMap<>();

        String sql = "SELECT me.*, r.ReservationID, r.CustomerName, rd.ServiceID, s.ServiceName " +
                     "FROM MedicalExaminations me " +
                     "JOIN Reservations r ON me.ReservationID = r.ReservationID " +
                     "JOIN ReservationDetails rd ON r.ReservationID = rd.ReservationID " +
                     "JOIN Services s ON rd.ServiceID = s.ServiceID";

        try (PreparedStatement st = DBContext.getConnection().prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                int examinationId = rs.getInt("ExaminationID");

                // Nếu lần khám chưa có trong map, tạo mới
                MedicalExamination exam = examMap.get(examinationId);
                if (exam == null) {
                    exam = new MedicalExamination();
                    exam.setExaminationId(examinationId);
                    exam.setPrescription(rs.getString("Prescription"));
                    exam.setCreationDate(rs.getDate("CreationDate"));
                    exam.setDoctorId(rs.getInt("DoctorID"));

                    Reservation reservation = new Reservation();
                    reservation.setReservationID(rs.getInt("ReservationID"));
                    reservation.setCustomerName(rs.getString("CustomerName"));
                    exam.setReservation(reservation);

                    exam.setServices(new ArrayList<>());
                    examMap.put(examinationId, exam);
                    examinations.add(exam);
                }

                // Thêm dịch vụ vào danh sách dịch vụ của lần khám
                Service service = new Service();
                service.setServiceID(rs.getInt("ServiceID"));
                service.setServiceName(rs.getString("ServiceName"));
                exam.getServices().add(service);
            }
        } catch (SQLException e) {
            Logger.getLogger(MedicalExaminationDBContext.class.getName()).log(Level.SEVERE, "Error getting all examinations", e);
        }

        return examinations;
    }

    /**
     * Lấy danh sách lần khám theo ngày
     */
    public List<MedicalExamination> getExaminationsByDate(Date date) {
        List<MedicalExamination> examinations = new ArrayList<>();
        Map<Integer, MedicalExamination> examMap = new HashMap<>();

        String sql = "SELECT me.*, r.ReservationID, r.CustomerName, rd.ServiceID, s.ServiceName " +
                     "FROM MedicalExaminations me " +
                     "JOIN Reservations r ON me.ReservationID = r.ReservationID " +
                     "JOIN ReservationDetails rd ON r.ReservationID = rd.ReservationID " +
                     "JOIN Services s ON rd.ServiceID = s.ServiceID " +
                     "WHERE me.CreationDate = ?";

        try (PreparedStatement st = DBContext.getConnection().prepareStatement(sql)) {
            st.setDate(1, new java.sql.Date(date.getTime()));
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int examinationId = rs.getInt("ExaminationID");

                MedicalExamination exam = examMap.get(examinationId);
                if (exam == null) {
                    exam = new MedicalExamination();
                    exam.setExaminationId(examinationId);
                    exam.setPrescription(rs.getString("Prescription"));
                    exam.setCreationDate(rs.getDate("CreationDate"));
                    exam.setDoctorId(rs.getInt("DoctorID"));

                    Reservation reservation = new Reservation();
                    reservation.setReservationID(rs.getInt("ReservationID"));
                    reservation.setCustomerName(rs.getString("CustomerName"));
                    exam.setReservation(reservation);

                    exam.setServices(new ArrayList<>());
                    examMap.put(examinationId, exam);
                    examinations.add(exam);
                }

                Service service = new Service();
                service.setServiceID(rs.getInt("ServiceID"));
                service.setServiceName(rs.getString("ServiceName"));
                exam.getServices().add(service);
            }
        } catch (SQLException e) {
            Logger.getLogger(MedicalExaminationDBContext.class.getName()).log(Level.SEVERE, "Error getting examinations by date", e);
        }

        return examinations;
    }

    /**
     * Lấy danh sách lần khám theo dịch vụ
     */
    public List<MedicalExamination> getExaminationsByService(int serviceId) {
        List<MedicalExamination> examinations = new ArrayList<>();
        Map<Integer, MedicalExamination> examMap = new HashMap<>();

        String sql = "SELECT me.*, r.ReservationID, r.CustomerName, rd.ServiceID, s.ServiceName " +
                     "FROM MedicalExaminations me " +
                     "JOIN Reservations r ON me.ReservationID = r.ReservationID " +
                     "JOIN ReservationDetails rd ON r.ReservationID = rd.ReservationID " +
                     "JOIN Services s ON rd.ServiceID = s.ServiceID " +
                     "WHERE rd.ServiceID = ?";

        try (PreparedStatement st = DBContext.getConnection().prepareStatement(sql)) {
            st.setInt(1, serviceId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int examinationId = rs.getInt("ExaminationID");

                MedicalExamination exam = examMap.get(examinationId);
                if (exam == null) {
                    exam = new MedicalExamination();
                    exam.setExaminationId(examinationId);
                    exam.setPrescription(rs.getString("Prescription"));
                    exam.setCreationDate(rs.getDate("CreationDate"));
                    exam.setDoctorId(rs.getInt("DoctorID"));

                    Reservation reservation = new Reservation();
                    reservation.setReservationID(rs.getInt("ReservationID"));
                    reservation.setCustomerName(rs.getString("CustomerName"));
                    exam.setReservation(reservation);

                    exam.setServices(new ArrayList<>());
                    examMap.put(examinationId, exam);
                    examinations.add(exam);
                }

                Service service = new Service();
                service.setServiceID(rs.getInt("ServiceID"));
                service.setServiceName(rs.getString("ServiceName"));
                exam.getServices().add(service);
            }
        } catch (SQLException e) {
            Logger.getLogger(MedicalExaminationDBContext.class.getName()).log(Level.SEVERE, "Error getting examinations by service", e);
        }

        return examinations;
    }

    /**
     * Lấy danh sách lần khám theo tên thuốc
     */
    public List<MedicalExamination> getExaminationsByMedicineName(String medicineName) {
        List<MedicalExamination> examinations = new ArrayList<>();
        Map<Integer, MedicalExamination> examMap = new HashMap<>();

        String sql = "SELECT me.*, r.ReservationID, r.CustomerName, rd.ServiceID, s.ServiceName " +
                     "FROM MedicalExaminations me " +
                     "JOIN Reservations r ON me.ReservationID = r.ReservationID " +
                     "JOIN ReservationDetails rd ON r.ReservationID = rd.ReservationID " +
                     "JOIN Services s ON rd.ServiceID = s.ServiceID " +
                     "WHERE me.Prescription LIKE ?";

        try (PreparedStatement st = DBContext.getConnection().prepareStatement(sql)) {
            st.setString(1, "%" + medicineName + "%");
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int examinationId = rs.getInt("ExaminationID");

                MedicalExamination exam = examMap.get(examinationId);
                if (exam == null) {
                    exam = new MedicalExamination();
                    exam.setExaminationId(examinationId);
                    exam.setPrescription(rs.getString("Prescription"));
                    exam.setCreationDate(rs.getDate("CreationDate"));
                    exam.setDoctorId(rs.getInt("DoctorID"));

                    Reservation reservation = new Reservation();
                    reservation.setReservationID(rs.getInt("ReservationID"));
                    reservation.setCustomerName(rs.getString("CustomerName"));
                    exam.setReservation(reservation);

                    exam.setServices(new ArrayList<>());
                    examMap.put(examinationId, exam);
                    examinations.add(exam);
                }

                Service service = new Service();
                service.setServiceID(rs.getInt("ServiceID"));
                service.setServiceName(rs.getString("ServiceName"));
                exam.getServices().add(service);
            }
        } catch (SQLException e) {
            Logger.getLogger(MedicalExaminationDBContext.class.getName()).log(Level.SEVERE, "Error getting examinations by medicine name", e);
        }

        return examinations;
    }

    /**
     * Lấy chi tiết một lần khám theo ID
     */
    public MedicalExamination getExaminationById(int id) {
        MedicalExamination exam = null;
        Map<Integer, MedicalExamination> examMap = new HashMap<>();

        String sql = "SELECT me.*, r.ReservationID, r.CustomerName, rd.ServiceID, s.ServiceName " +
                     "FROM MedicalExaminations me " +
                     "JOIN Reservations r ON me.ReservationID = r.ReservationID " +
                     "JOIN ReservationDetails rd ON r.ReservationID = rd.ReservationID " +
                     "JOIN Services s ON rd.ServiceID = s.ServiceID " +
                     "WHERE me.ExaminationID = ?";

        try (PreparedStatement st = DBContext.getConnection().prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int examinationId = rs.getInt("ExaminationID");

                exam = examMap.get(examinationId);
                if (exam == null) {
                    exam = new MedicalExamination();
                    exam.setExaminationId(examinationId);
                    exam.setPrescription(rs.getString("Prescription"));
                    exam.setCreationDate(rs.getDate("CreationDate"));
                    exam.setDoctorId(rs.getInt("DoctorID"));

                    Reservation reservation = new Reservation();
                    reservation.setReservationID(rs.getInt("ReservationID"));
                    reservation.setCustomerName(rs.getString("CustomerName"));
                    exam.setReservation(reservation);

                    exam.setServices(new ArrayList<>());
                    examMap.put(examinationId, exam);
                }

                Service service = new Service();
                service.setServiceID(rs.getInt("ServiceID"));
                service.setServiceName(rs.getString("ServiceName"));
                exam.getServices().add(service);
            }
        } catch (SQLException e) {
            Logger.getLogger(MedicalExaminationDBContext.class.getName()).log(Level.SEVERE, "Error getting examination by ID", e);
        }

        return exam;
    }

    /**
     * Thêm mới một lần khám
     */
    public void addExamination(MedicalExamination exam) {
        String sql = "INSERT INTO MedicalExaminations (ReservationID, Prescription, CreationDate, DoctorID) VALUES (?, ?, ?, ?)";

        try (PreparedStatement st = DBContext.getConnection().prepareStatement(sql)) {
            st.setInt(1, exam.getReservation().getReservationID());
            st.setString(2, exam.getPrescription());
            st.setDate(3, new java.sql.Date(exam.getCreationDate().getTime()));
            st.setInt(4, exam.getDoctorId());
            st.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(MedicalExaminationDBContext.class.getName()).log(Level.SEVERE, "Error adding examination", e);
        }
    }
}