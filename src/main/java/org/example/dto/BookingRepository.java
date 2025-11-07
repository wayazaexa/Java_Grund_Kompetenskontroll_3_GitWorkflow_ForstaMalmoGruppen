package org.example.dto;

import org.example.entities.Booking;
import org.example.services.NotificationService;
import org.example.store.BookingStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class BookingRepository implements BookingStore {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final Map<Integer, Booking> store;

    private NotificationService notificationService;

    public BookingRepository() {
        this.store = new HashMap<>();
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
