package fleetmanagement.trucker.module1.rules;

import fleetmanagement.trucker.module1.entity.Alert;
import fleetmanagement.trucker.module1.repository.AlertRepository;
import fleetmanagement.trucker.module1.utilities.AmazonSimpleMailServiceUtility;
import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Rule(name="engine RPM rule", description="engine RPM is greater than redline RPM")
@Component
public class EngineRPM{

    @Autowired
    AlertRepository alertRepository;

    @Autowired
    AmazonSimpleMailServiceUtility mailService;

    @Condition
    public boolean rpmTooHigh(@Fact("engineRPMHigh") boolean engineRPM){
        return engineRPM;
    }

    @Action
    public void triggerAlert(@Fact("vehicleId") String vin, @Fact("timestamp") String timestamp){
        String message = "RPM too HIGH";
        Alert alert  = new Alert(vin, message, "HIGH", Instant.parse(timestamp));
        alertRepository.save(alert);
        mailService.sendEmail(vin, message);
    }
}
