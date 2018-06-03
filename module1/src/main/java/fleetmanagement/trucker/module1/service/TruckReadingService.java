package fleetmanagement.trucker.module1.service;

import fleetmanagement.trucker.module1.entity.Reading;
import fleetmanagement.trucker.module1.entity.TireReading;
import fleetmanagement.trucker.module1.entity.Vehicle;
import fleetmanagement.trucker.module1.exceptions.ResourceNotFoundException;
import fleetmanagement.trucker.module1.repository.ReadingRepository;
import fleetmanagement.trucker.module1.repository.VehicleRepository;
import fleetmanagement.trucker.module1.rules.AlertRules;
import fleetmanagement.trucker.module1.utilities.AlertAttributes;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TruckReadingService implements ReadingService{

    @Autowired
    ReadingRepository readingRepository;

    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    AlertRules alertRules;

    @Override
    public void save(Reading reading) throws ResourceNotFoundException {
        this.readingRepository.save(reading);
        checkForAlertsAndProcess(reading);
    }

    @Async
    public void checkForAlertsAndProcess(Reading reading) throws ResourceNotFoundException {
        Optional<Vehicle> result = this.vehicleRepository.findById(reading.getVin());
        Vehicle vehicle = result.orElseThrow(() -> new ResourceNotFoundException("vehicleDetails not found"));
        AlertAttributes alertAttributes = new AlertAttributes();
        alertAttributes.setVin(reading.getVin());
        alertAttributes.setLastRecordedTime(reading.getTimestamp());
        this.alertRules.setAlertAttributes(alertAttributes);
        RulesEngine rulesEngine = new DefaultRulesEngine();
        addFactsForCurrentReading(vehicle, reading);
        this.alertRules.registerRules(); //register the rules of interest, with the rules Engine.
        //validate the facts against rules.
        rulesEngine.fire(this.alertRules.getRules(), this.alertRules.getFacts());
    }

    public void addFactsForCurrentReading(Vehicle vehicle, Reading reading){
        Facts facts = this.alertRules.getFacts();
        facts.put("engineRpmHigh",isEngineRpmMoreThanRedLineRpm(vehicle, reading));
        facts.put("fuelVolumeLow", isFuelVolumeBelowTenPercent(vehicle, reading));
        facts.put("tirePressureNotWithinLimits", isTirePressureWithinLimits(reading));
        facts.put("isCheckEngineLightOn", reading.isCheckEngineLightOn());
        facts.put("isEngineCoolantLow", reading.isEngineCoolantLow());
    }

    public boolean isEngineRpmMoreThanRedLineRpm(Vehicle vehicle, Reading reading){
        return vehicle.getRedlineRpm() < reading.getEngineRpm();
    }

    public boolean isFuelVolumeBelowTenPercent(Vehicle vehicle, Reading reading){
        return reading.getFuelVolume() < (0.10 * vehicle.getMaxFuelVolume());
    }

    public boolean isTirePressureWithinLimits(Reading reading){
        TireReading tireReading = reading.getTires();
        return  (tireReading.getFrontLeft() >= 32 && tireReading.getFrontLeft() <= 36)&&
                (tireReading.getRearLeft() >= 32 && tireReading.getRearLeft() <= 36)&&
                (tireReading.getFrontRight() >= 32 && tireReading.getFrontRight() <= 36)&&
                (tireReading.getRearRight() >= 32 && tireReading.getRearRight() <= 36) ;
    }
}
