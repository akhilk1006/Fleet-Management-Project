package fleetmanagement.trucker.module1.utilities;

import java.time.Instant;

//This class holds the required alert attributes fetched from vehicle repository.
public class AlertAttributes {
    private String vin;
    private Instant lastRecordedTime;

    public AlertAttributes() {
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Instant getLastRecordedTime() {
        return lastRecordedTime;
    }

    public void setLastRecordedTime(Instant lastRecordedTime) {
        this.lastRecordedTime = lastRecordedTime;
    }
}
