package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

public class Patient extends User{

    private final TreeSet<Appointment> appointmentsHistory = new TreeSet<>();

    //TODO: add more attributes (e.g. pendingAppointments)


    public Patient(String firstName, String lastName, long CNP, String dateOfBirth, String phoneNumber, String emailAddress, String password) {
        super(firstName, lastName, CNP, dateOfBirth, phoneNumber, emailAddress, password);
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
