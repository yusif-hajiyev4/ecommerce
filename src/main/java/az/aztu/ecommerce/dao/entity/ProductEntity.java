package az.aztu.ecommerce.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameAz;
    private String nameEn;
    private String nameRu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id", referencedColumnName = "id")
    private SubCategoryEntity subCategory;

    private String descriptionAz;
    private String descriptionEn;
    private String descriptionRu;
    private Boolean isPopular;
    private String imageUrl;
    private BigDecimal price;
    private String currency;
    private Integer starCount;
    private Boolean isFlashSale;
    private BigDecimal discountPercent;
    private Boolean isJustForYou;

    @Transient
    private Long relationId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
