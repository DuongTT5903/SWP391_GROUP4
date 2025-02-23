package model;

/**
 * Represents a slider with a title, background link, image, status, and author information.
 */
public class Slider {
    private int slideID;           // Unique identifier for the slider
    private String title;          // Title of the slider
    private String backLink;       // Link associated with the slider
    private String img;            // Image URL for the slider
    private boolean status;        // Indicates if the slider is active
    private User author;           // User who created or manages the slider

    // Default constructor

    /**
     *
     */
    public Slider() {
    }

    // Parameterized constructor

    /**
     *
     * @param slideID
     * @param title
     * @param backLink
     * @param img
     * @param status
     * @param author
     */
    public Slider(int slideID, String title, String backLink, String img, boolean status, User author) {
        this.slideID = slideID;
        this.title = title;
        this.backLink = backLink;
        this.img = img;
        this.status = status;
        this.author = author;
    }

    // Getters

    /**
     *
     * @return
     */
    public int getSlideID() {
        return slideID;
    }

    /**
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return
     */
    public String getBackLink() {
        return backLink;
    }

    /**
     *
     * @return
     */
    public String getImg() {
        return img;
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
     * @return
     */
    public User getAuthor() {
        return author;
    }

    // Setters

    /**
     *
     * @param slideID
     */
    public void setSlideID(int slideID) {
        this.slideID = slideID;
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @param backLink
     */
    public void setBackLink(String backLink) {
        this.backLink = backLink;
    }

    /**
     *
     * @param img
     */
    public void setImg(String img) {
        this.img = img;
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
     * @param author
     */
    public void setAuthor(User author) {
        this.author = author;
    }

    // toString method for debugging or logging

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Slider{" +
                "slideID=" + slideID +
                ", title='" + title + '\'' +
                ", backLink='" + backLink + '\'' +
                ", img='" + img + '\'' +
                ", status=" + status +
                ", author=" + author +
                '}';
    }
}
