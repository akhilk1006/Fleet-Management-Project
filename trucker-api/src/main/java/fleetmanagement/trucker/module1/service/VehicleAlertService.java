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
    public Iterable<Alert> findAll() {
        return this.alertRepository.findAll();
    }

    @Override
    public List<Alert> findAllWithCriteriaAndSort(String priority, Instant duration) {
        List<Alert> alerts = alertRepository.findAllWithCriteria(priority, duration);
        return sortVehiclesBasedOnAlertFrequency(alerts);
    }

    private List<Alert> sortVehiclesBasedOnAlertFrequency(List<Alert> alerts){
        Hashtable<String, Integer> frequencyTable = buildFrequencyTable(alerts);
        Collections.sort(alerts, (alert1, alert2) -> {
            return frequencyTable.get(alert2.getVin()).compareTo(frequencyTable.get(alert1.getVin()));
        });
        return alerts;
    }

    private Hashtable<String, Integer> buildFrequencyTable(Iterable<Alert> alerts){
        Hashtable<String, Integer> frequencyTable = new Hashtable<>();
        alerts.forEach((alert)->{
            frequencyTable.putIfAbsent(alert.getVin(), 0);
            frequencyTable.put(alert.getVin(), frequencyTable.get(alert.getVin()) + 1);
        });
        return frequencyTable;
    }
}
