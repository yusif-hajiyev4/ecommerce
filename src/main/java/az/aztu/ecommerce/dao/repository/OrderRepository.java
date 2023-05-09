package az.aztu.ecommerce.dao.repository;

import az.aztu.ecommerce.dao.entity.AccountEntity;
import az.aztu.ecommerce.dao.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findAllByAccount(AccountEntity account);
}
