package az.aztu.ecommerce.dao.repository;

import az.aztu.ecommerce.dao.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {

}
