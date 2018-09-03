package fleetmanagement.trucker.module1.controller;

import fleetmanagement.trucker.module1.entity.Alert;
import fleetmanagement.trucker.module1.repository.AlertRepository;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.Instant;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@AutoConfigureMockMvc
@ActiveProfiles("integration")
public class AlertControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AlertRepository alertRepository;

    public Alert alert = new Alert("1HGCR2F3XFA027534",
                            "RPM Too High",
                            "HIGH",
                            Instant.EPOCH);

    @Before
    public void setUp() throws Exception {
        alertRepository.save(alert);
    }

    @After
    public void tearDown() throws Exception {
        alertRepository.deleteAll();
    }

    @Test
    public void findAllAlertsById() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/alerts/1HGCR2F3XFA027534"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].vin", Matchers.is("1HGCR2F3XFA027534")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].message", Matchers.is("RPM Too High")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].priority", Matchers.is("HIGH")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].timestamp", Matchers.is(Instant.EPOCH.toString())));

    }

    @Test
    public void findAllAlerts() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/alerts"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].vin", Matchers.is("1HGCR2F3XFA027534")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].message", Matchers.is("RPM Too High")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].priority", Matchers.is("HIGH")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].timestamp", Matchers.is(Instant.EPOCH.toString())));
    }
}