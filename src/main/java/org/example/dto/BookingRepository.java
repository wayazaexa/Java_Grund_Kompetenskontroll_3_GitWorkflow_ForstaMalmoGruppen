package org.example.dto;

import org.example.entities.Booking;
import org.example.services.NotificationService;
import org.example.store.BookingStore;
import java.util.*;

public class BookingRepository implements BookingStore {
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
            // Log error Vehicle with regNr not found in the system
        }
        return tmp;
    }

    @Override
    public void add(Booking obj) {
        if (obj != null) {
            store.put(obj.getId(), obj);
            // Log Booking has been added
        }
    }

    @Override
    public Booking update(Booking obj) {
        if (obj != null) {
            Booking tmp = store.computeIfPresent(obj.getId(), (k, v) -> obj);
            if (tmp != null) {
                // Log Booking has been updated
            } else {
                // Log error Booking not found in system and can therefore not be updated
            }
            return tmp;
        }
        // Log error Booking is null (unless we log this somewhere else)
        return null;
    }

    @Override
    public void delete(int id) {
        Booking tmp = store.remove(id);
        if (tmp != null) {
            // Log Booking was removed
        } else {
            // Log Booking not found in system
        }
    }
}
