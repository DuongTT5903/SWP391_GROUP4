package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Reservation;
import model.ReservationDetail;
import model.Service;

public class CartDBContext extends DBContext {

    // Thêm đặt chỗ mới
public int addReservation(Reservation reservation) {
    String sql = "INSERT INTO Reservations (CustomerName, Email, Address, Phone, CreationDate, UserID, Status, PaymentMethod, TotalPrice) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = DBContext.getConnection(); 
         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        stmt.setString(1, reservation.getCustomerName());
        stmt.setString(2, reservation.getEmail());
        stmt.setString(3, reservation.getAddress());
        stmt.setString(4, reservation.getPhone());
        stmt.setDate(5, new java.sql.Date(System.currentTimeMillis())); // Chuyển thành `java.sql.Date`

        if (reservation.getUserID() == 0) {
            stmt.setNull(6, Types.INTEGER);
        } else {
            stmt.setInt(6, reservation.getUserID());
        }

        stmt.setInt(7, 0);  // Mặc định chưa thanh toán
        stmt.setInt(8, reservation.getPaymentMethod());

    stmt.setDouble(9, reservation.getTotalPrice());



        stmt.executeUpdate();

        try (ResultSet rs = stmt.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(CartDBContext.class.getName()).log(Level.SEVERE, "Error adding reservation", ex);
    }
    return -1;
}




    // Lấy ID đơn đặt chỗ mới nhất của người dùng
    public int getLatestReservationID(int userID) {
        String sql = "SELECT ReservationID FROM Reservations WHERE UserID = ? ORDER BY CreationDate DESC LIMIT 1";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ReservationID");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartDBContext.class.getName()).log(Level.SEVERE, "Error getting latest reservation ID", ex);
        }
        return -1;
    }

    // Kiểm tra xem có dịch vụ nào hết hạn không
    public boolean hasExpiredServices(int reservationID) {
        String sql = "SELECT COUNT(*) FROM ReservationDetails rd " +
                     "JOIN Services s ON rd.ServiceID = s.ServiceID " +
                     "WHERE rd.ReservationID = ? AND s.ExpiryDate < NOW()";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reservationID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartDBContext.class.getName()).log(Level.SEVERE, "Error checking expired services", ex);
        }
        return false;
    }

    // Thanh toán đơn đặt chỗ
    public boolean checkoutReservation(int reservationID, String total, String name, String phone, String email, String address) {

        String sql = "UPDATE Reservations SET CustomerName=?, Email=?, Address=?, Phone=?,  TotalPrice=?, Status=1 WHERE ReservationID=?";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, address);
            stmt.setString(4, phone);
            stmt.setFloat(5, Float.parseFloat(total));
            stmt.setInt(6, reservationID);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CartDBContext.class.getName()).log(Level.SEVERE, "Error during checkout", ex);
        }
        return false;
    }
    public void addReservationDetail(ReservationDetail detail) {
    try {
        String sql = "INSERT INTO ReservationDetails (reservationID, serviceID, amount, numberOfPerson) VALUES (?, ?, ?, ?)";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setInt(1, detail.getRevationID());
        stm.setInt(2, detail.getService().getServiceID());
        stm.setInt(3, detail.getAmount());
        stm.setInt(4, detail.getNumberOfPerson());
        stm.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}
