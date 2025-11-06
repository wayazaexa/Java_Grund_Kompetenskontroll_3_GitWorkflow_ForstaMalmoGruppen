package org.example.services;

import org.example.dto.BookingRepository;
import org.example.factories.VehicleFactory;
import org.example.factories.VehicleInspectionFactory;
import org.example.store.BookingStore;

import java.time.LocalDate;

public class InitializationService {
    public static BookingStore init() {
    BookingStore store = new BookingRepository();
    VehicleInspectionFactory vehicleInspectionFactory = new VehicleInspectionFactory();
    VehicleFactory vehicleFactory = new VehicleFactory();

    store.add(vehicleInspectionFactory.createBooking(
            vehicleFactory.createVehicle("ABC123", "VW Golf", 2005),
            LocalDate.of(2025, 11, 6),
            "kalle@mail.com"));
    store.add(vehicleInspectionFactory.createBooking(
            vehicleFactory.createVehicle("DEF456", "Toyota Corolla", 1991),
            LocalDate.of(2025, 11, 8),
            "jocke@mail.com"));
    store.add(vehicleInspectionFactory.createBooking(
            vehicleFactory.createVehicle("GHI78J", "Volvo 142", 1972),
            LocalDate.of(2025, 11, 8),
            "pelle@mail.com"));
    return store;
}
}
