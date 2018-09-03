package fleetmanagement.trucker.module1.entity;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class Vehicle {

    @Id
    private String vin;
    private String make;
    private String model;
    private Instant lastServiceDate;
    private int    year;
    private int    redlineRpm;
    private double maxFuelVolume;

    public Vehicle() {
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Instant getLastServiceDate() {
        return lastServiceDate;
    }

    public void setLastServiceDate(Instant lastServiceDate) {
        this.lastServiceDate = lastServiceDate;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRedlineRpm() {
        return redlineRpm;
    }

    public void setRedlineRpm(int redlineRpm) {
        this.redlineRpm = redlineRpm;
    }

    public double getMaxFuelVolume() {
        return maxFuelVolume;
    }

    public void setMaxFuelVolume(double maxFuelVolume) {
        this.maxFuelVolume = maxFuelVolume;
    }
}
