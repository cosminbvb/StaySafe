package classes;

import java.util.ArrayList;
import java.util.Date;

public class Appointment implements Comparable<Appointment>{

    private Patient patient;
    private Doctor doctor; // or maybe an array too
    private ArrayList<Nurse> nurses;
    private Date date;
    private MedicalCenter medicalCenter;
    private String patientIssueDescription;
    private Response response;

    public Appointment(Patient patient, Date date, MedicalCenter medicalCenter, String patientIssueDescription) {
        this.patient = patient;
        this.date = date;
        this.medicalCenter = medicalCenter;
        this.patientIssueDescription = patientIssueDescription;
    }

    public String getPatientIssueDescription() {
        return patientIssueDescription;
    }

    public void setPatientIssueDescription(String patientIssueDescription) {
        this.patientIssueDescription = patientIssueDescription;
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

    public ArrayList<Nurse> getNurses() {
        return nurses;
    }

    public void setNurses(ArrayList<Nurse> nurses) {
        this.nurses = nurses;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public MedicalCenter getMedicalCenter() {
        return medicalCenter;
    }

    public void setMedicalCenter(MedicalCenter medicalCenter) {
        this.medicalCenter = medicalCenter;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "patient=" + patient.toString() +
                ", doctor=" + doctor.toString() +
                ", nurses=" + nurses.toString() +
                ", date=" + date +
                ", medicalCenter=" + medicalCenter.toString() +
                ", response=" + response.toString() +
                '}';
    }

    @Override
    public int compareTo(Appointment o) {
        return this.date.compareTo(o.getDate());
    }
}
