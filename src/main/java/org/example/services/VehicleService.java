package org.example.services;


import org.example.entities.Vehicle;
import org.example.store.VehicleStore;

import java.util.List;

public class VehicleService {
    private final VehicleStore store;

    public VehicleService(VehicleStore store) {
        this.store = store;
    }


//    add vehicle
    public Vehicle addVehicle(Vehicle vehicle) {
        store.add(vehicle);
        return vehicle;
    }


//    update veh
    public void updateVehicle(Vehicle regNr) {
        store.update(regNr);
    }
//    delete by id
    public void deleteVehicle(String regNr) {
        List<Vehicle> all = store.getAll();
        all.stream()
                .filter(v -> v.getRegNr().equals(regNr))
                .findFirst().ifPresent(toDelete -> System.out.println("Vehicle has been deleted"));

        store.delete(regNr);
    }
//    findById
    public Vehicle getVehicle(String regNr) {
        return getAllVehicles().stream()
                .filter(vehicle -> vehicle.getRegNr().equals(regNr))
                .findFirst()
                .orElse(null);
    }
//    find All
    public List<Vehicle> getAllVehicles() {
        store.getAll();
        return store.getAll();
    }

}
