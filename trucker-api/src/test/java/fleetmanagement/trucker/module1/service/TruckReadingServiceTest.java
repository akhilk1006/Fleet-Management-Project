package fleetmanagement.trucker.module1.service;

import fleetmanagement.trucker.module1.alerts.ReadingProcessor;
import fleetmanagement.trucker.module1.entity.Reading;
import fleetmanagement.trucker.module1.repository.ReadingRepository;
import fleetmanagement.trucker.module1.repository.VehicleRepository;
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

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class TruckReadingServiceTest {

    @TestConfiguration
    static class TruckReadingServiceTestConfiguration{

        @Bean
        public TruckReadingService getReadingService(){
            return new TruckReadingService();
        }
    }

    @Autowired
    TruckReadingService service;

    @MockBean
    ReadingRepository readingRepository;

    @MockBean
    VehicleRepository vehicleRepository;

    @MockBean
    ReadingProcessor readingProcessor;

    private Reading reading = new Reading();

    @Before
    public void setUp() throws Exception {
        Mockito.when(this.readingRepository.save(this.reading))
                .thenThrow(NullPointerException.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test(expected = NullPointerException.class)
    public void save() {
        this.service.save(this.reading);
    }

}