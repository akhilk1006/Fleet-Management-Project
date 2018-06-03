package fleetmanagement.trucker.module1.rules;


import fleetmanagement.trucker.module1.entity.Alert;
import fleetmanagement.trucker.module1.repository.AlertRepository;
import fleetmanagement.trucker.module1.utilities.AlertAttributes;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.RuleBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AlertRules {

    private AlertAttributes alertAttributes;
    private Rules rules;
    private Facts facts;
    private AlertRepository alertRepository;
    public AlertRules(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
        this.rules = new Rules();
        this.facts = new Facts();
    }

    public Facts getFacts() {
        return facts;
    }

    public Rules getRules() {
        return rules;
    }

    public AlertAttributes getAlertAttributes() {
        return this.alertAttributes;
    }

    public void setAlertAttributes(AlertAttributes alertAttributes) {
        this.alertAttributes = alertAttributes;
    }

    public void preConfigureAlertForCurrentVehicle(Alert alert){
        alert.setVin(this.alertAttributes.getVin());
        alert.setTimestamp(this.alertAttributes.getLastRecordedTime());
    }

    public void registerRules(){
        this.rules.register(getEngineRPMRule());
        this.rules.register(getFuelVolumeRule());
        this.rules.register(getTirePressureRule());
        this.rules.register(getEngineCoolantRule());
        this.rules.register(getCheckEngineRule());
    }

    public Rule getEngineRPMRule(){
        return new RuleBuilder().when(facts -> facts.get("engineRpmHigh").equals(true))
                .name("Engine RPM too High")
                .then(facts -> {
                    Alert alert = new Alert();
                    preConfigureAlertForCurrentVehicle(alert);
                    alert.setMessage("RPM Too High");
                    alert.setPriority("HIGH");
                    this.alertRepository.save(alert);
                }).build();
    }

    public Rule getFuelVolumeRule(){
        return new RuleBuilder().when(facts -> facts.get("fuelVolumeLow").equals(true))
                .name("Low Fuel Volume")
                .then(facts -> {
                    Alert alert = new Alert();
                    preConfigureAlertForCurrentVehicle(alert);
                    alert.setMessage("Low Fuel Volume");
                    alert.setPriority("MEDIUM");
                    this.alertRepository.save(alert);
                }).build();
    }

    public Rule getTirePressureRule(){
        return new RuleBuilder().when(facts -> facts.get("tirePressureNotWithinLimits").equals(true))
                .name("Low Tire Pressure")
                .then(facts -> {
                    Alert alert = new Alert();
                    preConfigureAlertForCurrentVehicle(alert);
                    alert.setMessage("Check Tire Pressure");
                    alert.setPriority("LOW");
                    this.alertRepository.save(alert);
                }).build();
    }

    public Rule getEngineCoolantRule(){
        return new RuleBuilder().when(facts -> facts.get("isEngineCoolantLow").equals(true))
                .name("Low Engine Coolant")
                .then(facts -> {
                    Alert alert = new Alert();
                    preConfigureAlertForCurrentVehicle(alert);
                    alert.setMessage("Low Engine Coolant");
                    alert.setPriority("LOW");
                    this.alertRepository.save(alert);
                }).build();
    }
    public Rule getCheckEngineRule(){
        return new RuleBuilder().when(facts -> facts.get("isCheckEngineLightOn").equals(true))
                .name("Check Engine Warning On")
                .then(facts -> {
                    Alert alert = new Alert();
                    preConfigureAlertForCurrentVehicle(alert);
                    alert.setMessage("Check Engine Bulb");
                    alert.setPriority("LOW");
                    this.alertRepository.save(alert);
                }).build();
    }
}
