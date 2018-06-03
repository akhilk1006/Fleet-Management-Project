package fleetmanagement.trucker.module1.repository;

import fleetmanagement.trucker.module1.entity.Alert;
import org.springframework.data.repository.CrudRepository;

public interface AlertRepository extends CrudRepository<Alert, String> {
}
