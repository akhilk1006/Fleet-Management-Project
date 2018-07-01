package fleetmanagement.trucker.module1.rules;

import fleetmanagement.trucker.module1.entity.Alert;
import fleetmanagement.trucker.module1.repository.AlertRepository;
import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Rule(name="Fuel Volume rule", description="fuel volume is less than 10% of Max Fuel Volume")
public class FuelVolume {

    @Autowired
    AlertRepository alertRepository;

    @Condition
    public boolean fuelVolumeTooLow(@Fact("fuelVolumeLow") boolean fuelVolume){
        return fuelVolume;
    }

    @Action
    public void triggerAlert(@Fact("vehicleId") String vin, @Fact("timestamp") String timestamp){
        String message = "fuel volume is less than 10% of Max Fuel Volume";
        Alert alert  = new Alert(vin, message, "MEDIUM", Instant.now());
        alertRepository.save(alert);
    }
}
