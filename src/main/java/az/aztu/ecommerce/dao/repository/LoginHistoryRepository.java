package az.aztu.ecommerce.dao.repository;

import az.aztu.ecommerce.dao.entity.LoginHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginHistoryRepository extends JpaRepository<LoginHistoryEntity, Long> {

}
