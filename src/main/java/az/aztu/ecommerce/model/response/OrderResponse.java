package az.aztu.ecommerce.model.response;

import az.aztu.ecommerce.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;
    private String productName;
    private String imageUrl;
    private BigDecimal price;
    private BigDecimal discountPercent;
    private String description;
    private OrderStatus status;
}
