package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.TreeSet;

public abstract class User {

    protected long id;
    protected String firstName, lastName;
    protected long CNP;
    protected String dateOfBirth;
    protected String phoneNumber;
    protected String emailAddress; // unique
    protected String password;
    protected final TreeSet<Appointment> upcomingAppointments = new TreeSet<>();

    protected static long userCount = 0;

    public User(String firstName, String lastName, long CNP, String dateOfBirth, String phoneNumber, String emailAddress, String password) {
        this.id = ++userCount;
        this.firstName = firstName;
        this.lastName = lastName;
        this.CNP = CNP;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public void addUpcomingAppointment(Appointment appointment){
        upcomingAppointments.add(appointment);
    }

    public TreeSet<Appointment> getUpcomingAppointments() {
        return upcomingAppointments;
    }

    public long getId() {
        return id;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getCNP() {
        return CNP;
    }

    public void setCNP(long CNP) {
        this.CNP = CNP;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
