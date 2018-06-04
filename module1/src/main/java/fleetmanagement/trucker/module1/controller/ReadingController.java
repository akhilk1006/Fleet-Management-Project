package fleetmanagement.trucker.module1.controller;

import fleetmanagement.trucker.module1.entity.Reading;
import fleetmanagement.trucker.module1.service.ReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/readings")
public class ReadingController {

    @Autowired
    ReadingService readingService;

    @CrossOrigin(origins = "http://mocker.egen.io")
    @PostMapping("")
    public void postReading(@RequestBody Reading reading){
        readingService.save(reading);
    }

    @GetMapping("")
    public List<Reading> getReadings(){
        return readingService.findAll();
    }
}
