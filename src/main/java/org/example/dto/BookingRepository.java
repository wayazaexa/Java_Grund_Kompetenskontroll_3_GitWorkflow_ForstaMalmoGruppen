package org.example.dto;

import org.example.entities.Booking;
import org.example.store.Store;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingRepository implements Store<Booking> {
    private final Map<Integer, Booking> store;

    public BookingRepository() {
        this.store = new HashMap<Integer, Booking>();
    }

    @Override
    public List<Booking> getAll() {
        return store.values().stream().toList();
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
            }
            else {
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
        }
        else {
            // Log Booking not found in system
        }
    }
}
