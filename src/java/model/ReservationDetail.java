package model;

import java.util.Date;

/**
 *
 * @author yugio
 */
public class ReservationDetail {
    private int detailID;
    private Date reservationDate;
    private Service service; // Associated service
    private Reservation reservation; // Associated reservation
    private boolean status; // Reservation status
     private Children child;

    /**
     *
     */
    public ReservationDetail() {
    }

    /**
     *
     * @param detailID
     * @param reservationDate
     * @param service
     * @param reservation
     * @param status
     * @param child
     */
    public ReservationDetail(int detailID, Date reservationDate, Service service, Reservation reservation, boolean status, Children child) {
        this.detailID = detailID;
        this.reservationDate = reservationDate;
        this.service = service;
        this.reservation = reservation;
        this.status = status;
        this.child = child;
    }

    /**
     *
     * @return
     */
    public int getDetailID() {
        return detailID;
    }

    /**
     *
     * @param detailID
     */
    public void setDetailID(int detailID) {
        this.detailID = detailID;
    }

    /**
     *
     * @return
     */
    public Date getReservationDate() {
        return reservationDate;
    }

    /**
     *
     * @param reservationDate
     */
    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    /**
     *
     * @return
     */
    public Service getService() {
        return service;
    }

    /**
     *
     * @param service
     */
    public void setService(Service service) {
        this.service = service;
    }

    /**
     *
     * @return
     */
    public Reservation getReservation() {
        return reservation;
    }

    /**
     *
     * @param reservation
     */
    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
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
    public Children getChild() {
        return child;
    }

    /**
     *
     * @param child
     */
    public void setChild(Children child) {
        this.child = child;
    }

    
}
