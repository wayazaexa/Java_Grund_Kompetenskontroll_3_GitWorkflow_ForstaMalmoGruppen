package org.example.services;

import org.example.dto.BookingRepository;
import org.example.entities.Booking;
import org.example.entities.BookingStatus;
import org.example.entities.Vehicle;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class BookingService {

    private final BookingRepository bookingRepository;
    private final NotificationService notificationService;
    // Holds all already-booked time slots (date + time) so we can quickly check availability
    private final Set<LocalDateTime> bookedSlots = new HashSet<>();
    public BookingService(BookingRepository bookingRepository, NotificationService notificationService) {
        this.bookingRepository = bookingRepository;
        this.notificationService = notificationService;
    }

    /**
     * Create a new booking:
     *
     * @param date, check slot availability
     * @param time, check time availability
     * @param v     create booking with new ID
     * @param email send confirmation email
     *
     *
     */
    public Booking createBooking(LocalDate date, LocalTime time, Vehicle v, String email) {
//       slot already taken → return null to indicate failure
        LocalDateTime slot = LocalDateTime.of(date, time);
        if (bookedSlots.contains(slot)) {
            return null;
        }
        // generate unique ID
        int id = 1;
        int price = 0;
        var booking = new Booking(++id,
                new Vehicle(v.getRegNr(), v.getModel(), v.getYear()),
                time, date, price, email, BookingStatus.BOOKED);

        // store in memory
        bookingRepository.add(booking);

        // mark time as booked
        bookedSlots.add(slot);

//        send email if possible
        notificationService.notifyBookingEvent(booking,BookingStatus.BOOKED);

        return booking;
    }
}
