package fleetmanagement.trucker.module1.service;

import fleetmanagement.trucker.module1.entity.Vehicle;
import fleetmanagement.trucker.module1.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TruckService implements VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;

    @Override
    public void save(Vehicle vehicle) {
        this.vehicleRepository.save(vehicle);
    }

    @Override
    public void saveAll(List<Vehicle> vehicles){
        this.vehicleRepository.saveAll(vehicles);
    }
    @Override
    public Vehicle findById(String VehicleId) {
        return null;
    }

    @Override
    public List<Vehicle> findAll() {
        return null;
    }
}
