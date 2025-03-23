package model;

import java.util.Date;
import java.util.List;

public class MedicalExamination {
    private int examinationId;
    private Reservation reservation;
    private String prescription;
    private Date creationDate;
    private int doctorId;
    private List<Service> services; // Danh sách dịch vụ liên quan

    // Getters and Setters
    public int getExaminationId() { return examinationId; }
    public void setExaminationId(int examinationId) { this.examinationId = examinationId; }
    public Reservation getReservation() { return reservation; }
    public void setReservation(Reservation reservation) { this.reservation = reservation; }
    public String getPrescription() { return prescription; }
    public void setPrescription(String prescription) { this.prescription = prescription; }
    public Date getCreationDate() { return creationDate; }
    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }
    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
    public List<Service> getServices() { return services; }
    public void setServices(List<Service> services) { this.services = services; }
}