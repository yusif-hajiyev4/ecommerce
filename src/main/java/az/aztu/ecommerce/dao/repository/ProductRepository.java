package az.aztu.ecommerce.dao.repository;

import az.aztu.ecommerce.dao.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findAllByIsPopular(Boolean isPopular);

    List<ProductEntity> findAllByIsFlashSale(Boolean isFlashSale);

    List<ProductEntity> findAllByIsJustForYou(Boolean isJustForYou);
}
