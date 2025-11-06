package org.example.services;

import org.example.entities.Booking;
import org.example.entities.BookingStatus;
import org.example.entities.BookingType;
import org.example.entities.Vehicle;
import org.example.factories.BookingFactory;
import org.example.factories.RepairFactory;
import org.example.factories.ServiceFactory;
import org.example.factories.VehicleInspectionFactory;
import org.example.store.BookingStore;

import java.time.LocalDate;
import java.util.List;

public class BookingService {
    private final BookingStore bookingStore;
    private final NotificationService notificationService;

    public BookingService(BookingStore bookingStore, NotificationService notificationService) {
        this.bookingStore = bookingStore;
        this.notificationService = notificationService;
    }

    public List<Booking> getAll() {
        return bookingStore.getAll();
    }

    public Booking findById(int id) {
        return bookingStore.findById(id);
    }

    public Booking createBooking(BookingType bookingType, Vehicle vehicle, LocalDate date, String email) {
        BookingFactory factory = switch (bookingType) {
            case INSPECTION -> new VehicleInspectionFactory();
            case SERVICE -> new ServiceFactory();
            case REPAIR -> new RepairFactory();
        };
        var booking = factory.createBooking(vehicle, date, email);

        // store in memory
        bookingStore.add(booking);

        // send notification
        notificationService.notifyBookingEvent(booking, BookingStatus.BOOKED);

        return booking;
    }

    public void add(BookingType bookingType, Vehicle vehicle, LocalDate date, String email) {
        BookingFactory factory = switch (bookingType) {
            case INSPECTION -> new VehicleInspectionFactory();
            case SERVICE -> new ServiceFactory();
            case REPAIR -> new RepairFactory();
        };
        bookingStore.add(factory.createBooking(vehicle, date, email));
    }

    public Booking update(Booking booking) {
        return bookingStore.update(booking);
    }

    public void delete(int id) {
        bookingStore.delete(id);
    }
}
