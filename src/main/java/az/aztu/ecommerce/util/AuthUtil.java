package az.aztu.ecommerce.util;

import az.aztu.ecommerce.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthUtil {

    private final AuthService service;

    public Long getAccountId(String accessToken) {
        return service.validateToken(accessToken).getAccountId();
    }

    public void validateAuth(String accessToken) {
        service.validateToken(accessToken);
    }
}
