package classes;

import java.util.Date;

public class Doctor extends User {

    private String speciality;
    private double salary;
    private final Date hireDate; // TODO: convert to LocalDate

    public Doctor(String firstName, String lastName, long CNP, String dateOfBirth, String phoneNumber,
                  String emailAddress, String password, String speciality, double salary, Date hireDate) {
        super(firstName, lastName, CNP, dateOfBirth, phoneNumber, emailAddress, password);
        this.speciality = speciality;
        this.salary = salary;
        this.hireDate = hireDate;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", speciality='" + speciality + '\'' +
                ", hireDate=" + hireDate +
                '}';
    }
}
