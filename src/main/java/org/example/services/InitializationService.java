package org.example.services;

import org.example.dto.BookingRepository;
import org.example.entities.Booking;
import org.example.entities.BookingStatus;
import org.example.factories.RepairFactory;
import org.example.factories.ServiceFactory;
import org.example.factories.VehicleFactory;
import org.example.factories.VehicleInspectionFactory;
import org.example.store.BookingStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class InitializationService {
    private static final Logger log = LoggerFactory.getLogger(InitializationService.class);

    public static BookingStore init() {
        BookingStore store = new BookingRepository();
        VehicleInspectionFactory vehicleInspectionFactory = new VehicleInspectionFactory();
        RepairFactory repairFactory = new RepairFactory();
        VehicleFactory vehicleFactory = new VehicleFactory();
        ServiceFactory serviceFactory = new ServiceFactory();

        log.info("Populating store");
        Booking tmp = vehicleInspectionFactory.createBooking(
                vehicleFactory.createVehicle("ABC123", "VW Golf", 2005),
                LocalDate.of(2025, 12, 6).atStartOfDay(),
                "kalle@mail.com");
        tmp.setStatus(BookingStatus.CANCELLED);
        store.add(tmp);

        store.add(vehicleInspectionFactory.createBooking(
                vehicleFactory.createVehicle("DEF456", "Toyota Corolla", 1991),
                LocalDate.of(2025, 1, 5).atStartOfDay(),
                "jocke@mail.com"));
        store.add(vehicleInspectionFactory.createBooking(
                vehicleFactory.createVehicle("GHI78J", "Volvo 142", 1972),
                LocalDate.of(2025,6 , 8).atStartOfDay(),
                "pelle@mail.com"));
        tmp = serviceFactory.createBooking(
                vehicleFactory.createVehicle("HFR21F", "Toyota Rav4", 2022),
                LocalDate.of(2026,12, 2).atStartOfDay(),
                "david.davido@gmail.com");
        tmp.setStatus(BookingStatus.CONFIRMED);
        store.add(tmp);
        store.add(repairFactory.createBooking(
                vehicleFactory.createVehicle("BBL669", "Primavera", 2020),
                LocalDate.of(2025, 12, 18).atStartOfDay(),
                "axel.axelsson@yahoo.se",
                "Oil change"));
        tmp = repairFactory.createBooking(
                vehicleFactory.createVehicle("LOL001", "Bugatti", 2011),
                LocalDate.of(2026,1,1).atStartOfDay(),
                "omar.omarsson@icloud.com",
                "Engine swap");
        tmp.setStatus(BookingStatus.CANCELLED);
        store.add(tmp);
        return store;
    }
}
