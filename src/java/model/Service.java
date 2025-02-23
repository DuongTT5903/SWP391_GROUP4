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

    /**
     *
     */
    public Service() {
    }

    /**
     *
     * @param serviceID
     * @param serviceName
     * @param serviceDetail
     * @param category
     * @param servicePrice
     * @param salePrice
     * @param imageURL
     * @param status
     * @param author
     */
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

    /**
     *
     * @return
     */
    public int getServiceID() {
        return serviceID;
    }

    /**
     *
     * @param serviceID
     */
    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    /**
     *
     * @return
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     *
     * @param serviceName
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     *
     * @return
     */
    public String getServiceDetail() {
        return serviceDetail;
    }

    /**
     *
     * @param serviceDetail
     */
    public void setServiceDetail(String serviceDetail) {
        this.serviceDetail = serviceDetail;
    }

    /**
     *
     * @return
     */
    public ServiceCategory getCategory() {
        return category;
    }

    /**
     *
     * @param category
     */
    public void setCategory(ServiceCategory category) {
        this.category = category;
    }

    /**
     *
     * @return
     */
    public float getServicePrice() {
        return servicePrice;
    }

    /**
     *
     * @param servicePrice
     */
    public void setServicePrice(float servicePrice) {
        this.servicePrice = servicePrice;
    }

    /**
     *
     * @return
     */
    public float getSalePrice() {
        return salePrice;
    }

    /**
     *
     * @param salePrice
     */
    public void setSalePrice(float salePrice) {
        this.salePrice = salePrice;
    }

    /**
     *
     * @return
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     *
     * @param imageURL
     */
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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
    public User getAuthor() {
        return author;
    }

    /**
     *
     * @param author
     */
    public void setAuthor(User author) {
        this.author = author;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Service{" + "serviceID=" + serviceID + ", serviceName=" + serviceName + ", serviceDetail=" + serviceDetail + ", category=" + category + ", servicePrice=" + servicePrice + ", salePrice=" + salePrice + ", imageURL=" + imageURL + ", status=" + status + ", author=" + author + '}';
    }

}
