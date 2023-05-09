package az.aztu.ecommerce.dao.repository;

import az.aztu.ecommerce.dao.entity.SubCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategoryEntity, Long> {

    List<SubCategoryEntity> findAllByCategory_Id(Long categoryId);
}
