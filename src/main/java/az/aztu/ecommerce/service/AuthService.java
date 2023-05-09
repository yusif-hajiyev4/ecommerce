package az.aztu.ecommerce.service;

import az.aztu.ecommerce.dao.entity.LoginHistoryEntity;
import az.aztu.ecommerce.dao.entity.UserAuthEntity;
import az.aztu.ecommerce.dao.repository.AccountRepository;
import az.aztu.ecommerce.dao.repository.LoginHistoryRepository;
import az.aztu.ecommerce.dao.repository.UserAuthRepository;
import az.aztu.ecommerce.exception.AuthException;
import az.aztu.ecommerce.exception.BadRequestException;
import az.aztu.ecommerce.exception.NotFoundException;
import az.aztu.ecommerce.model.enums.AccountStatus;
import az.aztu.ecommerce.model.enums.LogType;
import az.aztu.ecommerce.model.request.SignInRequest;
import az.aztu.ecommerce.model.response.AuthPayloadResponse;
import az.aztu.ecommerce.model.response.AuthResponse;
import az.aztu.ecommerce.model.response.TokenResponse;
import az.aztu.ecommerce.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Calendar;
import java.util.Optional;

import static az.aztu.ecommerce.mapper.TokenMapper.buildAccessTokenClaimsSet;
import static az.aztu.ecommerce.mapper.TokenMapper.buildRefreshTokenClaimsSet;
import static az.aztu.ecommerce.model.constant.AuthConstants.RSA;
import static az.aztu.ecommerce.model.constant.ExceptionConstants.*;
import static jodd.util.Base64.encodeToString;
import static org.springframework.util.Base64Utils.decodeFromString;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserAuthRepository repository;
    private final AccountRepository accountRepository;
    private final LoginHistoryRepository loginHistoryRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;

    @Value("${jwt.accessToken.expiration.time}")
    private int accessTokenExpirationTime;

    @Value("${jwt.refreshToken.expiration.time}")
    private int refreshTokenExpirationTime;

    @Value("${jwt.refreshToken.expiration.count}")
    private int refreshTokenExpirationCount;

    public AuthResponse signIn(SignInRequest request) {
        log.info("ActionLog.start.signIn: username: {}", request.getUserName());

        var account = accountRepository.findByEmailAndStatus(request.getUserName(), AccountStatus.ACTIVE)
                .orElseThrow(() -> new NotFoundException(USERNAME_NOT_FOUND_MESSAGE, USERNAME_NOT_FOUND_CODE));

        if (!encoder.matches(request.getPassword(), account.getPassword())) {
            throw new BadRequestException(PASSWORD_IS_INCORRECT_MESSAGE, PASSWORD_IS_INCORRECT_CODE);
        }

        var tokenResponse = generateToken(account.getId(), refreshTokenExpirationCount);

        var loginHistoryEntity = LoginHistoryEntity.builder()
                .account(account)
                .type(LogType.LOGIN)
                .build();
        loginHistoryRepository.save(loginHistoryEntity);

        return AuthResponse.of(tokenResponse.getAccessToken(), tokenResponse.getRefreshToken());
    }

    private TokenResponse generateToken(Long accountId, int refreshTokenExpirationCount) {

        var accessTokenClaimsSet = buildAccessTokenClaimsSet(
                accountId,
                jwtUtil.generateSessionExpirationTime(accessTokenExpirationTime)
        );

        var refreshTokenClaimsSet = buildRefreshTokenClaimsSet(
                accountId,
                refreshTokenExpirationCount,
                jwtUtil.generateSessionExpirationTime(refreshTokenExpirationTime)
        );

        var keyPair = jwtUtil.generateKeyPair();

        saveAuthData(accountId, encodeToString(keyPair.getPublic().getEncoded()));

        var accessToken = jwtUtil.generateToken(accessTokenClaimsSet, keyPair.getPrivate());
        var refreshToken = jwtUtil.generateToken(refreshTokenClaimsSet, keyPair.getPrivate());

        return TokenResponse.of(accessToken, refreshToken);
    }

    public TokenResponse refreshToken(String refreshToken) {

        var refreshTokenClaimsSet = jwtUtil.getClaimsFromRefreshToken(refreshToken);
        var accountId = refreshTokenClaimsSet.getAccountId();

        try {

            var authData = Optional.ofNullable(getAuthData(accountId))
                    .orElseThrow(() -> new AuthException(USER_UNAUTHORIZED_MESSAGE, USER_UNAUTHORIZED_CODE, 401));

            var publicKey = KeyFactory.getInstance(RSA).generatePublic(
                    new X509EncodedKeySpec(decodeFromString(authData.getPublicKey()))
            );

            jwtUtil.verifyToken(refreshToken, (RSAPublicKey) publicKey);

            if (jwtUtil.isRefreshTokenTimeExpired(refreshTokenClaimsSet)) {
                throw new AuthException(REFRESH_TOKEN_EXPIRED_MESSAGE, USER_UNAUTHORIZED_CODE, 401);
            }

            if (jwtUtil.isRefreshTokenCountExpired(refreshTokenClaimsSet)) {
                throw new AuthException(REFRESH_TOKEN_COUNT_EXPIRED_MESSAGE, USER_UNAUTHORIZED_CODE, 401);
            }

            return generateToken(accountId, refreshTokenClaimsSet.getCount() - 1);
        } catch (AuthException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AuthException(USER_UNAUTHORIZED_MESSAGE, USER_UNAUTHORIZED_CODE, 401);
        }
    }

    public AuthPayloadResponse validateToken(String accessToken) {

        try {

            var accountId = jwtUtil.getClaimsFromAccessToken(accessToken).getAccountId();

            var authData = Optional.ofNullable(getAuthData(accountId))
                    .orElseThrow(() -> new AuthException(TOKEN_EXPIRED_MESSAGE, TOKEN_EXPIRED_CODE, 406));

            var publicKey = KeyFactory.getInstance(RSA).generatePublic(
                    new X509EncodedKeySpec(decodeFromString(authData.getPublicKey()))
            );

            jwtUtil.verifyToken(accessToken, (RSAPublicKey) publicKey);

            if (jwtUtil.isTokenExpired(authData.getExpiredAt())) {
                throw new AuthException(TOKEN_EXPIRED_MESSAGE, TOKEN_EXPIRED_CODE, 406);
            }

            return AuthPayloadResponse.of(accountId);
        } catch (AuthException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error(String.valueOf(ex));
            throw new AuthException(USER_UNAUTHORIZED_MESSAGE, USER_UNAUTHORIZED_CODE, 401);
        }
    }

    private void saveAuthData(Long accountId, String publicKey) {
        var account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException(ACCOUNT_NOT_FOUND_MESSAGE, ACCOUNT_NOT_FOUND_CODE));

        var userAuth = getAuthData(accountId);

        var calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 30);

        var entity = new UserAuthEntity();

        if (userAuth == null) {
            entity = UserAuthEntity.builder()
                    .account(account)
                    .publicKey(publicKey)
                    .expiredAt(calendar.getTime())
                    .build();
        } else {
            entity = userAuth;
            entity.setPublicKey(publicKey);
            entity.setExpiredAt(calendar.getTime());
        }

        repository.save(entity);
    }

    private UserAuthEntity getAuthData(Long accountId) {
        return repository.findByAccount_Id(accountId);
    }
}
