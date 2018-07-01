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
            notes = "lists all the alerts of required priority within the specified duration " +
                    "and sorts the list based on number of alerts per vehicle. When queried " +
                    "without Request Parameters(priority and duration) this operaton returns " +
                    "all the alerts.",
            response = Alert.class, responseContainer = "List")
    @GetMapping("")
    public LinkedHashSet<Alert> findAllAlerts(@ApiParam(name = "priority", required = false)
                                              @RequestParam(value = "priority", defaultValue = "") String priority,
                                              @ApiParam(name = "timeperiod", required = false)
                                              @RequestParam(value = "timeperiod", defaultValue = "-1") int timePeriod){

        Instant duration = (timePeriod >= 0)? Instant.now().minus(Duration.ofHours(timePeriod)) : Instant.EPOCH;
        Iterable<Alert> alerts = (priority.isEmpty() && timePeriod < 0)? this.alertService.findAll() :
                                            this.alertService.findAllWithCriteriaAndSort(priority, duration);

        //return as LinkedHashSet to preserve the order of sorted entities.
        LinkedHashSet<Alert> resultSet = new LinkedHashSet<>();
        alerts.forEach(resultSet::add);
        return resultSet;
    }
}
