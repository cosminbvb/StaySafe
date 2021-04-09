package services;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        StaySafe app = StaySafe.getInstance();

        listCommands();

        Scanner input = new Scanner(System.in);
        while(true){

            System.out.println("Enter command code (enter 0 too look at them again):");
            int commandCode = Integer.parseInt(input.nextLine());

            switch (commandCode) {
                case 0 -> listCommands();
                case 1 -> app.signUp();
                case 2 -> app.logIn();
                case 3 -> app.logOut();
                case 4 -> app.searchCenter();
                case 5 -> app.getUpcomingAppointments();
                case 6 -> System.exit(1);
                case 7 -> app.requestAppointment();
                case 8 -> app.getAppointmentHistory();
                case 9 -> app.processAppointment();
            }

        }
    }

    private static void listCommands(){
        System.out.println("----Available commands----\n");
        System.out.println("General commands:\n");
        System.out.println("0. Help");
        System.out.println("1. Sign up");
        System.out.println("2. Log in");
        System.out.println("3. Log out");
        System.out.println("4. Search Medical Center");
        System.out.println("5. See your upcoming appointments");
        System.out.println("6. Exit");

        System.out.println("Patients only commands:\n");
        System.out.println("7. Request an appointment");
        System.out.println("8. See your appointments history");

        System.out.println("Staff only commands:\n");
        System.out.println("9. Process Appointment");
    }

}
