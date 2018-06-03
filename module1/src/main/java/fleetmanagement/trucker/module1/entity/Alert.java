package fleetmanagement.trucker.module1.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Component
public class Alert {
    @Id
    private String id;
    private String vin;
    private String alertMessage;
    private String alertPriority;
    private String recordedTime;
    public Alert() {
        this.id = UUID.randomUUID().toString();
    }
    public Alert(String vehicleId, String alertMessage, String alertPriority, String recordedTime){
        this.setId(UUID.randomUUID().toString());
        this.vin = vehicleId;
        this.alertMessage = alertMessage;
        this.alertPriority = alertPriority;
        this.recordedTime = recordedTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public String getAlertPriority() {
        return alertPriority;
    }

    public void setAlertPriority(String alertPriority) {
        this.alertPriority = alertPriority;
    }

    public String getRecordedTime() {
        return recordedTime;
    }

    public void setRecordedTime(String recordedTime) {
        this.recordedTime = recordedTime;
    }
}
