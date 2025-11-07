package org.example.store;

import org.example.entities.Booking;

import java.util.List;

public interface BookingStore {
    List<Booking> getAll();

    Booking findById(int id);

    void add(Booking obj);

    Booking update(Booking obj);

    Booking delete(int id);

}
