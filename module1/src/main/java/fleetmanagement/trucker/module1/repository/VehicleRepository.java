package fleetmanagement.trucker.module1.repository;

import fleetmanagement.trucker.module1.entity.Vehicle;
import org.springframework.data.repository.CrudRepository;

public interface VehicleRepository extends CrudRepository<Vehicle, String> {

}
