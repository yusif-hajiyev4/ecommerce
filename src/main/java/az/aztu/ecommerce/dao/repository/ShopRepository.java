package az.aztu.ecommerce.dao.repository;

import az.aztu.ecommerce.dao.entity.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<ShopEntity, Long> {

}
