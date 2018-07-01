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
@Rule(name = "engine coolant low rule", description = "engine coolant is low")
public class EngineCoolantLow {

    @Autowired
    AlertRepository alertRepository;

    @Condition
    public boolean engineCoolantLow(@Fact("engineCoolantLow") boolean engineCoolant){
        return engineCoolant;
    }

    @Action
    public void triggerAlert(@Fact("vehicleId") String vin, @Fact("timestamp") String timestamp){
        String message = "Engine Coolant Low";
        Alert alert  = new Alert(vin, message, "LOW", Instant.parse(timestamp));
        alertRepository.save(alert);
    }
}
