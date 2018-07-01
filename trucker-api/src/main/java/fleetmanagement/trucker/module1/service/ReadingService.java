package fleetmanagement.trucker.module1.service;

import fleetmanagement.trucker.module1.entity.Reading;
import fleetmanagement.trucker.module1.exceptions.ResourceNotFoundException;

import java.util.List;


public interface ReadingService {
    void save(Reading reading);
    void processReading(Reading reading);
    List<Reading> findAll();
}
