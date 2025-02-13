package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Feedback;
import model.Service;
import model.User;

public class FeedbackDBContext {
    
    public List<Feedback> getAllFeedbacks() {
        List<Feedback> feedbackList = new ArrayList<>();
        String sql = "SELECT f.FeedbackID, f.FeedbackDetail, f.CustomerID, f.Rated, f.imglink, " +
                     "f.serviceID, f.CreationDate, f.Status, u.UserID, u.Name AS UserName " +
                     "FROM Feedbacks f INNER JOIN Users u ON f.CustomerID = u.UserID";
        
        try (Connection connection = DBContext.getConnection();
             PreparedStatement stm = connection.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                Feedback feedback = new Feedback(
                    rs.getInt("FeedbackID"),
                    rs.getString("FeedbackDetail"),
                    rs.getInt("CustomerID"),
                    rs.getInt("Rated"),
                    rs.getString("imglink"),
                    new Service(rs.getInt("serviceID"), null, null, null, 0.0f, 0.0f, null, false, null),
                    rs.getDate("CreationDate"),
                    rs.getBoolean("Status"),
                    new User(rs.getInt("UserID"), rs.getString("UserName"), false, null, null, null, null, null, null)
                );
                feedbackList.add(feedback);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDBContext.class.getName()).log(Level.SEVERE, "Error fetching all feedbacks", ex);
        }
        return feedbackList;
    }

    public List<Feedback> getFeedbacksByUser(int userID) {
        List<Feedback> feedbackList = new ArrayList<>();
        String sql = "SELECT f.FeedbackID, f.FeedbackDetail, f.CustomerID, f.Rated, f.imglink, " +
                     "f.serviceID, f.CreationDate, f.Status, u.UserID, u.Name AS UserName " +
                     "FROM Feedbacks f INNER JOIN Users u ON f.CustomerID = u.UserID " +
                     "WHERE u.UserID = ?";
        
        try (Connection connection = DBContext.getConnection();
             PreparedStatement stm = connection.prepareStatement(sql)) {
            
            stm.setInt(1, userID);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Feedback feedback = new Feedback(
                        rs.getInt("FeedbackID"),
                        rs.getString("FeedbackDetail"),
                        rs.getInt("CustomerID"),
                        rs.getInt("Rated"),
                        rs.getString("imglink"),
                        new Service(rs.getInt("serviceID"), null, null, null, 0.0f, 0.0f, null, false, null),
                        rs.getDate("CreationDate"),
                        rs.getBoolean("Status"),
                        new User(rs.getInt("UserID"), rs.getString("UserName"), false, null, null, null, null, null, null)
                    );
                    feedbackList.add(feedback);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackDBContext.class.getName()).log(Level.SEVERE, "Error fetching feedbacks by user", ex);
        }
        return feedbackList;
    }
}
