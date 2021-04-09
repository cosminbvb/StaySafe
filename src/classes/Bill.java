package classes;

import java.util.ArrayList;
import java.util.Date;

public class Bill {

    private int id;
    private ArrayList<Drug> drugs;
    private Patient patient;
    private Doctor doctor;
    private Date date;

    public Bill(int id, ArrayList<Drug> drugs, Patient patient, Doctor doctor, Date date) {
        this.id = id;
        this.drugs = drugs;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Drug> getDrugs() {
        return drugs;
    }

    public void setDrugs(ArrayList<Drug> drugs) {
        this.drugs = drugs;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", drugs=" + drugs.toString() +
                ", patient=" + patient.toString() +
                ", doctor=" + doctor.toString() +
                ", date=" + date +
                '}';
    }
}
