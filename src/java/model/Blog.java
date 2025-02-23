/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author admin
 */
public class Blog {

    private int blogID;
    private String blogTitle;
    private String blogDetail;
    private String Category;
    private boolean status;
    private String imageLink;
    private User author;

    /**
     *
     */
    public Blog() {
    }

    /**
     *
     * @param blogID
     * @param blogTitle
     * @param blogDetail
     * @param Category
     * @param status
     * @param imageLink
     */
    public Blog(int blogID, String blogTitle, String blogDetail, String Category, boolean status, String imageLink) {
        this.blogID = blogID;
        this.blogTitle = blogTitle;
        this.blogDetail = blogDetail;
        this.Category = Category;
        this.status = status;
        this.imageLink = imageLink;
    }

    /**
     *
     * @param blogID
     * @param blogTitle
     * @param blogDetail
     * @param Category
     * @param status
     * @param imageLink
     * @param author
     */
    public Blog(int blogID, String blogTitle, String blogDetail, String Category, boolean status, String imageLink, User author) {
        this.blogID = blogID;
        this.blogTitle = blogTitle;
        this.blogDetail = blogDetail;
        this.Category = Category;
        this.status = status;
        this.imageLink = imageLink;
        this.author = author;
    }

    /**
     *
     * @return
     */
    public int getBlogID() {
        return blogID;
    }

    /**
     *
     * @param blogID
     */
    public void setBlogID(int blogID) {
        this.blogID = blogID;
    }

    /**
     *
     * @return
     */
    public String getBlogTitle() {
        return blogTitle;
    }

    /**
     *
     * @param blogTitle
     */
    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    /**
     *
     * @return
     */
    public String getBlogDetail() {
        return blogDetail;
    }

    /**
     *
     * @param blogDetail
     */
    public void setBlogDetail(String blogDetail) {
        this.blogDetail = blogDetail;
    }

    /**
     *
     * @return
     */
    public String getCategory() {
        return Category;
    }

    /**
     *
     * @param Category
     */
    public void setCategory(String Category) {
        this.Category = Category;
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
    public String getImageLink() {
        return imageLink;
    }

    /**
     *
     * @param imageLink
     */
    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
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
    
    
    
}
