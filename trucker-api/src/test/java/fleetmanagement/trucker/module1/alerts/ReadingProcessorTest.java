package fleetmanagement.trucker.module1.alerts;
import fleetmanagement.trucker.module1.entity.TireReading;
import java.time.Instant;

import fleetmanagement.trucker.module1.entity.Reading;
import fleetmanagement.trucker.module1.entity.Vehicle;
import fleetmanagement.trucker.module1.repository.VehicleRepository;
import fleetmanagement.trucker.module1.rules.RulesTrigger;
import org.jeasy.rules.api.Facts;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class ReadingProcessorTest {

    @TestConfiguration
    static class ReadingProcessorTestConfig{
        @Bean
        public ReadingProcessor getProcessor(){
            return new ReadingProcessor();
        }
    }

    @Autowired
    ReadingProcessor readingProcessor;
    @MockBean
    VehicleRepository vehicleRepository;
    @MockBean
    RulesTrigger rulesTrigger;

    Vehicle vehicle;
    Reading reading;

    @Before
    public void setUp() throws Exception {
        this.vehicle = new Vehicle();
        vehicle.setVin("1HGCR2F3XFA027534");
        vehicle.setMaxFuelVolume(15);
        vehicle.setModel("ACCORD");
        vehicle.setMake("HONDA");
        vehicle.setRedlineRpm(5500);
        vehicle.setLastServiceDate(Instant.parse("2017-05-25T17:31:25.268Z"));
        vehicle.setYear(2015);
        this.reading = new Reading();
        reading.setVin("1HGCR2F3XFA027534");
        reading.setLatitude(41.803194);
        reading.setLongitude(-88.144406);
        reading.setTimestamp(Instant.parse("2017-05-25T17:31:25.268Z"));
        reading.setFuelVolume(1.5);
        reading.setSpeed(85);
        reading.setEngineHp(240);
        reading.setCheckEngineLightOn(false);
        reading.setEngineCoolantLow(true);
        reading.setCruiseControlOn(true);
        reading.setEngineRpm(6300);
        TireReading tireReading = new TireReading();
        tireReading.setFrontLeft(34);
        tireReading.setFrontRight(36);
        tireReading.setRearLeft(29);
        tireReading.setRearRight(34);
        reading.setTires(tireReading);


    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getFacts() {
        Facts facts = this.readingProcessor.getFacts(this.vehicle, this.reading);
        Assert.assertEquals("engine rpm is high", true, facts.get("engineRPMHigh"));
        Assert.assertEquals("fuel volume is not low", false, facts.get("fuelVolumeLow"));
        Assert.assertEquals("tire pressure is not within limits", false,
                            facts.get("tirePressureNotWithinLimits"));
        Assert.assertEquals("check engine light is not on", false, facts.get("checkEngineLightOn"));
        Assert.assertEquals("engine coolant is low", true, facts.get("engineCoolantLow"));
        Assert.assertEquals("timestamp's should be equal", this.reading.getTimestamp().toString(),
                            facts.get("timestamp"));
        Assert.assertEquals("vehicleId's should be same", this.reading.getVin(), facts.get("vehicleId"));
    }

    @Test
    public void isEngineRpmMoreThanRedLineRpm() {
        boolean result = this.readingProcessor.isEngineRpmMoreThanRedLineRpm(this.vehicle, this.reading);
        Assert.assertEquals("engine rpm is more than redLine rpm", true, result);
    }
    @Test
    public void engineRpmEqualtoRedLineRpm() {
        this.reading.setEngineRpm(5500);
        boolean result = this.readingProcessor.isEngineRpmMoreThanRedLineRpm(this.vehicle, this.reading);
        Assert.assertEquals("engine rpm is not more than redLine rpm", false, result);
    }
    @Test
    public void fuelVolumeEqualToTenPercent() {
        boolean result = this.readingProcessor.isFuelVolumeBelowTenPercent(this.vehicle, this.reading);
        Assert.assertEquals("fuel volume is not below 10%", false, result);
    }

    @Test
    public void fuelVolumeBelowTenPercent(){
        reading.setFuelVolume(1.4);
        boolean result = this.readingProcessor.isFuelVolumeBelowTenPercent(this.vehicle, this.reading);
        Assert.assertEquals("fuel volume is below ten percent", true, result);
    }
    @Test
    public void isTirePressureNotWithinLimits() {
        boolean result = this.readingProcessor.isTirePressureWithinLimits(this.reading);
        Assert.assertEquals("Tire pressure is not within the limits", false, result);
    }
    @Test
    public void isTirePressureWithinLimits() {
        this.reading.getTires().setRearLeft(32);
        boolean result = this.readingProcessor.isTirePressureWithinLimits(this.reading);
        Assert.assertEquals("Tire pressure is not within the limits", true, result);
    }

}