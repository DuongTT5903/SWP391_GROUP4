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

    public Blog() {
    }
public Blog(int blogID, String blogTitle, String blogDetail, String Category, boolean status, String imageLink) {
        this.blogID = blogID;
        this.blogTitle = blogTitle;
        this.blogDetail = blogDetail;
        this.Category = Category;
        this.status = status;
        this.imageLink = imageLink;
    }
    public Blog(int blogID, String blogTitle, String blogDetail, String Category, boolean status, String imageLink, User author) {
        this.blogID = blogID;
        this.blogTitle = blogTitle;
        this.blogDetail = blogDetail;
        this.Category = Category;
        this.status = status;
        this.imageLink = imageLink;
        this.author = author;
    }

    public int getBlogID() {
        return blogID;
    }

    public void setBlogID(int blogID) {
        this.blogID = blogID;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogDetail() {
        return blogDetail;
    }

    public void setBlogDetail(String blogDetail) {
        this.blogDetail = blogDetail;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
    
    
    
}
