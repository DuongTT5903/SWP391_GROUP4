package model;

/**
 * Represents a service with details, price, category, and author information.
 */
public class Service {

    private int serviceID;
    private String serviceName;
    private String serviceDetail;
    private ServiceCategory category; // Ensure this class exists
    private float servicePrice;
    private String imageURL;
    private boolean status; // Indicates if the service is active
    private User author;  // Ensure the User class exists

    // Default constructor
    public Service() {
    }

    // Parameterized constructor
    public Service(int serviceID, String serviceName, String serviceDetail, ServiceCategory category, float servicePrice, String imageURL, boolean status, User authorID) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.serviceDetail = serviceDetail;
        this.category = category;
        this.servicePrice = servicePrice;
        this.imageURL = imageURL;
        this.status = status;
        this.author = author;
    }

    // Getters
    public int getServiceID() {
        return serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceDetail() {
        return serviceDetail;
    }

    public ServiceCategory getCategory() {
        return category;
    }

    public float getServicePrice() {
        return servicePrice;
    }

    public String getImageURL() {
        return imageURL;
    }

    public boolean isStatus() {
        return status;
    }

    public User getAuthor() {
        return author;
    }

    // Setters
    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setServiceDetail(String serviceDetail) {
        this.serviceDetail = serviceDetail;
    }

    public void setCategory(ServiceCategory category) {
        this.category = category;
    }

    public void setServicePrice(float servicePrice) {
        this.servicePrice = servicePrice;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setAuthorID(User authorID) {
        this.author = author;
    }

    // toString method for debugging or logging
    @Override
    public String toString() {
        return "Service{" +
                "serviceID=" + serviceID +
                ", serviceName='" + serviceName + '\'' +
                ", serviceDetail='" + serviceDetail + '\'' +
                ", category=" + category +
                ", servicePrice=" + servicePrice +
                ", imageURL='" + imageURL + '\'' +
                ", status=" + status +
                ", author=" + author +
                '}';
    }
}
