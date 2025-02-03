package model;

import java.util.Date;


public class ReservationDetail {
    private int detailID;
    private Date reservationDate;
    private Service service; // Associated service
    private Reservation reservation; // Associated reservation
    private boolean status; // Reservation status
     private Children child;

    public ReservationDetail() {
    }

    public ReservationDetail(int detailID, Date reservationDate, Service service, Reservation reservation, boolean status, Children child) {
        this.detailID = detailID;
        this.reservationDate = reservationDate;
        this.service = service;
        this.reservation = reservation;
        this.status = status;
        this.child = child;
    }

    public int getDetailID() {
        return detailID;
    }

    public void setDetailID(int detailID) {
        this.detailID = detailID;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Children getChild() {
        return child;
    }

    public void setChild(Children child) {
        this.child = child;
    }

    
}
