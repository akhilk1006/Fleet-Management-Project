package fleetmanagement.trucker.module1.repository;

import fleetmanagement.trucker.module1.entity.Reading;
import fleetmanagement.trucker.module1.utilities.GeoLocation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.time.Instant;
import java.util.List;

@Repository
public interface ReadingRepository extends CrudRepository<Reading, String> {

    @Query("SELECT " +
           "NEW fleetmanagement.trucker.module1.utilities.GeoLocation(reading.latitude, reading.longitude)" +
           "FROM Reading reading " +
           "WHERE reading.vin = ?2 AND reading.timestamp >= ?1")
    List<GeoLocation> findGeoLocationsById(Instant timeStamp, String id);
}
