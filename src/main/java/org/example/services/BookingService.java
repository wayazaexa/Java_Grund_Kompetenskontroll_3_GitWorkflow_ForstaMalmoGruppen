package org.example.services;

import org.example.dto.BookingRepository;
import org.example.entities.*;
import org.example.factories.BookingFactory;
import org.example.factories.RepairFactory;
import org.example.factories.ServiceFactory;
import org.example.factories.VehicleInspectionFactory;
import org.example.store.BookingStore;
import org.example.store.NotificationRepo;
import java.time.LocalDateTime;
import java.util.List;

public class BookingService {
    private final BookingStore bookingStore;
    private final NotificationRepo notificationService;

    public BookingService(BookingStore bookingStore, NotificationRepo notificationService) {
        this.bookingStore = bookingStore;
        this.notificationService = notificationService;
    }

    public List<Booking> getAll() {
        return bookingStore.getAll();
    }

    public Booking findById(int id) {
        return bookingStore.findById(id);
    }

    public void createBooking(BookingType bookingType, Vehicle v, LocalDateTime date, String email) {
        BookingFactory factory = switch (bookingType) {
            case INSPECTION -> new VehicleInspectionFactory();
            case SERVICE -> new ServiceFactory();
            case REPAIR -> new RepairFactory();
        };

        var booking = factory.createBooking(new Vehicle(v.getRegNr(), v.getModel(), v.getYear()), date, email, bookingType);



        // store in memory
        bookingStore.add(booking);

        // send notification
        notificationService.notifyBookingEvent(booking, BookingStatus.BOOKED);

        System.out.println("Booking has been created" + booking);
    }

    public void update(Booking booking, BookingStatus bookingStatus) {
        bookingStore.update(booking);
    }

    public Booking delete(int id) {
        return bookingStore.delete(id);
    }

    public boolean existsCarNumber(String car) {
        for (Booking b : bookingStore.getAll()) {
            if (b.getVehicle().getRegNr().equalsIgnoreCase(car)) {
                return true;
            }
        }
        return false;
    }
}
