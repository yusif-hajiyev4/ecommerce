package az.aztu.ecommerce.service;

import az.aztu.ecommerce.dao.entity.OrderEntity;
import az.aztu.ecommerce.dao.repository.OrderRepository;
import az.aztu.ecommerce.dao.repository.ProductColorRepository;
import az.aztu.ecommerce.dao.repository.ProductRelationsRepository;
import az.aztu.ecommerce.dao.repository.ProductSizeRepository;
import az.aztu.ecommerce.exception.NotFoundException;
import az.aztu.ecommerce.model.enums.Language;
import az.aztu.ecommerce.model.request.OrderRequest;
import az.aztu.ecommerce.model.response.OrderResponse;
import az.aztu.ecommerce.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static az.aztu.ecommerce.mapper.OrderMapper.buildOrderEntity;
import static az.aztu.ecommerce.mapper.OrderMapper.buildOrderResponse;
import static az.aztu.ecommerce.model.constant.ExceptionConstants.ORDER_NOT_FOUND_CODE;
import static az.aztu.ecommerce.model.constant.ExceptionConstants.ORDER_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository repository;
    private final AccountService accountService;
    private final ProductRelationsRepository productRelationsRepository;
    private final ProductSizeRepository productSizeRepository;
    private final ProductColorRepository productColorRepository;
    private final AuthUtil authUtil;

    public void addOrder(String accessToken, OrderRequest request) {
        log.info("ActionLog.start.addOrder: request: {}", request.toString());

        var accountId = authUtil.getAccountId(accessToken);

        var account = accountService.getAccount(accountId);

        var productRelations = productRelationsRepository.findById(request.getRelationId())
                .orElse(null);

        var size = productSizeRepository.findById(request.getSizeId())
                .orElse(null);

        var color = productColorRepository.findById(request.getColorId())
                .orElse(null);

        repository.save(buildOrderEntity(request, account, productRelations, size, color));
    }

    public List<OrderResponse> getOrderList(String accessToken, Language language) {
        var accountId = authUtil.getAccountId(accessToken);

        var account = accountService.getAccount(accountId);

        var orderList = repository.findAllByAccount(account);

        return orderList.stream()
                .map(entity -> buildOrderResponse(entity, language))
                .collect(Collectors.toList());
    }

    public OrderEntity getOrder(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(ORDER_NOT_FOUND_MESSAGE, ORDER_NOT_FOUND_CODE));
    }
}
