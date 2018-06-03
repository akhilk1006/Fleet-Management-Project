package fleetmanagement.trucker.module1.controller;

import fleetmanagement.trucker.module1.entity.Vehicle;
import fleetmanagement.trucker.module1.exceptions.ResourceNotFoundException;
import fleetmanagement.trucker.module1.service.VehicleService;
import fleetmanagement.trucker.module1.utilities.GeoLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @Autowired
    Instant currentInstant;

    @CrossOrigin(origins = "http://mocker.egen.io")
    @PutMapping("")
    public void putVehicles(@RequestBody List<Vehicle> vehicle){
        this.vehicleService.saveAll(vehicle);
    }

    @GetMapping("")
    public Iterable<Vehicle> getVehicles(){
        return this.vehicleService.findAll();
    }

    @GetMapping("{id}")
    public Vehicle findVehicleById(@PathVariable("id") final String id) throws ResourceNotFoundException {
        return this.vehicleService.findById(id);
    }

    @GetMapping("{id}/geolocation")
    public List<GeoLocation> findGeoLocationsById(@PathVariable final String id){
        return this.vehicleService.findGeoLocationsById(this.currentInstant, id);
    }

}
