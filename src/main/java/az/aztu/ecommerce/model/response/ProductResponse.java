package az.aztu.ecommerce.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Long id;
    private Long relationId;
    private String name;
    private String imageUrl;
    private BigDecimal price;
    private BigDecimal discountPercent;
    private Integer starCount;
}
