package az.aztu.ecommerce.dao.repository;

import az.aztu.ecommerce.dao.entity.ProductRelationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Tuple;
import java.util.List;

public interface ProductRelationsRepository extends JpaRepository<ProductRelationsEntity, Long> {

    @Query(
            value = "select p.id as id, pr.id as relationId, p.nameAz as nameAz, p.nameEn as nameEn, " +
                    "p.nameRu as nameRu, p.imageUrl as imageUrl, p.price as price, " +
                    "p.discountPercent as discountPercent, p.starCount as starCount " +
                    "from ProductRelationsEntity pr " +
                    "inner join ProductEntity p on p.id = pr.product.id " +
                    "inner join CategoryEntity c on c.id = pr.category.id " +
                    "inner join SubCategoryEntity sc on sc.id = pr.subCategory.id " +
                    "inner join ShopEntity s on s.id = pr.shop.id " +
                    "inner join LocationEntity l on l.id = pr.location.id " +
                    "where pr.category.id =:categoryId and " +
                    "(:subCategoryId is null or pr.subCategory.id =:subCategoryId) and " +
                    "(:productId is null or pr.product.id =:productId) and " +
                    "(:shopId is null or pr.shop.id =:shopId) and " +
                    "(:locationId is null or pr.location.id =:locationId)"
    )
    List<Tuple> getProducts(
            @Param("categoryId") Long categoryId,
            @Param("subCategoryId") Long subCategoryId,
            @Param("productId") Long productId,
            @Param("shopId") Long shopId,
            @Param("locationId") Long locationId
    );
}
