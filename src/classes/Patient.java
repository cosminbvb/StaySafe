package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

public class Patient extends User{

    private final TreeSet<Appointment> appointmentsHistory = new TreeSet<>();

    //TODO: add more attributes


    public Patient(String firstName, String lastName, long CNP, String dateOfBirth, String phoneNumber, String emailAddress, String password) {
        super(firstName, lastName, CNP, dateOfBirth, phoneNumber, emailAddress, password);
    }

    public void requestAppointment(ArrayList<MedicalCenter> centers, String description){
        //TODO - a patient can request an appointment by choosing a medical center (which will automatically assign a doctor)
        //TODO - which will be used to determine the doctor fit for the issue
    }

    public void addToHistory(Appointment appointment){
        appointmentsHistory.add(appointment);
    }

    public TreeSet<Appointment> getAppointmentsHistory() {
        return appointmentsHistory;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}
