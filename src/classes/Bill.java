package classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Bill {

    private int id;
    private List<Drug> drugs;
    private Patient patient;
    private Doctor doctor;
    private LocalDate date;

    private static int billCount = 0;

    // TODO: get total method

    public Bill(List<Drug> drugs, Patient patient, Doctor doctor, LocalDate date) {
        this.id = ++billCount;
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

    public List<Drug> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<Drug> drugs) {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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
