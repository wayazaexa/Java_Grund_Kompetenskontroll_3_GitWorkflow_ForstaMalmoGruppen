package org.example.services;

import org.example.dto.BookingRepository;
import org.example.entities.Booking;
import org.example.entities.BookingStatus;
import org.example.entities.BookingType;
import org.example.entities.Vehicle;
import org.example.factories.BookingFactory;
import org.example.factories.RepairFactory;
import org.example.factories.ServiceFactory;
import org.example.factories.VehicleInspectionFactory;
import org.example.store.BookingStore;
import org.example.store.NotificationRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BookingService {
    private final BookingRepository bookingStore;
    private final NotificationRepo notificationService;

    public BookingService(BookingRepository bookingStore, NotificationRepo notificationService) {
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
        var booking = factory.createBooking(new Vehicle(v.getRegNr(), v.getModel(), v.getYear()), date, email);



        // store in memory
        bookingStore.add(booking);

        // send notification
        notificationService.notifyBookingEvent(booking, BookingStatus.BOOKED);

        System.out.println("Booking has been created" + booking);
    }

    public void update(Booking booking) {
        Booking tmp = bookingStore.update(booking);
        if (tmp == null) {
            System.out.println("Booking could not be updated");
        }
        else {
            System.out.println("Booking with id " + tmp.getId() + " was updated");

            // send notification
            notificationService.notifyBookingEvent(booking, BookingStatus.DONE);
        }
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
