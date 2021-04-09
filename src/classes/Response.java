package classes;

import java.util.HashMap;

public class Response {

    private String description; // notes given by the doctor
    private HashMap<Drug, Integer> treatmentPlan; // Drug -> Time interval TODO: Drug -> Dosage + Time inerval
    private Bill bill;

    public Response(String description, HashMap<Drug, Integer> treatmentPlan, Bill bill) {
        this.description = description;
        this.treatmentPlan = treatmentPlan;
        this.bill = bill;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HashMap<Drug, Integer> getTreatmentPlan() {
        return treatmentPlan;
    }

    public void setTreatmentPlan(HashMap<Drug, Integer> treatmentPlan) {
        this.treatmentPlan = treatmentPlan;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    @Override
    public String toString() {
        return "Response{" +
                "description='" + description + '\'' +
                ", treatmentPlan=" + treatmentPlan.toString() +
                ", bill=" + bill.toString() +
                '}';
    }
}
