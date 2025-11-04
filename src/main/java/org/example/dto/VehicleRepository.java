package org.example.dto;

import org.example.entities.Vehicle;
import org.example.store.VehicleStore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VehicleRepository implements VehicleStore {
    private final Map<String, Vehicle> store;

    public VehicleRepository() {
        this.store = new HashMap<>();
    }

    @Override
    public List<Vehicle> getAll() {
        return store.values().stream().toList();
    }

    @Override
    public Vehicle findById(String regNr) {
        Vehicle tmp = store.get(regNr);
        if (tmp == null) {
            // Log error Vehicle with regNr not found in the system
        }
        return tmp;
    }

    @Override
    public void add(Vehicle obj) {
        if (obj != null) {
            store.put(obj.getRegNr(), obj);
            // Log Vehicle has been added
        }
    }

    @Override
    public Vehicle update(Vehicle obj) {
        if (obj != null) {
            Vehicle tmp = store.computeIfPresent(obj.getRegNr(), (k, v) -> obj);
            if (tmp != null) {
                // Log Vehicle has been updated
            }
            else {
                // Log error Vehicle not found in system and can therefore not be updated
            }
            return tmp;
        }
        // Log error Vehicle is null (unless we log this somewhere else)
        return null;
    }

    @Override
    public void delete(String id) {
        Vehicle tmp = store.remove(id);
        if (tmp != null) {
            // Log Vehicle was removed
        }
        else {
            // Log Vehicle not found in system
        }
    }
}
