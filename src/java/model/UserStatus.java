/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author yugio /** Represents the status of a user.
 */
public class UserStatus {

    private int userID;
    private Boolean status; // Changed to Boolean to handle nullable values

    /**
     * Constructor with parameters.
     *
     * @param userID Unique user identifier.
     * @param status User's active status (true = active, false = inactive).
     */
    public UserStatus(int userID, Boolean status) {
        this.userID = userID;
        this.status = status;
    }

    /**
     * Default constructor.
     */
    public UserStatus() {
        this.status = false; // Default value
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * Returns whether the user is active.
     *
     * @return true if active, false otherwise.
     */
    public boolean isActive() {
        return Boolean.TRUE.equals(status); // Ensures null is treated as false
    }
}
