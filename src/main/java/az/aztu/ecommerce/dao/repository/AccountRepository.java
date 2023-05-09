package az.aztu.ecommerce.dao.repository;

import az.aztu.ecommerce.dao.entity.AccountEntity;
import az.aztu.ecommerce.model.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByEmailAndStatus(String email, AccountStatus status);
}
