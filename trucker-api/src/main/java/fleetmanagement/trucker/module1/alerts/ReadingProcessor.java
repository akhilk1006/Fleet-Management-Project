package fleetmanagement.trucker.module1.alerts;

import fleetmanagement.trucker.module1.entity.Reading;
import fleetmanagement.trucker.module1.entity.TireReading;
import fleetmanagement.trucker.module1.entity.Vehicle;
import fleetmanagement.trucker.module1.repository.VehicleRepository;
import fleetmanagement.trucker.module1.rules.*;
import org.jeasy.rules.api.Facts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class ReadingProcessor {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    RulesTrigger rulesTrigger;

    @Async
    public void processReading(Reading reading){
        //fetch the corresponding vehicle details.
        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(reading.getVin());

        //if result is not null, then validate the facts about current reading against the rules.
        vehicleOptional.ifPresent(vehicle -> this.rulesTrigger
                                             .fireRulesAgainstFacts(getFacts(vehicle, reading)));
    }

    //generate facts about a given reading.
    public Facts getFacts(Vehicle vehicle, Reading reading){
        Facts facts = new Facts();
        facts.put("engineRPMHigh",isEngineRpmMoreThanRedLineRpm(vehicle, reading));
        facts.put("fuelVolumeLow", isFuelVolumeBelowTenPercent(vehicle, reading));
        facts.put("tirePressureNotWithinLimits", isTirePressureWithinLimits(reading));
        facts.put("checkEngineLightOn", reading.isCheckEngineLightOn());
        facts.put("engineCoolantLow", reading.isEngineCoolantLow());
        facts.put("timestamp", reading.getTimestamp().toString());
        facts.put("vehicleId", reading.getVin());
        return facts;
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
