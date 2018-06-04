package fleetmanagement.trucker.module1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fleetmanagement.trucker.module1.entity.Reading;
import fleetmanagement.trucker.module1.entity.TireReading;
import fleetmanagement.trucker.module1.repository.ReadingRepository;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.Embedded;
import java.time.Instant;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@ActiveProfiles("integration")
@AutoConfigureMockMvc
public class ReadingControllerTest {

    @Autowired
    ReadingRepository readingRepository;

    @Autowired
    MockMvc mockMvc;

    public Reading reading;

    @Before
    public void setUp() throws Exception {
        this.reading = new Reading();
        this.reading.setVin("1HGCR2F3XFA027534");
        this.reading.setLatitude(41.803194);
        this.reading.setLongitude(-88.144406);
        this.reading.setTimestamp(null);
        this.reading.setFuelVolume(1.5);
        this.reading.setSpeed(85);
        this.reading.setEngineHp(240);
        this.reading.setCheckEngineLightOn(false);
        this.reading.setEngineCoolantLow(true);
        this.reading.setCruiseControlOn(true);
        this.reading.setEngineRpm(1800);
        TireReading tireReading = new TireReading();
        tireReading.setFrontLeft(34);
        tireReading.setFrontRight(32);
        tireReading.setRearRight(36);
        tireReading.setRearLeft(36);
        this.reading.setTires(tireReading);

        this.readingRepository.save(reading);
    }

    @After
    public void tearDown() throws Exception {
        readingRepository.delete(reading);
    }

    @Test
    public void postReading() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        this.reading.setId(UUID.randomUUID().toString());
        this.mockMvc.perform(MockMvcRequestBuilders.post("/readings")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsBytes(this.reading)))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/readings"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].vin", Matchers.is("1HGCR2F3XFA027534")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].latitude", Matchers.is(41.803194)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].longitude", Matchers.is(-88.144406)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].fuelVolume", Matchers.is(1.5)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].speed", Matchers.is(85)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].engineHp", Matchers.is(240)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].checkEngineLightOn", Matchers.is(false)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].engineCoolantLow", Matchers.is(true)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].cruiseControlOn", Matchers.is(true)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].engineRpm", Matchers.is(1800)));


    }

    @Test
    public void GetReadings() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/readings"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].vin", Matchers.is("1HGCR2F3XFA027534")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].latitude", Matchers.is(41.803194)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].longitude", Matchers.is(-88.144406)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].fuelVolume", Matchers.is(1.5)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].speed", Matchers.is(85)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].engineHp", Matchers.is(240)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].checkEngineLightOn", Matchers.is(false)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].engineCoolantLow", Matchers.is(true)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].cruiseControlOn", Matchers.is(true)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].engineRpm", Matchers.is(1800)));
    }
}