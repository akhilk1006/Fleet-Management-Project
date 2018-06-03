package fleetmanagement.trucker.module1.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;
import java.util.UUID;

@Entity
@Component
public class Alert {
    @Id
    private String id;
    private String vin;
    private String message;
    private String priority;
    private Instant timestamp;

    public Alert() {
        this.id = UUID.randomUUID().toString();
    }

    public Alert(String vehicleId, String message, String priority, Instant timestamp){
        this.setId(UUID.randomUUID().toString());
        this.vin = vehicleId;
        this.message = message;
        this.priority = priority;
        this.timestamp = timestamp;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
