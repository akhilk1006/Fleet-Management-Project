package fleetmanagement.trucker.module1.service;

import fleetmanagement.trucker.module1.entity.Alert;
import fleetmanagement.trucker.module1.entity.Vehicle;
import fleetmanagement.trucker.module1.exceptions.ResourceNotFoundException;
import fleetmanagement.trucker.module1.repository.AlertRepository;
import fleetmanagement.trucker.module1.repository.ReadingRepository;
import fleetmanagement.trucker.module1.repository.VehicleRepository;
import fleetmanagement.trucker.module1.utilities.GeoLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class TruckService implements VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    ReadingRepository readingRepository;

    @Autowired
    AlertRepository alertRepository;

    @Override
    public void save(Vehicle vehicle) {
        this.vehicleRepository.save(vehicle);
    }

    @Override
    public void saveAll(List<Vehicle> vehicles){
        this.vehicleRepository.saveAll(vehicles);
    }

    @Override
    public Vehicle findById(String vehicleId) throws ResourceNotFoundException {
        return this.vehicleRepository.findById(vehicleId)
                .orElseThrow(()-> new ResourceNotFoundException("Invalid address"));
    }

    @Override
    public Iterable<Vehicle> findAll() {
        return this.vehicleRepository.findAll();
    }

    @Override
    public List<GeoLocation> findGeoLocationsById(Instant currentTimeStamp, String id){
        return this.readingRepository.findGeoLocationsById(currentTimeStamp.minusSeconds(1800), id);
    }

    @Override
    public List<Alert> findAllAlerts(String id){
        return this.alertRepository.findAllByVin(id);
    }
}
