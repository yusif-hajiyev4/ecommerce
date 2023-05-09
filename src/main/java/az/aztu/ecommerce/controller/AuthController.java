package az.aztu.ecommerce.controller;

import az.aztu.ecommerce.model.request.SignInRequest;
import az.aztu.ecommerce.model.response.AuthResponse;
import az.aztu.ecommerce.model.response.TokenResponse;
import az.aztu.ecommerce.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static az.aztu.ecommerce.model.constant.Headers.REFRESH_TOKEN;

@RestController
@RequestMapping("v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping
    public AuthResponse signIn(
            @RequestBody SignInRequest request
    ) {
        return service.signIn(request);
    }

    @PostMapping("/refresh-token")
    public TokenResponse refreshToken(
            @RequestHeader(value = REFRESH_TOKEN) String refreshToken
    ) {
        return service.refreshToken(refreshToken);
    }
}
