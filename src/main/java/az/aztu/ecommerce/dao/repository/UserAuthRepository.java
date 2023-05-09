package az.aztu.ecommerce.dao.repository;

import az.aztu.ecommerce.dao.entity.UserAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthRepository extends JpaRepository<UserAuthEntity, Long> {

    UserAuthEntity findByAccount_Id(Long accountId);
}
