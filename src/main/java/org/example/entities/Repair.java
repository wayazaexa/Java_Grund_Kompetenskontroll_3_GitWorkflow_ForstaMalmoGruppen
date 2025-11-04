package org.example.entities;

public class Repair extends Booking {

    private String measure;

    public Repair(Vehicle vehicle, int date, int price, String email, boolean isReady, String measure) {
        super(vehicle, date, price, email, isReady);
        this.measure = measure;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    @Override
    public String toString() {
        return "Repair{" +
                "measure='" + measure + '\'' +
                '}';
    }
}
