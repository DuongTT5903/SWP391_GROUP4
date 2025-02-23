package model;

import java.util.Objects;

public class Post {
    private int id;
    private String title;
    private String detail;
    private String category;
    private boolean status;
    private String imageLink;
    private User author;

    public Post() {}

    public Post(int id, String title, String detail, String category, boolean status, String imageLink, User author) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.category = category;
        this.status = status;
        this.imageLink = imageLink;
        this.author = author;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public String getImageLink() { return imageLink; }
    public void setImageLink(String imageLink) { this.imageLink = imageLink; }

    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }

    // Trả về trạng thái dạng text (Active / Hidden)
    public String getStatusText() {
        return status ? "Active" : "Hidden";
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", status=" + getStatusText() +
                ", author=" + (author != null ? author.getName() : "Unknown") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {
    return Objects.hash(id);
    }
}