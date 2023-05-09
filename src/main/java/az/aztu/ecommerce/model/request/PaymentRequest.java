package az.aztu.ecommerce.model.request;

import az.aztu.ecommerce.model.enums.Currency;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {

    private Long orderId;
    private BigDecimal amount;
    private Currency currency;
    private String billingAddress;
}
