package org.example.entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class VehicleInspection extends Booking {


    public VehicleInspection(int id, Vehicle vehicle, LocalTime time, LocalDate date, int price, String email, BookingStatus isReady) {
        super(id, vehicle, time, date, price, email, isReady);
    }
}
