package model;

import java.util.Date;
import java.util.List;

public class Reservation {

    private int reservationID;
    private String customerName;
    private String email;
    private String address;
    private String phone;
    private Date creationDate;
    private int userID;
    private int status;
    private int paymentMethod;
    private float totalPrice;
    private int acceptStatus;
    private Date BookingDate;
    private List<ReservationDetail> details; // Danh sách chi tiết đặt chỗ

    public Reservation() {
    }

    public void setAcceptStatus(int acceptStatus) {
        this.acceptStatus = acceptStatus;
    }

    public void setBookingDate(Date BookingDate) {
        this.BookingDate = BookingDate;
    }

    public int getAcceptStatus() {
        return acceptStatus;
    }

    public Date getBookingDate() {
        return BookingDate;
    }

    public Reservation(int reservationID, String customerName, String email, String address, String phone, Date creationDate, int userID, int status, int paymentMethod, float totalPrice) {
        this.reservationID = reservationID;
        this.customerName = customerName;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.creationDate = creationDate;
        this.userID = userID;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
    }

    // Getters và Setters
    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<ReservationDetail> getDetails() {
        return details;
    }

    public void setDetails(List<ReservationDetail> details) {
        this.details = details;
        calculateTotalPrice(); // Cập nhật tổng tiền
    }

    public void setReservationDetails(List<ReservationDetail> cartItems) {
        this.details = cartItems;
        calculateTotalPrice(); // Tự động cập nhật tổng tiền khi set danh sách chi tiết
    }

    // Phương thức tính tổng tiền đặt chỗ
    public void calculateTotalPrice() {
        float total = 0;
        if (details != null) {
            for (ReservationDetail item : details) {
                total += item.getPrice();
            }
        }
        this.totalPrice = total;
    }
}
