package az.aztu.ecommerce.controller;

import az.aztu.ecommerce.model.request.AccountEditRequest;
import az.aztu.ecommerce.model.request.AccountRequest;
import az.aztu.ecommerce.model.request.ChangePasswordRequest;
import az.aztu.ecommerce.model.response.AccountDetailsResponse;
import az.aztu.ecommerce.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static az.aztu.ecommerce.model.constant.Headers.AUTHORIZATION;

@RestController
@RequestMapping("v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createAccount(
            @RequestBody AccountRequest request
    ) {
        service.createAccount(request);
    }

    @GetMapping("/check")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void checkAccount(
            @RequestParam String username
    ) {
        service.checkAccount(username);
    }

    @GetMapping("/detail")
    public AccountDetailsResponse getAccountDetails(
            @RequestHeader(value = AUTHORIZATION) String accessToken
    ) {
        return service.getAccountDetails(accessToken);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editAccountDetails(
            @RequestHeader(value = AUTHORIZATION) String accessToken,
            @RequestBody AccountEditRequest request
    ) {
        service.editAccountDetails(accessToken, request);
    }

    @PutMapping("/change-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(
            @RequestHeader(value = AUTHORIZATION) String accessToken,
            @RequestBody ChangePasswordRequest request
    ) {
        service.changePassword(accessToken, request);
    }
}
