package fleetmanagement.trucker.module1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fleetmanagement.trucker.module1.entity.Reading;
import fleetmanagement.trucker.module1.entity.Vehicle;
import fleetmanagement.trucker.module1.repository.VehicleRepository;
import org.hamcrest.Matcher;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@ActiveProfiles("integration")
public class VehicleControllerTest {

    @Autowired
    public VehicleRepository vehicleRepository;

    public Vehicle vehicle;

    @Autowired
    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.vehicle = new Vehicle();
        this.vehicle.setVin("1HGCR2F3XFA027534");
        this.vehicle.setYear(2015);
        this.vehicle.setMaxFuelVolume(12);
        this.vehicle.setLastServiceDate("2017-05-25T17:31:25.268Z");
        this.vehicle.setRedlineRpm(3000);
        this.vehicle.setMake("HONDA");
        this.vehicle.setModel("CIVIC");

        this.vehicleRepository.save(this.vehicle);
    }

    @After
    public void tearDown() throws Exception {
        this.vehicleRepository.delete(this.vehicle);
    }

    @Test
    public void putVehicle() throws Exception{
        this.vehicle.setMaxFuelVolume(15);
        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.add(this.vehicle);
        ObjectMapper mapper = new ObjectMapper();
        this.mockMvc.perform(MockMvcRequestBuilders.put("/vehicles")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsBytes(vehicleList)))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/vehicles"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].vin", Matchers.is("1HGCR2F3XFA027534")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].year", Matchers.is(2015)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].maxFuelVolume", Matchers.is((double)15)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].redlineRpm", Matchers.is(3000)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].make", Matchers.is("HONDA")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].model", Matchers.is("CIVIC")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastServiceDate", Matchers
                                                                           .is("2017-05-25T17:31:25.268Z")));
    }

    @Test
    public void getVehicles() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/vehicles"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].vin", Matchers.is("1HGCR2F3XFA027534")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].year", Matchers.is(2015)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].maxFuelVolume", Matchers.is((double)12)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].redlineRpm", Matchers.is(3000)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].make", Matchers.is("HONDA")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].model", Matchers.is("CIVIC")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastServiceDate", Matchers
                        .is("2017-05-25T17:31:25.268Z")));
    }

    @Test
    public void getVehicleById() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/vehicles/1HGCR2F3XFA027534"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.vin", Matchers.is("1HGCR2F3XFA027534")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.year", Matchers.is(2015)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.maxFuelVolume", Matchers.is((double)12)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.redlineRpm", Matchers.is(3000)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.make", Matchers.is("HONDA")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", Matchers.is("CIVIC")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastServiceDate", Matchers
                        .is("2017-05-25T17:31:25.268Z")));
    }

    @Test
    public void findGeoLocationsByIdNotFound() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/vehicles/1HGCR2F3XFA027534/geolocation"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }


}