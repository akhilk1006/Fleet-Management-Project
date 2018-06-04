package fleetmanagement.trucker.module1.controller;

import fleetmanagement.trucker.module1.entity.Vehicle;
import fleetmanagement.trucker.module1.exceptions.ResourceNotFoundException;
import fleetmanagement.trucker.module1.service.VehicleService;
import fleetmanagement.trucker.module1.utilities.GeoLocation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.List;

@Api(description = " This api contains end points related to vehicles",
     produces = "application/json", consumes = "application/json")
@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @Autowired
    Instant currentInstant;

    @ApiOperation(value = "UPSERT's vehicle information",
                  notes = "This end point can insert or update(if already exists), vehicle data.")
    @CrossOrigin(origins = "http://mocker.egen.io")
    @PutMapping("")
    public void putVehicles(@RequestBody List<Vehicle> vehicle){
        this.vehicleService.saveAll(vehicle);
    }

    @ApiOperation(value = "get information of all vehicles",
                  notes = "This end point returns an iterable collection of all vehicles.",
                  response = Vehicle.class, responseContainer = "List")
    @GetMapping("")
    public Iterable<Vehicle> getVehicles(){
        return this.vehicleService.findAll();
    }

    @ApiOperation(value = "get information of a specific vehicle",
                  notes = "This end point returns a single vehicle whose vid matches the id in the url",
                  response = Vehicle.class)
    @GetMapping("{id}")
    public Vehicle findVehicleById(@PathVariable("id") final String id) throws ResourceNotFoundException {
        return this.vehicleService.findById(id);
    }

    @ApiOperation(value = "get geolocation information of a vehicle within last 30 minutes",
            notes = "This end point spits out geolocation information within the last 30 " +
                    "minutes of a single vehicle whose vid matches the id in the url",
            response = GeoLocation.class, responseContainer = "List")
    @GetMapping("{id}/geolocation")
    public List<GeoLocation> findGeoLocationsById(@PathVariable final String id){
        return this.vehicleService.findGeoLocationsById(this.currentInstant, id);
    }

}
