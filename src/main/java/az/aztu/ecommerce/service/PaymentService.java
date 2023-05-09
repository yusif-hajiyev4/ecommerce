package az.aztu.ecommerce.service;

import az.aztu.ecommerce.dao.repository.PaymentRepository;
import az.aztu.ecommerce.model.enums.OrderStatus;
import az.aztu.ecommerce.model.request.PaymentRequest;
import az.aztu.ecommerce.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static az.aztu.ecommerce.mapper.PaymentMapper.buildPaymentEntity;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository repository;
    private final AccountService accountService;
    private final OrderService orderService;
    private final AuthUtil authUtil;

    public void createPayment(String accessToken, PaymentRequest request) {
        log.info("ActionLog.start.createPayment: request: {}", request.toString());

        var accountId = authUtil.getAccountId(accessToken);

        var account = accountService.getAccount(accountId);

        var order = orderService.getOrder(request.getOrderId());
        order.setStatus(OrderStatus.SUCCESS);
        order.setIsPay(true);

        repository.save(buildPaymentEntity(request, account, order));
    }
}
