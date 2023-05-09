package az.aztu.ecommerce.mapper;

import az.aztu.ecommerce.dao.entity.AccountEntity;
import az.aztu.ecommerce.dao.entity.OrderEntity;
import az.aztu.ecommerce.dao.entity.PaymentEntity;
import az.aztu.ecommerce.model.request.PaymentRequest;

import static az.aztu.ecommerce.model.enums.PaymentStatus.SUCCESS;

public final class PaymentMapper {

    public static PaymentEntity buildPaymentEntity(PaymentRequest request, AccountEntity account, OrderEntity order) {
        return PaymentEntity.builder()
                .account(account)
                .order(order)
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .billingAddress(request.getBillingAddress())
                .status(SUCCESS)
                .build();
    }
}
