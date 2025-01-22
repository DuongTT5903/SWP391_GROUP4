package model;

import java.util.Date;

/**
 * This class represents a Feedback with various details such as rating, 
 * customer ID, associated service, and more.
 *
 * @author admin
 */
public class Feedback {
    private int id;
    private String feedbackDetail;
    private int customerID;
    private int rated; // Rating value
    private String imgLink; // Image link associated with feedback
    private Service services; // Associated service
    private Date creationDate; // Creation date of feedback
    private boolean status; // Status of the feedback (e.g., approved or pending)
    private User user; // Associated user who gave feedback

    // Default constructor
    public Feedback() {
    }

    // Parameterized constructor
    public Feedback(int id, String feedbackDetail, int customerID, int rated, String imgLink, Service services, Date creationDate, boolean status, User user) {
        this.id = id;
        this.feedbackDetail = feedbackDetail;
        this.customerID = customerID;
        this.rated = rated;
        this.imgLink = imgLink;
        this.services = services;
        this.creationDate = creationDate;
        this.status = status;
        this.user = user;
    }

    // Getter and Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFeedbackDetail() {
        return feedbackDetail;
    }

    public void setFeedbackDetail(String feedbackDetail) {
        this.feedbackDetail = feedbackDetail;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getRated() {
        return rated;
    }

    public void setRated(int rated) {
        this.rated = rated;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public Service getServices() {
        return services;
    }

    public void setServices(Service services) {
        this.services = services;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // toString method for easy representation
    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", feedbackDetail='" + feedbackDetail + '\'' +
                ", customerID=" + customerID +
                ", rated=" + rated +
                ", imgLink='" + imgLink + '\'' +
                ", services=" + services +
                ", creationDate=" + creationDate +
                ", status=" + status +
                ", user=" + user +
                '}';
    }
}
