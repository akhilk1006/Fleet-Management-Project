package fleetmanagement.trucker.module1.controller;

import fleetmanagement.trucker.module1.entity.Vehicle;
import fleetmanagement.trucker.module1.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @CrossOrigin(origins = "http://mocker.egen.io")
    @PutMapping(value= "")
    public void putVehicles(@RequestBody List<Vehicle> vehicle){
        this.vehicleService.saveAll(vehicle);
    }
}
