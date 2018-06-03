package fleetmanagement.trucker.module1.service;

import fleetmanagement.trucker.module1.entity.Reading;
import fleetmanagement.trucker.module1.exceptions.ResourceNotFoundException;


public interface ReadingService {
    void save(Reading reading) throws ResourceNotFoundException;
}
