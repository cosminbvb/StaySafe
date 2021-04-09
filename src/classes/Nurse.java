package classes;

import java.util.Date;

public class Nurse extends User{

    private final Date hireDate;
    private double salary;

    //TODO: add more attributes

    public Nurse(String firstName, String lastName, long CNP, String dateOfBirth, String phoneNumber,
                 String emailAddress, String password, Date hireDate, double salary) {
        super(firstName, lastName, CNP, dateOfBirth, phoneNumber, emailAddress, password);
        this.hireDate = hireDate;
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getHireDate() {
        return hireDate;
    }

    @Override
    public String toString() {
        return "Nurse{" +
                ", id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                "hireDate=" + hireDate +
                ", salary=" + salary +
                '}';
    }
}
