package fleetmanagement.trucker.module1.service;

import fleetmanagement.trucker.module1.entity.Alert;

import java.time.Instant;
import java.util.List;

public interface AlertService {

    List<Alert> findAllByVin(String id);

    List<Alert> findAll();

    List<Alert> findAllWithCriteria(String priority, Instant duration);
}
