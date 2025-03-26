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
import java.util.stream.Collectors;
import model.MedicalExamination;
import model.Reservation;
import model.Service;
import model.ServiceCategory;

public class MedicalExaminationDBContext extends DBContext {
   public boolean updateExamination(MedicalExamination exam) {
    String sql = "UPDATE MedicalExaminations SET Prescription = ? WHERE ExaminationID = ?";
    // Chỉ cập nhật Prescription, không cập nhật CreationDate
    
    try (Connection conn = super.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, exam.getPrescription());
        stmt.setInt(2, exam.getExaminationId());
        
        int affectedRows = stmt.executeUpdate();
        return affectedRows > 0;
        
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    private static Connection connection = DBContext.getConnection();

    /**
     * Lấy tất cả các lần khám bệnh
     */
    
    public List<Service> getMedicalServices() {
    List<Service> services = new ArrayList<>();
    String sql = "SELECT s.ServiceID, s.ServiceName, s.ServicePrice, s.SalePrice, " +
                 "sc.CategoryID, sc.CategoryName " +
                 "FROM Services s " +
                 "JOIN ServiceCategories sc ON s.CategoryID = sc.CategoryID " +
                 "WHERE sc.CategoryName IN ('Khám Mắt', 'Khám Răng', 'Tiêm Phòng') " +
                 "AND s.Status = 1"; // Chỉ lấy dịch vụ đang hoạt động
    
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        
        while (rs.next()) {
            ServiceCategory category = new ServiceCategory();
            category.setCategoryID(rs.getInt("CategoryID"));
            category.setCategoryName(rs.getString("CategoryName"));
            
            Service service = new Service();
            service.setServiceID(rs.getInt("ServiceID"));
            service.setServiceName(rs.getString("ServiceName"));
            service.setServicePrice(rs.getFloat("ServicePrice"));
            service.setSalePrice(rs.getFloat("SalePrice"));
            service.setCategory(category);
            
            services.add(service);
        }
    } catch (SQLException e) {
        // Ghi log lỗi
        System.err.println("Lỗi khi truy vấn dịch vụ y tế: " + e.getMessage());
    }
    
    return services;
}
    public List<MedicalExamination> getAllExaminations() {
    List<MedicalExamination> examinations = new ArrayList<>();
    Map<Integer, MedicalExamination> examMap = new HashMap<>();

    // Query to get examination data with reservation info
    String sql = "SELECT me.*, r.ReservationID, r.CustomerName \n" +
"                 FROM MedicalExaminations me \n" +
"                 JOIN Reservations r ON me.ReservationID = r.ReservationID\n" +
"                 Where r.AcceptStatus='1' ";

    try (Connection conn = DBContext.getConnection();
         PreparedStatement st = conn.prepareStatement(sql);
         ResultSet rs = st.executeQuery()) {

        // First pass: get all examinations
        while (rs.next()) {
            int examinationId = rs.getInt("ExaminationID");
            
            MedicalExamination exam = new MedicalExamination();
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

        // Second query to get services for all examinations
        if (!examMap.isEmpty()) {
            String servicesSql = "SELECT rd.ReservationID, s.ServiceID, s.ServiceName " +
                               "FROM ReservationDetails rd " +
                               "JOIN Services s ON rd.ServiceID = s.ServiceID " +
                               "JOIN MedicalExaminations me ON rd.ReservationID = me.ReservationID " +
                               "WHERE me.ExaminationID IN (" + 
                               examMap.keySet().stream().map(String::valueOf)
                                   .collect(Collectors.joining(",")) + ")";
            
            try (PreparedStatement stServices = conn.prepareStatement(servicesSql);
                 ResultSet rsServices = stServices.executeQuery()) {
                
                while (rsServices.next()) {
                    int reservationId = rsServices.getInt("ReservationID");
                    
                    // Find the examination with this reservation ID
                    for (MedicalExamination exam : examinations) {
                        if (exam.getReservation().getReservationID() == reservationId) {
                            Service service = new Service();
                            service.setServiceID(rsServices.getInt("ServiceID"));
                            service.setServiceName(rsServices.getString("ServiceName"));
                            exam.getServices().add(service);
                            break;
                        }
                    }
                }
            }
        }
    } catch (SQLException e) {
        Logger.getLogger(MedicalExaminationDBContext.class.getName()).log(Level.SEVERE, null, e);
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
             "WHERE rd.ServiceID = ? " +  // Thêm khoảng trắng ở đây
             "AND r.AcceptStatus = 1";

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
                     
                     "WHERE me.ExaminationID = ?"
                        ;

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
    
    }
