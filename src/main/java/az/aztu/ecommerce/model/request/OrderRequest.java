package az.aztu.ecommerce.model.request;

import az.aztu.ecommerce.model.enums.Currency;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderRequest {

    private Long relationId;
    private Long sizeId;
    private Long colorId;
    private BigDecimal totalPrice;
    private Currency currency;
}
