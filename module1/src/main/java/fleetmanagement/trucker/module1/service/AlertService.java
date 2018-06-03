package fleetmanagement.trucker.module1.service;

import fleetmanagement.trucker.module1.entity.Alert;

import java.time.Instant;
import java.util.List;

public interface AlertService {

    List<Alert> findAllByVin(String id);

    Iterable<Alert> findAll();

    List<Alert> findAllWithCriteriaAndSort(String priority, Instant duration);
}
