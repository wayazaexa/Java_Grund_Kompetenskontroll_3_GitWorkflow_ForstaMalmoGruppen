package org.example.store;

import org.example.entities.Vehicle;

import java.util.List;

public interface VehicleStore {
    List<Vehicle> getAll();

    Vehicle findById(String regNr);

    void add(Vehicle obj);

    Vehicle update(Vehicle obj);

    void delete(String id);
}
