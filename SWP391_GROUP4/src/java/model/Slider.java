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
    public Slider() {
    }

    // Parameterized constructor
    public Slider(int slideID, String title, String backLink, String img, boolean status, User author) {
        this.slideID = slideID;
        this.title = title;
        this.backLink = backLink;
        this.img = img;
        this.status = status;
        this.author = author;
    }

    // Getters
    public int getSlideID() {
        return slideID;
    }

    public String getTitle() {
        return title;
    }

    public String getBackLink() {
        return backLink;
    }

    public String getImg() {
        return img;
    }

    public boolean isStatus() {
        return status;
    }

    public User getAuthor() {
        return author;
    }

    // Setters
    public void setSlideID(int slideID) {
        this.slideID = slideID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBackLink(String backLink) {
        this.backLink = backLink;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    // toString method for debugging or logging
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
