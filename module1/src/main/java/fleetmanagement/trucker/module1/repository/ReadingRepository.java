package fleetmanagement.trucker.module1.repository;

import fleetmanagement.trucker.module1.entity.Reading;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadingRepository extends CrudRepository<Reading, String> {

}
