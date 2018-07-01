package fleetmanagement.trucker.module1.rules;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.springframework.stereotype.Component;
import org.jeasy.rules.api.Rules;
@Component
public class RulesTrigger {

    RulesEngine rulesEngine;
    Rules rules;

    public RulesTrigger(EngineRPM engineRPM, EngineCoolantLow engineCoolantLow,
                        CheckEngineLight checkEngineLight, TirePressure tirePressure,
                        FuelVolume fuelVolume){
        this.rulesEngine = new DefaultRulesEngine();
        this.rules = new Rules();
        this.rules.register(engineRPM);
        this.rules.register(engineCoolantLow);
        this.rules.register(checkEngineLight);
        this.rules.register(tirePressure);
        this.rules.register(fuelVolume);
    }

    public void fireRulesAgainstFacts(Facts facts){
        this.rulesEngine.fire(this.rules, facts);
    }
}
