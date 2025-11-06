package org.example.dto;

import org.example.entities.Vehicle;
import org.example.store.VehicleStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VehicleRepository implements VehicleStore {
    private final Logger log = LoggerFactory.getLogger(VehicleRepository.class);

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
            log.warn("Vehicle with registration plate: {} not found in the system", regNr);
        }
        return tmp;
    }

    @Override
    public void add(Vehicle obj) {
        if (obj != null) {
            store.put(obj.getRegNr(), obj);
            log.info("Vehicle has been added");
        }
    }

    @Override
    public Vehicle update(Vehicle obj) {
        if (obj != null) {
            Vehicle tmp = store.computeIfPresent(obj.getRegNr(), (k, v) -> obj);
            if (tmp != null) {
                log.info("Vehicle has been updated");
            } else {
                log.error("Vehicle not found in system and can therefore not be updated");
            }
            return tmp;
        }
        log.error("Vehicle is null"); // unless we log this somewhere else
        return null;
    }

    @Override
    public void delete(String id) {
        Vehicle tmp = store.remove(id);
        if (tmp != null) {
            log.info("Vehicle with registration plate: {} was removed", tmp.getRegNr());
        } else {
            log.warn("Vehicle with registration plate: {} not found in system", id);
        }
    }
}
