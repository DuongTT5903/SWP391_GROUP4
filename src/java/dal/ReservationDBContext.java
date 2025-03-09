/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Customer;
import model.ReservationDetail;
import model.User;

/*
 *
 * @author admin
 */
public class ReservationDBContext {

    public int ReservationID() {
        String sql = "SELECT COUNT(*)"
                + "FROM Reservations;";
        int count = 0;
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                count = rs.getInt("COUNT(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;

    }

    public void addReservation(String customerName, String email, String address, String phone, LocalDate creationDate, int userID, int paymentMethod, float totalPrice, int gender, String Description,int status) {

        String sql = "INSERT INTO reservations ( CustomerName,Email,Address,Phone ,CreationDate,UserID   ,PaymentMethod ,TotalPrice, Gender,Description ,Status)"
                + " VALUES (?, ?, ?, ?, ?,?, ?, ?,?,?,?);";

        // ✅ Use executeUpdate() instead
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customerName);
            stmt.setString(2, email);
            stmt.setString(3, address);
            stmt.setString(4, phone);
            stmt.setDate(5, java.sql.Date.valueOf(creationDate));
            stmt.setInt(6, userID);
            stmt.setInt(7, paymentMethod);
            stmt.setFloat(8, totalPrice);
            stmt.setInt(9, gender);
            stmt.setString(10, Description);
            stmt.setInt(11, status);
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Service added successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addReservationDetail(int revationID, int serviceID, int amount, int numberOfPerson) {
        String sql = "INSERT INTO reservationDetails ( reservationID,serviceID,amount,numberOfPerson)"
                + " VALUES (?, ?, ?,?);";

        // ✅ Use executeUpdate() instead
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, revationID);
            stmt.setInt(2, serviceID);
            stmt.setInt(3, amount);
            stmt.setInt(4, numberOfPerson);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Service added successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCardInfo(int revationID,String cardName, String cardNumber, String CVV, LocalDate expirationDate) {
        String sql = "INSERT INTO reservations ( revationID,cardName,cardNumber,CVV,expirationDate)"
                + " VALUES (?,?, ?, ?,?);";

        // ✅ Use executeUpdate() instead
        try (Connection conn = DBContext.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, revationID);
            stmt.setString(2, cardName);
            stmt.setString(3, cardNumber);
            stmt.setString(4, CVV);
            stmt.setDate(5, java.sql.Date.valueOf(expirationDate));

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Service added successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Customer getCustomerByID(int ID) {
    Customer customer = null;  // Use null to handle cases where no customer is found
    String sql = "SELECT u.*, c.CustomerID, c.Address "
               + "FROM customer c JOIN users u ON c.userID = u.userID "
               + "WHERE c.userID = ?";

    try (Connection conn = DBContext.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, ID);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {  // Use if instead of while since userID should be unique
            User author = new User(
                rs.getInt("UserID"),
                rs.getString("Name"),
                rs.getBoolean("Gender"),
                rs.getString("Email"),
                rs.getString("Username"),
                rs.getString("Password"),
                rs.getString("Phone"),
                "", ""
            );
            customer = new Customer(
                rs.getInt("CustomerID"),
                author,
                rs.getString("Address")  // Corrected field name
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return customer;
}


    public List<ReservationDetail> searchService(List<ReservationDetail> reservationDetail, String search, int categoryID, int page, int pageSize) {
        int min = pageSize * (page - 1);
        int max = pageSize * page;
        List<ReservationDetail> services1 = new ArrayList<>();
        List<ReservationDetail> services2 = new ArrayList<>();
        for (int i = 0; i < reservationDetail.size(); i++) {
            int k = reservationDetail.get(i).getService().getServiceName().indexOf(search);
            if (k != -1) {
                if (reservationDetail.get(i).getService().getCategory().getCategoryID() == categoryID || categoryID == 0) {
                    services1.add(reservationDetail.get(i));
                }
            }
        }
        if (services1.size() > min) {
            if (services1.size() < max) {
                max = services1.size();
            }
            for (int i = min; i < max; i++) {
                services2.add(services1.get(i));
            }
        }

        return services2;
    }

    public static void main(String[] args) {
        ReservationDBContext r = new ReservationDBContext();
        LocalDate today = LocalDate.now();
        System.out.println(""+r.getCustomerByID(5).getAddress());
       
    }
}
