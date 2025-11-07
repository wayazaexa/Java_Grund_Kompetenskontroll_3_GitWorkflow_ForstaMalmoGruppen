package org.example.dto;

import org.example.entities.Booking;
import org.example.store.BookingStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class BookingRepository implements BookingStore {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final Map<Integer, Booking> store;

    public BookingRepository() {
        this.store = new HashMap<>();
    }

    @Override
    public List<Booking> getAll() {
        log.info("Returned a list of all current bookings");
        return store.values().stream().toList();
    }

    @Override
    public Booking findById(int id) {
        Booking tmp = store.get(id);
        if (tmp == null) {
            log.warn("Booking with id: {} not found in the system", id);
        }
        else {
            log.info("Found booking with id: {}", id);
        }
        return tmp;
    }

    @Override
    public void add(Booking booking) {
        if (booking != null) {
            store.put(booking.getId(), booking);
            log.info("Booking has been added");
        }
    }

    @Override
    public Booking update(Booking booking) {
        if (booking != null) {
            Booking tmp = store.computeIfPresent(booking.getId(), (k, v) -> booking);
            if (tmp != null) {
                log.info("Booking has been updated");
            }
            else {
                log.error("Booking not found in system and can therefore not be updated");
            }
            return tmp;
        }
        else {
            log.error("Something went wrong. Booking is null");
        }
        return null;
    }

    @Override
    public Booking delete(int id) {
        Booking tmp = store.remove(id);
        if (tmp != null) {
            log.info("Booking with id: {} was removed", tmp.getId());
        }
        else {
            log.warn("Booking with id: {} not found in system", id);
        }
        return tmp;
    }



}
