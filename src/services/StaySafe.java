package services;

import classes.*;
import com.sun.source.tree.Tree;

import java.util.*;

public class StaySafe {

    private final List<User> allUsers = new ArrayList<>();
    private final List<Patient> patients = new ArrayList<>();
    private final List<Doctor> doctors = new ArrayList<>();
    private final List<Nurse> nurses = new ArrayList<>();
    private final List<MedicalCenter> centers = new ArrayList<>();
    private final List<Drug> drugs = new ArrayList<>();
    private final List<Appointment> pendingAppointments = new ArrayList<>();
    //these represent the requested but not yet assigned to doctors and nurses appointments

    private static StaySafe instance = null;

    private User user = null;

    public static StaySafe getInstance(){
        if(instance == null)
            instance = new StaySafe();
        return instance;
    }

    private StaySafe(){
        initializeDatabase();
    }

    private void initializeDatabase(){
        Patient p1 = new Patient("Cosmin", "Petrescu", 5000000111111L, "05.09.2000",
                "0728000001", "cosmin.petrescu@my.fmi.unibuc.ro", "password1");
        Patient p2 = new Patient("Tudor", "Plescaru", 5000000111121L, "09.11.2000",
                "0728000002", "tudor.plescaru@my.fmi.unibuc.ro", "password2");
        Patient p3 = new Patient("Daniel", "Vlascenco", 5000000111131L, "29.01.2000",
                "0728000003", "daniel.vlascenco@my.fmi.unibuc.ro", "password3");

        patients.add(p1);
        patients.add(p2);
        patients.add(p3);
        allUsers.add(p1);
        allUsers.add(p2);
        allUsers.add(p3);

        Doctor d1 = new Doctor("Andrei", "Pantea", 5000000111141L, "05.09.2000",
                "0728000004", "andrei.pantea@doctor.ro", "password4", "Cardio",
                5000, new Date());
        Doctor d2 = new Doctor("Meredith", "Grey", 5000000111142L, "05.09.2000",
                "0728000005", "meredith.grey@doctor.ro", "password5", "Neuro",
                6000, new Date());

        doctors.add(d1);
        doctors.add(d2);
        allUsers.add(d1);
        allUsers.add(d2);

        Nurse n1 = new Nurse("Andreea", "Ionescu", 5000000111152L, "05.09.2000",
                "0728000006", "andreea.ionescu@nurse.ro", "password6", new Date(), 3000);

        Nurse n2 = new Nurse("Ion", "Popescu", 5000000111161L, "05.09.2000",
                "0728000007", "ion.popescu@nurse.ro", "password7", new Date(), 3000);

        nurses.add(n1);
        nurses.add(n2);
        allUsers.add(n1);
        allUsers.add(n2);

        MedicalCenter c1 = new MedicalCenter("Spitalul Judetean", "Ploiesti", "Strada Spitalului nr 1", 1000);

        MedicalCenter c2 = new MedicalCenter("Clinica Petrolul", "Ploiesti", "Bulevardul Republicii nr 21", 150);

        centers.add(c1);
        centers.add(c2);

        Drug drug1 = new Drug("Nurofan", "Pfozar", 20,
                "Nurofan 400 mg drajeuri calmează eficace un spectru larg de dureri acute.",
                new HashMap<String, Double>(){{
                    put("Ibuprofen", 400.0);
                    put("Zahar", 0.0);
                    put("Glucoza", 0.0);
                }});

        Drug drug2 = new Drug("Aspantar", "Pfozar", 30,
                """
                        Aspantar actioneaza prin prevenirea formarii cheagurilor de sange si este utilizat pentru:
                        prevenirea repetarii infarctului miocardic si a accidentului vascular cerebral """,
                new HashMap<String, Double>(){{
                    put("Acid acetilsalicilic", 75.0);
                    put("Celuloza microcristalina", 5.0);
                    put("Amidon pregelatinizat", 3.0);
                }});

        drugs.add(drug1);
        drugs.add(drug2);
    }

    public void signUp(){
        if(user != null){
            System.out.println("You are already logged in.\n");
            return;
        }
        System.out.println("Sign Up");
        Scanner input = new Scanner(System.in);
        System.out.println("Types of users: 1 - Patient, 2 - Doctor, 3 - Nurse\nEnter user code: ");
        int userCode = Integer.parseInt(input.nextLine());
        User newUser = null;

        // shared attributes:
        System.out.println("First name: ");
        String firstName = input.nextLine();
        System.out.println("Last name: ");
        String lastName = input.nextLine();
        System.out.println("CNP: ");
        long cnp = Long.parseLong(input.nextLine());
        System.out.println("Date of birth: ");
        String dateOfBirth = input.nextLine();
        System.out.println("Phone number: ");
        String phoneNumber = input.nextLine();
        System.out.println("Email: ");
        String email = input.nextLine();
        System.out.println("Password: ");
        String password = input.nextLine();

        if (userCode == 1){
            newUser = new Patient(firstName, lastName, cnp, dateOfBirth, phoneNumber, email, password);
            patients.add((Patient) newUser);
        }

        if (userCode == 2){
            System.out.println("Speciality: ");
            String speciality = input.nextLine();
            System.out.println("Salary: ");
            double salary = Double.parseDouble(input.nextLine());
            Date hireDate = new Date();
            newUser = new Doctor(firstName, lastName, cnp, dateOfBirth, phoneNumber, email, password,
                    speciality, salary, hireDate);
            doctors.add((Doctor) newUser);
        }

        if (userCode == 3){
            System.out.println("Salary: ");
            double salary = Double.parseDouble(input.nextLine());
            Date hireDate = new Date();
            newUser = new Nurse(firstName, lastName, cnp, dateOfBirth, phoneNumber, email, password, hireDate, salary);
            nurses.add((Nurse) newUser);
        }

        allUsers.add(newUser);

        // redirect to login
        logIn();

    }

    public void logIn(){
        if(user != null){
            System.out.println("You are already logged in.\n");
            return;
        }
        System.out.println("Log In");
        Scanner input = new Scanner(System.in);

        while(true) {
            System.out.println("Email: ");
            String email = input.nextLine();
            System.out.println("Password: ");
            String password = input.nextLine();

            User loggingIn = findUser(allUsers, email);
            if (loggingIn == null){
                System.out.println("This email doesn't exist\n");
            }
            else{
                boolean verified = verifyInfo(loggingIn, email, password);
                if(!verified){
                    System.out.println("Password incorrect\n");
                }
                else{
                    System.out.println("Welcome!\n");
                    user = loggingIn;
                    break;
                }
            }

        }
    }

    public void logOut(){
        if (user != null){
            user = null;
            System.out.println("Goodbye and stay safe!\n");
        }
    }

    public void searchCenter(){
        Scanner input = new Scanner(System.in);
        while (true){
            System.out.println("Enter the Medical Center's name: ");
            String name = input.nextLine();
            MedicalCenter mc = findCenter(name);
            if (mc == null){
                System.out.println("Center not found, press 0 to exit or 1 to try again");
                int command = Integer.parseInt(input.nextLine());
                if(command == 0)
                    break;
            }
            else{
                System.out.println(mc.toString()+"\n");
                break;
            }
        }
    }

    public void requestAppointment(){
        // users can request appointments which will be stored in pending appointments
        // until a nurse assigns it
        if (user == null) {
            System.out.println("You need to log in first (Press 2 to Log In or 1 to create a new account");
        }
        else if (user instanceof Doctor || user instanceof Nurse) {
                System.out.println("You need to log in with a Patient account");
        }
        else {
            Scanner input = new Scanner(System.in);
            MedicalCenter mc = null;
            while(true) {
                System.out.println("Enter the name of the preferred center:");
                String centerName = input.nextLine();
                mc = findCenter(centerName);
                if (mc != null){
                    break;
                }
                else{
                    System.out.println("Center not found. Press 0 to exit or 1 to try again");
                    int command = Integer.parseInt(input.nextLine());
                    if(command == 0){
                        return;
                    }
                }
            }
            System.out.println("Start by describing the issue:");
            String description = input.nextLine();

            Appointment requested = new Appointment((Patient) user, new Date(), mc, description);
            pendingAppointments.add(requested);
            System.out.println("Appointment requested. You will be contacted shortly, once your request has been processed\n");
            // this either has to be added to user upcoming appointments
            // or it has to be added once a nurse assigns it
            // for now, it is stored in pending
        }
    }

    public void getUpcomingAppointments(){
        // every type of user can see his upcoming appointments //
        if (user == null) {
            System.out.println("You need to log in first (Press 2 to Log In or 1 to create a new account");
        }
        else{
            TreeSet<Appointment> upcomingAppointments = user.getUpcomingAppointments();
            if(upcomingAppointments.size() == 0){
                System.out.println("You have no upcoming appointments\n");
            }
            else {
                System.out.println("Here are your upcoming appointments:\n");
                for (Appointment appointment : upcomingAppointments) {
                    System.out.println(appointment);
                }
            }
        }
    }

    public void getAppointmentHistory(){
        if (user == null) {
            System.out.println("You need to log in first (Press 2 to Log In or 1 to create a new account");
        }
        else if (user instanceof Doctor || user instanceof Nurse) {
            System.out.println("You need to log in with a Patient account");
        }
        else{
            TreeSet<Appointment> history = ((Patient) user).getAppointmentsHistory();
            if(history.size() == 0){
                System.out.println("You have no past appointments\n");
            }
            else{
                System.out.println("Here are you past appointments:\n");
                for(Appointment appointment : history){
                    System.out.println(appointment);
                }
            }
        }
    }

    public void processAppointment(){
        // Doctors and Nurses have the ability to process appointments
        // Meaning that a staff member will read the issue description and will
        // make an assignment based on the given description
        return;
    }

    // HELPERS:

    // find user by email:
    private User findUser(List<User> users, String email){
        for(User user : users){
            if(Objects.equals(user.getEmailAddress(), email))
                return user;
        }
        return null;
    }

    // verify login info:
    private boolean verifyInfo(User user, String email, String password){
        return Objects.equals(user.getEmailAddress(), email) && Objects.equals(user.getPassword(), password);
    }

    // find center by name:
    private MedicalCenter findCenter(String name){
        for(MedicalCenter center : centers){
            if(Objects.equals(center.getName().toLowerCase(Locale.ROOT), name.toLowerCase(Locale.ROOT)))
                return center;
        }
        return null;
    }

}
