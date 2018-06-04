package fleetmanagement.trucker.module1.service;

import fleetmanagement.trucker.module1.entity.Alert;
import fleetmanagement.trucker.module1.entity.Vehicle;
import fleetmanagement.trucker.module1.exceptions.ResourceNotFoundException;
import fleetmanagement.trucker.module1.utilities.GeoLocation;

import java.time.Instant;
import java.util.List;

public interface VehicleService {

    void saveAll(List<Vehicle> vehicles);

    Vehicle findById(String VehicleId) throws ResourceNotFoundException;

    Iterable<Vehicle> findAll();

    //returns vehicle's geolocation within last 30 minutes
    List<GeoLocation> findGeoLocationsById(Instant currentTimeStamp, String id);

    //returns all alerts of a vehicle from alert repository
    List<Alert> findAllAlerts(String id);
}
