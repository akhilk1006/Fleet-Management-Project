package fleetmanagement.trucker.module1.service;

import fleetmanagement.trucker.module1.entity.Reading;
import fleetmanagement.trucker.module1.repository.ReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TruckReadingService implements ReadingService{

    @Autowired
    ReadingRepository readingRepository;

    @Override
    public void save(Reading reading) {
        this.readingRepository.save(reading);
    }
}
