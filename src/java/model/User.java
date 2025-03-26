/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author admin
 */
public class User {

    private int userID;
    private String name;
    private boolean gender;
    private String email;
    private String username;
    private String password;
    private String phone;
    private String role;
    private String imageURL;
    private UserStatus status;
    private Customer customer;
     private String  address;



    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }
    // Default constructor
    /**
     *
     */
    public User() {
    }

    /**
     *
     * @param userID
     */
    public User(int userID) {
        this.userID = userID;
    }

    public User(int userID, String name) {
        this.userID = userID;
        this.name = name;
    }

    public User(int userID, String name, String role) {
        this.userID = userID;
        this.name = name;
        this.role = role;
    }
    

    public User(int userID, String name, boolean gender, String email, String username, String password, String phone, String role, String imageURL, UserStatus status) {
        this.userID = userID;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.imageURL = imageURL;
        this.status = status;
    }
public User(String name, boolean gender, String email, String username, String password, String phone, String role, String imageURL) {
    this.name = name;
    this.gender = gender;
    this.email = email;
    this.username = username;
    this.password = password;
    this.phone = phone;
    this.role = role;
    this.imageURL = imageURL;
 
}
public User(String name, boolean gender, String email, String username, String password, String phone,String address) {
    this.name = name;
    this.gender = gender;
    this.email = email;
    this.username = username;
    this.password = password;
    this.phone = phone;
  this.address = address;
 
}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    // Parameterized constructor
    /**
     *
     * @param userID
     * @param name
     * @param gender
     * @param email
     * @param username
     * @param password
     * @param phone
     * @param role
     * @param imageURL
     */
    public User(int userID, String name, boolean gender, String email, String username, String password, String phone, String role, String imageURL) {
        this.userID = userID;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.imageURL = imageURL;
    }



    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    // Getters and setters
    /**
     *
     * @return
     */
    public int getUserID() {
        return userID;
    }

    /**
     *
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public boolean isGender() {
        return gender;
    }

    /**
     *
     * @param gender
     */
    public void setGender(boolean gender) {
        this.gender = gender;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     * @return
     */
    public String getRole() {
        return role;
    }

    /**
     *
     * @param role
     */
    public void setRole(String role) {
        this.role = role;
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

    // Override toString() for debugging or display purposes
}
