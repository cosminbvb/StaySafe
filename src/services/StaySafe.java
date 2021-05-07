package services;

import classes.*;

import javax.print.Doc;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StaySafe {

    CSV csv = CSV.getInstance();

    private List<User> allUsers = new ArrayList<>();
    private List<Patient> patients = new ArrayList<>();
    private List<Doctor> doctors = new ArrayList<>();
    private List<Nurse> nurses = new ArrayList<>();
    private List<MedicalCenter> centers = new ArrayList<>();
    private List<Drug> drugs = new ArrayList<>();
    private final TreeSet<Appointment> pendingAppointments = new TreeSet<>();
    //these represent the requested but not yet assigned to doctors and nurses appointments
    //fifo => they need to be ordered by date

    private static StaySafe instance = null;

    private User user = null;

    public static StaySafe getInstance(){
        if(instance == null)
            instance = new StaySafe();
        return instance;
    }

    private StaySafe(){
        // initializeDatabase();
        loadDatabase();
    }

    private void initializeDatabase(){
        Patient p1 = new Patient(1,"Cosmin", "Petrescu", 5000000111111L, "05.09.2000",
                "0728000001", "cosmin.petrescu@my.fmi.unibuc.ro", "password1");
        Patient p2 = new Patient(2, "Tudor", "Plescaru", 5000000111121L, "09.11.2000",
                "0728000002", "tudor.plescaru@my.fmi.unibuc.ro", "password2");
        Patient p3 = new Patient(3, "Daniel", "Vlascenco", 5000000111131L, "29.01.2000",
                "0728000003", "daniel.vlascenco@my.fmi.unibuc.ro", "password3");

        patients.add(p1);
        patients.add(p2);
        patients.add(p3);
        allUsers.add(p1);
        allUsers.add(p2);
        allUsers.add(p3);

        for (Patient p : patients){
            CSV.writeCSV("patients.csv", p.getCSV());
        }

        Doctor d1 = new Doctor(4, "Andrei", "Pantea", 5000000111141L, "05.09.2000",
                "0728000004", "andrei.pantea@doctor.ro", "password4", "Cardio",
                5000, LocalDate.now());
        Doctor d2 = new Doctor(5, "Meredith", "Grey", 5000000111142L, "05.09.2000",
                "0728000005", "meredith.grey@doctor.ro", "password5", "Neuro",
                6000, LocalDate.now());

        doctors.add(d1);
        doctors.add(d2);
        allUsers.add(d1);
        allUsers.add(d2);

        for (Doctor d : doctors){
            CSV.writeCSV("doctors.csv", d.getCSV());
        }

        Nurse n1 = new Nurse(6, "Andreea", "Ionescu", 5000000111152L, "05.09.2000",
                "0728000006", "andreea.ionescu@nurse.ro", "password6", LocalDate.now(), 3000);

        Nurse n2 = new Nurse(7, "Ion", "Popescu", 5000000111161L, "05.09.2000",
                "0728000007", "ion.popescu@nurse.ro", "password7", LocalDate.now(), 3000);

        nurses.add(n1);
        nurses.add(n2);
        allUsers.add(n1);
        allUsers.add(n2);


        for (Nurse n : nurses){
            CSV.writeCSV("nurses.csv", n.getCSV());
        }


        MedicalCenter c1 = new MedicalCenter(1,"Spitalul Judetean", "Ploiesti", "Strada Spitalului nr 1", 1000);

        MedicalCenter c2 = new MedicalCenter(2,"Clinica Petrolul", "Ploiesti", "Bulevardul Republicii nr 21", 150);

        centers.add(c1);
        centers.add(c2);


        for(MedicalCenter mc : centers){
            CSV.writeCSV("centers.csv", mc.getCSV());
        }


        Drug drug1 = new Drug(1, "Nurofan", "Pfozar", 20,
                "Nurofan 400 mg drajeuri calmează eficace un spectru larg de dureri acute.",
                new HashMap<String, Double>(){{
                    put("Ibuprofen", 400.0);
                    put("Zahar", 0.0);
                    put("Glucoza", 0.0);
                }});

        Drug drug2 = new Drug(2, "Aspantar", "Pfozar", 30,
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

        for (Drug d : drugs){
            CSV.writeCSV("drugs.csv", d.getCSV());
            for (Map.Entry<String, Double> entry : d.getIngredients().entrySet()){
                String data = d.getId() + "," + entry.getKey() + "," + entry.getValue();
                CSV.writeCSV("drugs_ingredients.csv", data);
            }
        }

    }

    private void loadDatabase(){
        List<String []> data = CSV.readCSV("patients.csv");
        patients = data.stream().map(line -> new Patient(Long.parseLong(line[0]),
                line[1], line[2], Long.parseLong(line[3]), line[4], line[5], line[6], line[7]))
                .collect(Collectors.toList());
        // build the objects and put them in the list

        long userLastId = patients.get(patients.size()-1).getId(); // remembering the last id (also the biggest one)

        data = CSV.readCSV("doctors.csv");
        doctors = data.stream().map(line -> new Doctor(Long.parseLong(line[0]), line[1],
                line[2], Long.parseLong(line[3]), line[4], line[5], line[6], line[7],
                line[8], Double.parseDouble(line[9]), LocalDate.parse(line[10])))
                .collect(Collectors.toList());

        userLastId = Math.max(userLastId, doctors.get(doctors.size()-1).getId());
        // since all the users share the same id generator, we need to find the biggest id
        // towards all types of users

        data = CSV.readCSV("nurses.csv");
        nurses = data.stream().map(line -> new Nurse(Long.parseLong(line[0]), line[1],
                line[2], Long.parseLong(line[3]), line[4], line[5], line[6], line[7],
                LocalDate.parse(line[9]), Double.parseDouble(line[8])))
                .collect(Collectors.toList());

        userLastId = Math.max(userLastId, nurses.get(nurses.size()-1).getId());
        User.setNextId(userLastId + 1); // set the next User id

        // finally, concatenate all types of users into a single additional list
        allUsers = Stream.concat(patients.stream(), doctors.stream()).collect(Collectors.toList());
        allUsers = Stream.concat(allUsers.stream(), nurses.stream()).collect(Collectors.toList());


        data = CSV.readCSV("centers.csv");
        centers = data.stream().map(line -> new MedicalCenter(Long.parseLong(line[0]),
                line[1], line[2], line[3], Long.parseLong(line[4])))
                .collect(Collectors.toList());

        MedicalCenter.setNextId(centers.get(centers.size()-1).getId() + 1);


        data = CSV.readCSV("drugs.csv");
        List<String []> ingredientsData = CSV.readCSV("drugs_ingredients.csv");
        for (String[] line : data){
            long id = Long.parseLong(line[0]);
            List<String []> ingredients = ingredientsData.stream().filter(l -> Long.parseLong(l[0]) == id)
                    .collect(Collectors.toList());
            // now ingredients is a list of string arrays, each array containing a drug id an ingredient name and the quantity
            HashMap<String, Double> ingredientsMap = new HashMap<>();
            // build the ingredient -> quantity map
            for (String[] ingr : ingredients){
                String name = ingr[1];
                Double quantity = Double.parseDouble(ingr[2]);
                ingredientsMap.put(name, quantity);
            }
            // build the drug and add it to the list
            Drug drug = new Drug(id, line[1], line[2], Double.parseDouble(line[3]), line[4], ingredientsMap);
            drugs.add(drug);
        }
        Drug.setNextId(Long.parseLong(data.get(data.size()-1)[0]) + 1);


        data = CSV.readCSV("appointments.csv");
        List<String []> responses = CSV.readCSV("responses.csv");
        List<String[]> bills = CSV.readCSV("bills.csv");
        List<String[]> treatments = CSV.readCSV("response_treatments.csv");
        List<String[]> billsDrugs = CSV.readCSV("bill_drugs.csv");
        for (String[] line : data){
            long id = Long.parseLong(line[0]);

            long patientId = Long.parseLong(line[2]);
            String status = line[1];
            Patient patient = (Patient) findUserById(allUsers, patientId);

            long centerId = Long.parseLong(line[6]);
            MedicalCenter mc = findMedicalCenterById(centers, centerId);

            Appointment appointment = new Appointment(id, patient, LocalDate.parse(line[5]), mc, line[7], status);

            Doctor doctor = null;
            Nurse nurse = null;
            Response response = null;

            if (status.equals("assigned") || status.equals("completed")){
                long doctorId = Long.parseLong(line[3]);
                doctor = (Doctor) findUserById(allUsers, doctorId);
                long nurseId = Long.parseLong(line[4]);
                nurse = (Nurse) findUserById(allUsers, nurseId);
                appointment.setDoctor(doctor);
                appointment.setNurse(nurse);
                if (status.equals("assigned")){
                    doctor.addUpcomingAppointment(appointment);
                    nurse.addUpcomingAppointment(appointment);
                    patient.addUpcomingAppointment(appointment);
                }
            }
            else{
                pendingAppointments.add(appointment);
            }
            if (status.equals("completed")){
                // read response with the coresponding id
                long responseId = Long.parseLong(line[9]);
                String[] resp = responses.stream().filter(x -> Long.parseLong(x[0]) == responseId).collect(Collectors.toList()).get(0);

                // build the treatment plan:
                List<String []> plan = treatments.stream().filter(l -> Long.parseLong(l[0]) == responseId)
                        .collect(Collectors.toList());
                HashMap<Drug, Integer> treatmentPlan = new HashMap<>();
                // build the ingredient -> quantity map
                for (String[] p : plan){
                    long drugId = Long.parseLong(p[1]);
                    Drug drug = findDrugByID(drugs, drugId);
                    Integer interval = Integer.parseInt(p[2]);
                    treatmentPlan.put(drug, interval);
                }

                // in order to construct the response, we first have to build the bill
                // and the treatment plan
                long billId = Long.parseLong(resp[2]);
                String[] bill = bills.stream().filter(x -> Long.parseLong(x[0]) == billId).collect(Collectors.toList()).get(0);

                // build the drug list for the bill
                List<Drug> billDrugList = new ArrayList<>();
                List<String[]> drugsStrings = billsDrugs.stream().filter(l -> Long.parseLong(l[0]) == billId)
                        .collect(Collectors.toList());
                for(String[] d : drugsStrings){
                    Drug drug = findDrugByID(drugs, Long.parseLong(d[1]));
                    billDrugList.add(drug);
                }

                Bill finalBill = new Bill(Long.parseLong(bill[0]), billDrugList, LocalDate.parse(bill[2]));

                response = new Response(responseId, resp[1], treatmentPlan, finalBill);
                appointment.setResponse(response);

                patient.addToHistory(appointment);
            }
        }
        // set the next id for each class
        if (data.size() > 0)
            Appointment.setNextId(Long.parseLong(data.get(data.size()-1)[0]) + 1);
        else
            Appointment.setNextId(1);
        if (responses.size() > 0)
            Response.setNextId(Long.parseLong(responses.get(responses.size()-1)[0]) + 1);
        else
            Response.setNextId(1);
        if (bills.size() > 0)
            Bill.setNextId(Long.parseLong(bills.get(bills.size()-1)[0]) + 1);
        else
            Bill.setNextId(1);
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
            newUser = new Patient(User.getNextId(), firstName, lastName, cnp, dateOfBirth, phoneNumber, email, password);
            patients.add((Patient) newUser);
            CSV.writeCSV("patients.csv", ((Patient) newUser).getCSV());
        }

        if (userCode == 2){
            System.out.println("Speciality: ");
            String speciality = input.nextLine();
            System.out.println("Salary: ");
            double salary = Double.parseDouble(input.nextLine());
            LocalDate hireDate = LocalDate.now();
            newUser = new Doctor(User.getNextId(), firstName, lastName, cnp, dateOfBirth, phoneNumber, email, password,
                    speciality, salary, hireDate);
            doctors.add((Doctor) newUser);
            CSV.writeCSV("doctors.csv", ((Doctor) newUser).getCSV());
        }

        if (userCode == 3){
            System.out.println("Salary: ");
            double salary = Double.parseDouble(input.nextLine());
            LocalDate hireDate = LocalDate.now();
            newUser = new Nurse(User.getNextId(), firstName, lastName, cnp, dateOfBirth, phoneNumber, email, password, hireDate, salary);
            nurses.add((Nurse) newUser);
            CSV.writeCSV("nurses.csv", ((Nurse) newUser).getCSV());
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
            System.out.println("You need to log in first (Press 2 to Log In or 1 to create a new account)");
        }
        else if (user instanceof Doctor || user instanceof Nurse) {
                System.out.println("You need to log in with a Patient account");
        }
        else {
            Scanner input = new Scanner(System.in);
            MedicalCenter mc;
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

            Appointment requested = new Appointment(Appointment.getNextId(), (Patient) user, LocalDate.now(), mc, description, "pending");
            pendingAppointments.add(requested);
            System.out.println("Appointment requested. You will be contacted shortly, once your request has been processed\n");
            CSV.writeCSV("appointments.csv", requested.getCSV());
        }
    }

    public void getUpcomingAppointments(){
        // every type of user can see his upcoming appointments //
        if (user == null) {
            System.out.println("You need to log in first (Press 2 to Log In or 1 to create a new account)");
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
            System.out.println("You need to log in first (Press 2 to Log In or 1 to create a new account)");
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
        if (user == null) {
            System.out.println("You need to log with a staff member account\n");
        }
        else if (user instanceof Patient) {
            System.out.println("Patients not allowed\n");
        }
        else{
            Scanner input = new Scanner(System.in);
            Appointment appointment = pendingAppointments.pollFirst();
            if(appointment == null){
                System.out.println("There are no requested appointments\n");
                return;
            }
            String oldData = appointment.getCSV(); // needed when updating the CSV
            Patient patient = appointment.getPatient();
            System.out.printf("Patient: %s %s\nDescription: %s\n%n", patient.getFirstName(),
                    patient.getLastName(), appointment.getPatientIssueDescription());
            System.out.println("You must assign a doctor");
            while (true){
                System.out.println("Doctor's first name: ");
                String firstName = input.nextLine();
                System.out.println("Doctor's last name: ");
                String lastName = input.nextLine();
                Doctor doc = findDoctor(firstName, lastName);
                if (doc == null){
                    System.out.println("Doctor not found. Press 1 to try again or 0 to abort request processing");
                    int command = Integer.parseInt(input.nextLine());
                    if(command == 0){
                        System.out.println("Request processing aborted\n");
                        pendingAppointments.add(appointment); // add appointment back in line
                        return;
                    }
                }
                else{
                    appointment.setDoctor(doc); // assign doctor to the current appointment
                    doc.addUpcomingAppointment(appointment); // insert it into the doctor's schedule
                    break;
                }
            }
            appointment.setNurse((Nurse) user); // assign nurse to the current appointment
            user.addUpcomingAppointment(appointment); // assuming that the nurse that is processing the request will attend it

            System.out.println("Set a date (YYYY-MM-DD):");
            String stringDate = input.nextLine();
            LocalDate date = LocalDate.parse(stringDate);
            appointment.setDate(date); // set the scheduled date of the appointment

            patient.addUpcomingAppointment(appointment); // insert it into the patient's schedule

            appointment.setStatus("assigned");
            System.out.println(appointment.hashCode());

            // update the appointment details in the csv file
            try {
                CSV.updateCSV("appointments.csv", oldData, appointment.getCSV());
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("The request has been processed.");
            System.out.println(appointment.toString());

        }
    }

    public void searchDoctor(){
        Scanner input = new Scanner(System.in);
        while (true){
            System.out.println("Enter first name: ");
            String firstName = input.nextLine();
            System.out.println("Enter last name: ");
            String lastName = input.nextLine();
            Doctor doc = findDoctor(firstName, lastName);
            if (doc == null){
                System.out.println("Doctor not found. Press 0 to exit or 1 to try again");
                int command = Integer.parseInt(input.nextLine());
                if(command == 0)
                    break;
            }
            else{
                System.out.println(doc.toString()+"\n");
                break;
            }
        }
    }

    public void searchDrug(){
        if (user == null) {
            System.out.println("You need to log with a staff member account\n");
        }
        else if (user instanceof Patient) {
            System.out.println("Patients not allowed\n");
        }
        else{
            Scanner input = new Scanner(System.in);
            while (true){
                System.out.println("Enter drug name: ");
                String name = input.nextLine();
                Drug drug = findDrug(name);
                if (drug == null){
                    System.out.println("Drug not found. Press 0 to exit or 1 to try again");
                    int command = Integer.parseInt(input.nextLine());
                    if(command == 0)
                        break;
                }
                else{
                    System.out.println(drug.toString()+"\n");
                    break;
                }
            }
        }
    }

    public void giveResponse(){
        // Intended for staff members - here they can give a treatment and leave notes for the patient
        // This was designed for a f2f situation
        if (user == null) {
            System.out.println("You need to log with a staff member account\n");
        }
        else if (user instanceof Patient){
            System.out.println("Patients not allowed\n");
        }
        else{
            // first we need get the most recent appointment
            Appointment currentAppointment = user.getUpcomingAppointments().pollFirst();
            if (currentAppointment == null){
                System.out.println("No upcoming appointments");
                return;
            }
            String oldData = currentAppointment.getCSV(); // needed when updating the CSV
            // construct a response:
            Scanner input = new Scanner(System.in);
            System.out.println("Enter description: ");
            String description = input.nextLine();
            HashMap<Drug, Integer> treatmentPlan = new HashMap<>();
            List<Drug> givenDrugs = new ArrayList<>(); // for the bill

            while (true){
                System.out.println("Drug name: ");
                String name = input.nextLine();
                Drug drug = findDrug(name);
                if (drug == null){
                    System.out.println("Drug not found, try again");
                }
                else{
                    System.out.println("Enter hour interval:");
                    int interval = Integer.parseInt(input.nextLine());
                    treatmentPlan.put(drug, interval);
                    givenDrugs.add(drug);
                    System.out.println("Press 0 to add another drug or 1 to finish");
                    int command = Integer.parseInt(input.nextLine());
                    if(command == 1)
                        break;
                }
            }

            // construct the bill
            Bill bill = new Bill(Bill.getNextId(), givenDrugs, LocalDate.now());

            for(Drug drug : givenDrugs){
                CSV.writeCSV("bill_drugs.csv", bill.getId() + "," + drug.getId());
            }
            CSV.writeCSV("bills.csv", bill.getCSV());

            Response response = new Response(Response.getNextId(), description, treatmentPlan, bill);

            for(Map.Entry<Drug, Integer> e : treatmentPlan.entrySet()){
                CSV.writeCSV("response_treatments.csv", response.getId() + "," + e.getKey().getId() + "," + e.getValue());
            }
            CSV.writeCSV("responses.csv", response.getCSV());

            // add all the response the current appointment
            currentAppointment.setResponse(response);


            currentAppointment.setStatus("completed");

            // update the appointment details in the csv file
            try {
                CSV.updateCSV("appointments.csv", oldData, currentAppointment.getCSV());
            } catch (IOException e) {
                e.printStackTrace();
            }


            // remove the appointment from the upcoming set for the doctor, nurse and patient
            // add it to the patient's history
            Patient patient = currentAppointment.getPatient();
            Doctor doctor = currentAppointment.getDoctor();
            Nurse nurse = currentAppointment.getNurse();
            patient.getUpcomingAppointments().remove(currentAppointment);
            patient.getAppointmentsHistory().add(currentAppointment);
            doctor.getUpcomingAppointments().remove(currentAppointment);
            nurse.getUpcomingAppointments().remove(currentAppointment);

            //TODO: works but need further testing

            System.out.println("Response submitted.\n");

        }
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

    // find doctor by name:
    private Doctor findDoctor(String firstName, String lastName){
        for(Doctor doc : doctors){
            //TODO: search after transforming to lowercase
            if(Objects.equals(doc.getFirstName(), firstName) && Objects.equals(doc.getLastName(), lastName))
                return doc;
        }
        return null;
    }

    // find drug by name:
    private Drug findDrug(String name){
        for(Drug drug : drugs){
            //TODO: search after transforming to lowercase
            if(Objects.equals(drug.getName(), name))
                return drug;
        }
        return null;
    }

    private User findUserById(List<User> users, long id){
        for(User user : users){
            if(user.getId() == id)
                return user;
        }
        return null;
    }

    private MedicalCenter findMedicalCenterById(List<MedicalCenter> list, long id){
        for(MedicalCenter m : list){
            if(m.getId() == id)
                return m;
        }
        return null;
    }

    private Drug findDrugByID(List<Drug> drugs, long id){
        for(Drug d : drugs){
            if (d.getId() == id){
                return d;
            }
        }
        return null;
    }

}
