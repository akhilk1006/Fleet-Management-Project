package fleetmanagement.trucker.module1.service;

import fleetmanagement.trucker.module1.entity.Alert;
import fleetmanagement.trucker.module1.repository.AlertRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class VehicleAlertServiceTest {

    @TestConfiguration
    static class VehicleAlertServiceTestConfiguration{
        @Bean
        public AlertService alertService(){
            return new VehicleAlertService();
        }
    }

    @Autowired
    AlertService service;

    @MockBean
    AlertRepository alertRepository;

    List<Alert> alerts;

    public Alert getTestAlert(){
        return new Alert("1VWAP7A35CC020276", "RPM too high", "HIGH", Instant.now());
    }

    @Before
    public void setUp() throws Exception {
        alerts = new ArrayList<>();
        alerts.add(getTestAlert());

        Mockito.when(this.alertRepository.findAll())
                .thenReturn(this.alerts);
        Mockito.when(this.alertRepository.findAllByVin("1VWAP7A35CC020276"))
                .thenReturn(this.alerts);
        Mockito.when(this.alertRepository.findAllWithCriteria(any(String.class), any(Instant.class)))
                .thenReturn(this.alerts);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findAllByVin() {
        Iterable<Alert> alerts = this.service.findAllByVin("1VWAP7A35CC020276");
        assertEquals("alerts should match", alerts, this.alerts);
    }

    @Test
    public void findAll() {
        Iterable<Alert> alerts = this.service.findAll();
        assertEquals("alerts should match", alerts, this.alerts);
    }

    @Test
    public void findAllWithCriteriaAndSort() {
        Iterable<Alert> alerts = this.service.findAllWithCriteriaAndSort("HIGH", Instant.EPOCH);
        assertEquals("all alerts should match", alerts, this.alerts);
    }
}