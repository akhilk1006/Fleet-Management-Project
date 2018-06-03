package fleetmanagement.trucker.module1.repository;

import fleetmanagement.trucker.module1.entity.Vehicle;
import fleetmanagement.trucker.module1.utilities.GeoLocation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.Instant;
import java.util.List;

public interface VehicleRepository extends CrudRepository<Vehicle, String> {

}
