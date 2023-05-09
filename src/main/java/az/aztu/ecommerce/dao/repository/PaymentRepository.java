package az.aztu.ecommerce.dao.repository;

import az.aztu.ecommerce.dao.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

}
