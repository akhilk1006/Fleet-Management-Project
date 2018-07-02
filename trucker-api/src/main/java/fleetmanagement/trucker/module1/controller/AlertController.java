package fleetmanagement.trucker.module1.controller;

import fleetmanagement.trucker.module1.entity.Alert;
import fleetmanagement.trucker.module1.service.AlertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.List;

@Api(description = "This api contains end points related to alerts",
     produces = "application/json", consumes = "application/json")
@RestController
@RequestMapping("/alerts")
public class AlertController {

    @Autowired
    AlertService alertService;

    @ApiOperation(value = "find all alerts of a vehicle.",
                  notes = "This end point can be used to find all historical alerts of a vehicle.",
                  response = Alert.class, responseContainer = "List")
    @GetMapping("{id}")
    public List<Alert> findAllAlertsById(@PathVariable("id") final String id){

        return this.alertService.findAllByVin(id);
    }

    @ApiOperation(value = "find all alerts of required priority within the duration specified",
            notes = "lists all the alerts of required priority within the specified duration. " +
                    "When queried without Request Parameters(priority and duration) this "+
                    "operaton returns all the alerts.",
            response = Alert.class, responseContainer = "List")
    @GetMapping("")
    public List<Alert> findAllAlerts(@ApiParam(name = "priority", required = false)
                                      @RequestParam(value = "priority", defaultValue = "") String priority,
                                      @ApiParam(name = "timeperiod", required = false)
                                      @RequestParam(value = "timeperiod", defaultValue = "-1") int timePeriod){

        // get an Instant at the start of timeperiod, if no time period is specified then get all alerts.
        Instant duration = (timePeriod >= 0)? Instant.now().minus(Duration.ofHours(timePeriod)) : Instant.EPOCH;
        List<Alert> alerts = (priority.isEmpty() && timePeriod < 0)? this.alertService.findAll() :
                                            this.alertService.findAllWithCriteria(priority, duration);
        return alerts;
    }
}
