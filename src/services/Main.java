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
                case 5 -> app.searchDoctor();
                case 6 -> app.getUpcomingAppointments();
                case 7 -> System.exit(1);
                case 8 -> app.requestAppointment();
                case 9 -> app.getAppointmentHistory();
                case 10 -> app.processAppointment();
                case 11 -> app.searchDrug();
                case 12 -> app.giveResponse();
            }

        }
    }

    private static void listCommands(){
        System.out.println("----Available commands----\n");
        System.out.println("--General commands:");
        System.out.println("0. Help");
        System.out.println("1. Sign up");
        System.out.println("2. Log in");
        System.out.println("3. Log out");
        System.out.println("4. Search Medical Center");
        System.out.println("5. Search Doctor");
        System.out.println("6. See your upcoming appointments");
        System.out.println("7. Exit");

        System.out.println("--Patients only commands:");
        System.out.println("8. Request an appointment");
        System.out.println("9. See your appointments history");

        System.out.println("--Staff only commands:");
        System.out.println("10. Process Appointment");
        System.out.println("11. Search Drug");
        System.out.println("12. Give response");
    }

}
