package fleetmanagement.trucker.module1.service;

import fleetmanagement.trucker.module1.entity.Alert;
import fleetmanagement.trucker.module1.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

@Transactional(readOnly = true)
@Service
public class VehicleAlertService implements AlertService {

    @Autowired
    AlertRepository alertRepository;

    @Override
    public List<Alert> findAllByVin(String id) {
        return alertRepository.findAllByVin(id);
    }

    @Override
    public List<Alert> findAll() {
        return (List<Alert>)this.alertRepository.findAll();
    }

    @Override
    public List<Alert> findAllWithCriteria(String priority, Instant duration) {
        List<Alert> alerts = alertRepository.findAllByPriorityAndTimestampGreaterThanEqual(priority, duration);
        return alerts;
    }
}
