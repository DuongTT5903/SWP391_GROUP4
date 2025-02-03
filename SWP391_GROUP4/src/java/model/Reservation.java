/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author admin
 */
public class Reservation {

    private int reservationID;
    private String description;
    private String creationDate;
    private User customerID;
    private float totalPrice;

    public Reservation() {
    }

    public Reservation(int reservationID, String description, String creationDate, User customerID, float totalPrice) {
        this.reservationID = reservationID;
        this.description = description;
        this.creationDate = creationDate;
        this.customerID = customerID;
        this.totalPrice = totalPrice;
    }
    

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public User getCustomerID() {
        return customerID;
    }

    public void setCustomerID(User customerID) {
        this.customerID = customerID;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
   
}