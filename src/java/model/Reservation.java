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

    /**
     *
     */
    public Reservation() {
    }

    /**
     *
     * @param reservationID
     * @param description
     * @param creationDate
     * @param customerID
     * @param totalPrice
     */
    public Reservation(int reservationID, String description, String creationDate, User customerID, float totalPrice) {
        this.reservationID = reservationID;
        this.description = description;
        this.creationDate = creationDate;
        this.customerID = customerID;
        this.totalPrice = totalPrice;
    }
    
    /**
     *
     * @return
     */
    public int getReservationID() {
        return reservationID;
    }

    /**
     *
     * @param reservationID
     */
    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     */
    public String getCreationDate() {
        return creationDate;
    }

    /**
     *
     * @param creationDate
     */
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    /**
     *
     * @return
     */
    public User getCustomerID() {
        return customerID;
    }

    /**
     *
     * @param customerID
     */
    public void setCustomerID(User customerID) {
        this.customerID = customerID;
    }

    /**
     *
     * @return
     */
    public float getTotalPrice() {
        return totalPrice;
    }

    /**
     *
     * @param totalPrice
     */
    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
   
}