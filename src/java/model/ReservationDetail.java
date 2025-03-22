package model;

/**
 *
 * @author yugio
 */
public class ReservationDetail {
    private int detailID;
    private int revationID;
    private Service service; // Associated service
    private int amount;
    private int numberOfPerson;

    public ReservationDetail() {
    }

    public ReservationDetail(int detailID, int revationID, Service service, int amount, int numberOfPerson) {
        this.detailID = detailID;
        this.revationID = revationID;
        this.service = service;
        this.amount = amount;
        this.numberOfPerson = numberOfPerson;
    }

    public int getDetailID() {
        return detailID;
    }

    public void setDetailID(int detailID) {
        this.detailID = detailID;
    }

    public int getRevationID() {
        return revationID;
    }

    public void setRevationID(int revationID) {
        this.revationID = revationID;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getNumberOfPerson() {
        return numberOfPerson;
    }

    public void setNumberOfPerson(int numberOfPerson) {
        this.numberOfPerson = numberOfPerson;
    }
public float getPrice() {
  return service != null ? service.getServicePrice() * service.getSalePrice() * amount : 0;

}

    /**
     *
     */
}
