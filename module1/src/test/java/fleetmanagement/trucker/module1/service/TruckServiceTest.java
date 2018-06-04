package fleetmanagement.trucker.module1.service;

import exceptions.TestException;
import fleetmanagement.trucker.module1.entity.Alert;
import fleetmanagement.trucker.module1.entity.Vehicle;
import fleetmanagement.trucker.module1.exceptions.ResourceNotFoundException;
import fleetmanagement.trucker.module1.repository.AlertRepository;
import fleetmanagement.trucker.module1.repository.ReadingRepository;
import fleetmanagement.trucker.module1.repository.VehicleRepository;
import fleetmanagement.trucker.module1.utilities.GeoLocation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class TruckServiceTest {

    @TestConfiguration
    static class TruckServiceTestConfiguration{

        @Bean
        public VehicleService getservice(){
            return new TruckService();
        }

    }

    private List<Vehicle> vehicles;
    List<GeoLocation> geoLocations;
    List<Alert> alerts;

    @Autowired
    VehicleService service;

    @MockBean
    ReadingRepository readingRepository;

    @MockBean
    AlertRepository alertRepository;

    @MockBean
    VehicleRepository vehicleRepository;

    @Before
    public void setup(){
        this.vehicles = new ArrayList<>();
        this.geoLocations = new ArrayList<GeoLocation>();
        this.alerts = new ArrayList<>();
        alerts.add(new Alert("1HGCR2F3XFA027534", "rpm too high", "HIGH", Instant.EPOCH));
        geoLocations.add(new GeoLocation(10000.34, -118899.002));
        Vehicle vehicle = getTestVehicle();
        this.vehicles.add(vehicle);

        Mockito.when(vehicleRepository.findAll())
                .thenReturn(this.vehicles);
        Mockito.when(vehicleRepository.findById("1HGCR2F3XFA027534"))
               .thenReturn(Optional.of(vehicle));

        //set mockito to throw a custom exception of type TestException when saveAll() is called
        Mockito.doAnswer((Answer<TestException>) invocationOnMock -> {
                throw new TestException();
                }).when(vehicleRepository).saveAll(this.vehicles);

        Mockito.when(readingRepository.findGeoLocationsById(any(Instant.class), any(String.class)))
               .thenReturn(geoLocations);
        Mockito.when(alertRepository.findAllByVin("1HGCR2F3XFA027534"))
               .thenReturn(alerts);
    }

    private Vehicle getTestVehicle(){
        Vehicle vehicle = new Vehicle();
        vehicle.setLastServiceDate("2017-05-25T17:31:25.268Z");
        vehicle.setMake("HONDA");
        vehicle.setMaxFuelVolume(1200);
        vehicle.setRedlineRpm(5000);
        vehicle.setYear(2011);
        vehicle.setVin("1HGCR2F3XFA027534");
        return vehicle;
    }

    @After
    public void cleanUp(){
        this.vehicles.clear();
    }

    @Test(expected = TestException.class)
    public void saveAll() {
        this.service.saveAll(this.vehicles);
    }

    @Test
    public void findById() throws Exception{
        Vehicle vehicle = this.service.findById("1HGCR2F3XFA027534");
        assertEquals("vehicle found should match", vehicle, this.vehicles.get(0));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findByIdNotFound() throws Exception {
        Vehicle vehicle = this.service.findById("1HGCR2F3XFA027535");
    }

    @Test
    public void findAll() {
        List<Vehicle> vehicles = (List<Vehicle>)this.service.findAll();
        assertEquals("vehicle list should match", vehicles, this.vehicles);
    }

    @Test
    public void findGeoLocationsById() {
        List<GeoLocation> result = this.service.findGeoLocationsById(Instant.now(), "1HGC");
        assertEquals("geoLocations should match", result.get(0),this.geoLocations.get(0));
    }

    @Test
    public void findAllAlerts() {
        List<Alert> alert = this.service.findAllAlerts("1HGCR2F3XFA027534");
        assertEquals("alerts should match", alert.get(0), this.alerts.get(0));
    }
}