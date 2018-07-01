package fleetmanagement.trucker.module1.controller;

import fleetmanagement.trucker.module1.entity.Reading;
import fleetmanagement.trucker.module1.service.ReadingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = " This api contains end points related to readings",
        produces = "application/json", consumes = "application/json")
@RestController
@RequestMapping("/readings")
public class ReadingController {

    @Autowired
    ReadingService readingService;

    @ApiOperation(value = "stores the current reading posted.",
                  notes = "This end point can store the new vehicle reading")
    @CrossOrigin(origins = "http://mocker.egen.io")
    @PostMapping("")
    public void postReading(@RequestBody Reading reading){
        readingService.processReading(reading);
    }

    @ApiOperation(value = "get all the readings of all vehicles",
                  notes = "This end point can be used to fetch the readings of all vehicles",
                  response = Reading.class, responseContainer = "List")
    @GetMapping("")
    public List<Reading> getReadings(){
        return readingService.findAll();
    }
}
