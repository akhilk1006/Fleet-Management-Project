package fleetmanagement.trucker.module1.service;

import fleetmanagement.trucker.module1.alerts.ReadingProcessor;
import fleetmanagement.trucker.module1.entity.Reading;
import fleetmanagement.trucker.module1.repository.ReadingRepository;
import fleetmanagement.trucker.module1.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class TruckReadingService implements ReadingService{

    @Autowired
    ReadingRepository readingRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    ReadingProcessor readingProcessor;

    @Override
    public void save(Reading reading){
        this.readingRepository.save(reading);
    }

    @Override
    public void processReading(Reading reading){
        // save the reading into database first.
        this.save(reading);

        // validate the reading and trigger alerts if required.
        this.readingProcessor.processReading(reading);
    }

    public List<Reading> findAll(){
        return (List<Reading>)this.readingRepository.findAll();
    }
}
