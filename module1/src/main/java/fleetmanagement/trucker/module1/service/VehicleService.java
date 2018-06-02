package fleetmanagement.trucker.module1.service;

import fleetmanagement.trucker.module1.entity.Vehicle;

import java.util.List;

public interface VehicleService {
    void save(Vehicle vehicle);
    void saveAll(List<Vehicle> vehicles);
    Vehicle findById(String VehicleId);
    List<Vehicle> findAll();

}
