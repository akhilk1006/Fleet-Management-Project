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
@Rule(name = "check engine light on rule", description = "check engine light is on")
public class CheckEngineLight {
    @Autowired
    AlertRepository alertRepository;

    @Condition
    public boolean checkEngineLight(@Fact("checkEngineLightOn") boolean engineLight){
        return engineLight;
    }

    @Action
    public void triggerAlert(@Fact("vehicleId") String vin, @Fact("timestamp") String timestamp){
        String message = "Check Engine Bulb is on";
        Alert alert  = new Alert(vin, message, "LOW", Instant.parse(timestamp));
        alertRepository.save(alert);
    }
}
