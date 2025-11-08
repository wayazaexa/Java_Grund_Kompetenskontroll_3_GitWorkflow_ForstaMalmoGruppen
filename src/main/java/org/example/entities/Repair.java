package org.example.entities;

import java.time.LocalDateTime;

public class Repair extends Booking {

    private String measure;

    public Repair(Vehicle vehicle, LocalDateTime date, String email, String measure) {
        super(vehicle,date, email, BookingType.REPAIR);
        this.measure = measure;
        this.setBookingType(BookingType.REPAIR);
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    @Override
    public String toString() {
        return "Repair - " +
                "id: " + getId() +
                ", vehicle: [ " + getVehicle() + " ]" +
                ", date: " + getDate() +
                ", email: '" + getEmail() + '\'' +
                ", status: " + getStatus() +
                ", measure: '" + getMeasure() + '\'';
    }
}
