package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cart;
import model.Customer;
import model.Reservation;
import model.ReservationDetail;
import model.Service;

public class CartDBContext extends DBContext {
    public boolean updateReservationStatus(int reservationID, int status) {
    String sql = "UPDATE Reservations SET status = ? WHERE reservationID = ?";
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, status);
        statement.setInt(2, reservationID);
        
        int rowsAffected = statement.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
public boolean removeCheckedItems(int userID, List<Cart> checkItem) {
    // Step 1: Build the IN clause dynamically based on the size of checkItem
    StringBuilder inClause = new StringBuilder();
    for (int i = 0; i < checkItem.size(); i++) {
        if (i > 0) {
            inClause.append(", ");
        }
        inClause.append("?");
    }
    
    // Step 2: Prepare the SQL query with the dynamic IN clause
    String query = "DELETE FROM Carts WHERE userID = ? AND serviceID IN (" + inClause.toString() + ")";
    
    try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
        // Set the userID parameter
        stmt.setInt(1, userID);

        // Step 3: Set the serviceIDs dynamically into the PreparedStatement
        for (int i = 0; i < checkItem.size(); i++) {
            stmt.setInt(i + 2, checkItem.get(i).getService().getServiceID());  // Setting service IDs starting from index 2
        }

        // Step 4: Execute the update and check the result
        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}




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

        String sql = "UPDATE Reservations SET CustomerName=?, Email=?, Address=?, Phone=?,  TotalPrice=?, Status=0 WHERE ReservationID=?";

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
public boolean addReservationDetail(ReservationDetail detail) {
    try (Connection conn = getConnection()) {
        String sql = "INSERT INTO ReservationDetails (reservationID, serviceID, amount) VALUES (?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, detail.getRevationID());
            stmt.setInt(2, detail.getService().getServiceID());
            stmt.setInt(3, detail.getAmount());
           
            
            int rowsAffected = stmt.executeUpdate();
            // If at least one row was affected, return true
            return rowsAffected > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return false;  // Return false if there was an exception
    }
}


    // Phương thức giả lập kết nối cơ sở dữ liệu (cần thay thế bằng phương thức thực tế của bạn)






    public static void main(String[] args) {
        // Create mock Cart data
        List<Cart> checkItem = new ArrayList<>();
        
        // Mock Cart items
        Service service1 = new Service(1, "Service 1", "", null, 100, 10, "", true, null);
        Service service2 = new Service(2, "Service 2", "", null, 200, 20, "", true, null);
        
        // Mock Cart objects
        Cart cart1 = new Cart(1, true, 2, service1, null); // 2 items of Service 1
        Cart cart2 = new Cart(2, true, 1, service2, null); // 1 item of Service 2
        
        checkItem.add(cart1);
        checkItem.add(cart2);
        
        // Print checkItem contents before removal
        System.out.println("Before removal:");
        for (Cart cart : checkItem) {
            System.out.println("Service ID: " + cart.getService().getServiceID() + ", Amount: " + cart.getAmount());
        }

        // Simulate a userID for testing
        int userID = 4;  // Assuming user ID is 4
        
        // Call the method to remove checked items
        CartDBContext cartDB = new CartDBContext();
        boolean result = cartDB.removeCheckedItems(userID, checkItem);

        // Print result of removal
        if (result) {
            System.out.println("Checked items removed successfully.");
        } else {
            System.out.println("Failed to remove checked items.");
        }

        // Print checkItem contents after removal (if the list was updated properly)
        System.out.println("After removal:");
        for (Cart cart : checkItem) {
            System.out.println("Service ID: " + cart.getService().getServiceID() + ", Amount: " + cart.getAmount());
        }
    }



    private void closeConnection(Connection conn, PreparedStatement ps) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
