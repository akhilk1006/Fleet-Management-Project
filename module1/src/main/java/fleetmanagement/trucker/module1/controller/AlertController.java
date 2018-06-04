package fleetmanagement.trucker.module1.controller;

import fleetmanagement.trucker.module1.entity.Alert;
import fleetmanagement.trucker.module1.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.List;

@RestController
@RequestMapping("/alerts")
public class AlertController {

    @Autowired
    AlertService alertService;

    @Autowired
    Instant instant;

    @GetMapping("{id}")
    public List<Alert> findAllAlertsById(@PathVariable("id") final String id){

        return this.alertService.findAllByVin(id);
    }

    @GetMapping("")
    public LinkedHashSet<Alert> findAllAlerts(@RequestParam(value = "priority", defaultValue = "") final String priority,
                                              @RequestParam(value = "timeperiod", defaultValue = "-1") final int timePeriod){

        Instant duration = (timePeriod >= 0)? instant.now().minus(Duration.ofHours(timePeriod)) : this.instant.EPOCH;
        Iterable<Alert> alerts = (priority.isEmpty() && timePeriod < 0)? this.alertService.findAll() :
                                            this.alertService.findAllWithCriteriaAndSort(priority, duration);

        //return as LinkedHashSet to preserve the order of sorted entities.
        LinkedHashSet<Alert> resultSet = new LinkedHashSet<>();
        alerts.forEach(resultSet::add);
        return resultSet;
    }
}
