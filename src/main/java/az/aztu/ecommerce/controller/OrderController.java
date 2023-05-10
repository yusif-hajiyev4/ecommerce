package az.aztu.ecommerce.controller;

import az.aztu.ecommerce.model.enums.Language;
import az.aztu.ecommerce.model.request.OrderRequest;
import az.aztu.ecommerce.model.response.OrderResponse;
import az.aztu.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static az.aztu.ecommerce.model.constant.Headers.AUTHORIZATION;
import static az.aztu.ecommerce.model.constant.Headers.LANGUAGE;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addOrder(
            @RequestHeader(value = AUTHORIZATION) String accessToken,
            @RequestBody OrderRequest request
    ) {
        service.addOrder(accessToken, request);
    }

    @GetMapping
    public List<OrderResponse> getOrderList(
            @RequestHeader(value = LANGUAGE, defaultValue = "AZ") Language language,
            @RequestHeader(value = AUTHORIZATION) String accessToken
    ) {
        return service.getOrderList(accessToken, language);
    }
}
