package fleetmanagement.trucker.module1.repository;

import fleetmanagement.trucker.module1.entity.Alert;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.Instant;
import java.util.List;

public interface AlertRepository extends CrudRepository<Alert, String> {
    List<Alert> findAllByVin(String vin);

    @Query("SELECT alert FROM Alert alert WHERE alert.priority = ?1 AND alert.timestamp >= ?2")
    List<Alert> findAllWithCriteria(String priority, Instant duration);
}
