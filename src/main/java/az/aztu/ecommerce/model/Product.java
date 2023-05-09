package az.aztu.ecommerce.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {

    private Long id;
    private Long relationId;
    private String nameAz;
    private String nameEn;
    private String nameRu;
    private String imageUrl;
    private BigDecimal price;
    private Integer starCount;
    private BigDecimal discountPercent;
}
