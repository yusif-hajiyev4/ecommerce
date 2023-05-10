package az.aztu.ecommerce.controller;

import az.aztu.ecommerce.model.request.PaymentRequest;
import az.aztu.ecommerce.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static az.aztu.ecommerce.model.constant.Headers.AUTHORIZATION;

@RestController
@RequestMapping("api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createPayment(
            @RequestHeader(value = AUTHORIZATION) String accessToken,
            @RequestBody PaymentRequest request
    ) {
        service.createPayment(accessToken, request);
    }
}
