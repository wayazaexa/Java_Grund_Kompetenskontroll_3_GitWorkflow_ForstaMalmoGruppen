package org.example.dto;

import org.example.entities.Booking;
import org.example.entities.BookingStatus;
import org.example.entities.Vehicle;
import org.example.services.NotificationService;
import org.example.store.BookingStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class BookingRepository implements BookingStore {
    private final Logger log = LoggerFactory.getLogger(BookingRepository.class);
    // Holds all already-booked time slots (date + time) so we can quickly check availability
    private final Set<LocalDateTime> bookedSlots = new HashSet<>();

    private final Map<Integer, Booking> store;

    private NotificationService notificationService;

    public BookingRepository() {
        this.store = new HashMap<>();
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
        store.put(id, booking);

        // mark time as booked
        bookedSlots.add(slot);

//        send email if possible
        notificationService.notifyBookingEvent(booking,BookingStatus.BOOKED);

        return booking;
    }

    @Override
    public List<Booking> getAll() {
        return store.values().stream().toList();
    }

    @Override
    public Booking findById(int id) {
        Booking tmp = store.get(id);
        if (tmp == null) {
            log.warn("Booking with id: {} not found in the system", id);
        }
        return tmp;
    }

    @Override
    public void add(Booking obj) {
        if (obj != null) {
            store.put(obj.getId(), obj);
            log.info("Booking has been added");
        }
    }

    @Override
    public Booking update(Booking obj) {
        if (obj != null) {
            Booking tmp = store.computeIfPresent(obj.getId(), (k, v) -> obj);
            if (tmp != null) {
                log.info("Booking has been updated");
            }
            else {
                log.error("Booking not found in system and can therefore not be updated");
            }
            return tmp;
        }
        log.error("Booking is null"); // unless we log this somewhere else
        return null;
    }

    @Override
    public void delete(int id) {
        Booking tmp = store.remove(id);
        if (tmp != null) {
            log.info("Booking with id: {} was removed", tmp.getId());
        }
        else {
            log.warn("Booking with id: {} not found in system", id);
        }
    }
}
