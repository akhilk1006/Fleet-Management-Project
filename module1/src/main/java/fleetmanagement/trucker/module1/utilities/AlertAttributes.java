package fleetmanagement.trucker.module1.utilities;

//This class holds the required alert attributes fetched from vehicle repository.
public class AlertAttributes {
    private String vin;
    private String lastRecordedTime;

    public AlertAttributes() {
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getLastRecordedTime() {
        return lastRecordedTime;
    }

    public void setLastRecordedTime(String lastRecordedTime) {
        this.lastRecordedTime = lastRecordedTime;
    }
}
