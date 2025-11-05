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
    private final Set<LocalDateTime> bookedSlots = new HashSet<>();
    private int bookingSeq = 0;

    public BookingService(BookingRepository bookingRepository,
                          NotificationService notificationService) {
        this.bookingRepository = bookingRepository;
        this.notificationService = notificationService;
    }

    public Booking createBooking(LocalDate date, LocalTime time, Vehicle v, String email) {
        // build slot
        LocalDateTime slot = LocalDateTime.of(date, time);

        // slot already taken → return null to indicate failure
        if (bookedSlots.contains(slot)) {
            return null;
        }

        int price = 0; // TODO: calculate price

        // generate unique ID (simple in-memory)
        int id = ++bookingSeq;

        Booking booking = new Booking(
                id,
                v,
                time,
                date,
                price,
                email,
                BookingStatus.BOOKED
        );

        // store in repository
        bookingRepository.add(booking);

        // mark slot as taken
        bookedSlots.add(slot);

        // send notification
        notificationService.notifyBookingEvent(booking, BookingStatus.BOOKED);

        return booking;
    }
}
