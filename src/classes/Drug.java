package classes;

import java.util.HashMap;
import java.util.Objects;

public class Drug {

    private String name;
    private String manufacturer;
    private double price;
    private String prospect;
    private HashMap<String, Double> ingredients;

    //TODO: list of side effects

    public Drug(String name, String manufacturer, double price, String prospect, HashMap<String, Double> ingredients) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.price = price;
        this.prospect = prospect;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public HashMap<String, Double> getIngredients() {
        return ingredients;
    }

    public void setIngredients(HashMap<String, Double> ingredients) {
        this.ingredients = ingredients;
    }

    public String getProspect() {
        return prospect;
    }

    public void setProspect(String prospect) {
        this.prospect = prospect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drug drug = (Drug) o;
        return Double.compare(drug.price, price) == 0 && Objects.equals(name, drug.name) &&
                Objects.equals(manufacturer, drug.manufacturer) && Objects.equals(prospect, drug.prospect) &&
                Objects.equals(ingredients, drug.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, manufacturer, price, prospect, ingredients);
    }

    @Override
    public String toString() {
        return "Drug{" +
                "name='" + name + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", price=" + price +
                ", ingredients=" + ingredients.toString() +
                '}';
    }
}
