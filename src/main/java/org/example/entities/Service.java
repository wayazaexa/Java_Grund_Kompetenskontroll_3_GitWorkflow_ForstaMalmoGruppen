package org.example.entities;

import java.time.LocalDateTime;

public class Service extends Booking {

    public Service(Vehicle vehicle, LocalDateTime date, String email) {
        super(vehicle, date, email, BookingType.SERVICE);
        this.setPrice(calculatePrice(vehicle.getYear()));
    }

    private int calculatePrice(int year) {
        if (year >= 2020) return 1500;
        else if (year >= 2015) return 1800;
        else if (year >= 2010) return 2000;
        else if (year >= 2005) return 2300;
        else return 2800;
    }

    @Override
    public String toString() {
        return "Service - " +
                super.toString();
    }
}
