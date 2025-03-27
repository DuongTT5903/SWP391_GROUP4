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

    /**
     *
     */
    public Feedback() {
    }

    // Parameterized constructor

    /**
     *
     * @param id
     * @param feedbackDetail
     * @param customerID
     * @param rated
     * @param imgLink
     * @param services
     * @param creationDate
     * @param status
     * @param user
     */
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
    
    public Feedback(String feedbackDetail, int rated, Service services, User user) {
    this.feedbackDetail = feedbackDetail;
    this.rated = rated;
    this.services = services;
    this.user = user;
    this.customerID = user.getUserID(); // Lấy ID từ User
    this.creationDate = new Date(); // Mặc định ngày hiện tại
    this.status = true; // Mặc định trạng thái là true
}

    // Getter and Setter methods

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getFeedbackDetail() {
        return feedbackDetail;
    }

    /**
     *
     * @param feedbackDetail
     */
    public void setFeedbackDetail(String feedbackDetail) {
        this.feedbackDetail = feedbackDetail;
    }

    /**
     *
     * @return
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     *
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     *
     * @return
     */
    public int getRated() {
        return rated;
    }

    /**
     *
     * @param rated
     */
    public void setRated(int rated) {
        this.rated = rated;
    }

    /**
     *
     * @return
     */
    public String getImgLink() {
        return imgLink;
    }

    /**
     *
     * @param imgLink
     */
    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    /**
     *
     * @return
     */
    public Service getServices() {
        return services;
    }

    /**
     *
     * @param services
     */
    public void setServices(Service services) {
        this.services = services;
    }

    /**
     *
     * @return
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     *
     * @param creationDate
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     *
     * @return
     */
    public boolean isStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     *
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    // toString method for easy representation

    /**
     *
     * @return
     */
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
