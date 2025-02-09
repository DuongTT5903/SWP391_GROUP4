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
    private float salePrice;
    private String imageURL;
    private boolean status; // Indicates if the service is active
    private User author;  // Ensure the User class exists

    // Default constructor
    public Service() {
    }


    public Service(int serviceID, String serviceName, String serviceDetail, ServiceCategory category, float servicePrice, float salePrice, String imageURL, boolean status, User author) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.serviceDetail = serviceDetail;
        this.category = category;
        this.servicePrice = servicePrice;
        this.salePrice = salePrice;
        this.imageURL = imageURL;
        this.status = status;
        this.author = author;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDetail() {
        return serviceDetail;
    }

    public void setServiceDetail(String serviceDetail) {
        this.serviceDetail = serviceDetail;
    }

    public ServiceCategory getCategory() {
        return category;
    }

    public void setCategory(ServiceCategory category) {
        this.category = category;
    }

    public float getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(float servicePrice) {
        this.servicePrice = servicePrice;
    }

    public float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(float salePrice) {
        this.salePrice = salePrice;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Service{" + "serviceID=" + serviceID + ", serviceName=" + serviceName + ", serviceDetail=" + serviceDetail + ", category=" + category + ", servicePrice=" + servicePrice + ", salePrice=" + salePrice + ", imageURL=" + imageURL + ", status=" + status + ", author=" + author + '}';
    }

}
