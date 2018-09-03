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
@Rule(name = "tire pressure rule", description = "tire pressures are not within the limits")
public class TirePressure {

    @Autowired
    AlertRepository alertRepository;

    @Condition
    public boolean tirePressureWithinLimits(@Fact("tirePressureNotWithinLimits") boolean tirePressure){
        return tirePressure;
    }

    @Action
    public void triggerAlert(@Fact("vehicleId") String vin, @Fact("timestamp") String timestamp){
        String message = "Check the tire Pressure";
        Alert alert  = new Alert(vin, message, "LOW", Instant.now());
        alertRepository.save(alert);
    }
}
