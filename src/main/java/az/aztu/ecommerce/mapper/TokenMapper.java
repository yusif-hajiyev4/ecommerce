package az.aztu.ecommerce.mapper;

import az.aztu.ecommerce.model.jwt.AccessTokenClaimsSet;
import az.aztu.ecommerce.model.jwt.RefreshTokenClaimsSet;
import lombok.NoArgsConstructor;

import java.util.Date;

import static az.aztu.ecommerce.model.constant.AuthConstants.ISSUER;
import static az.aztu.ecommerce.util.DateUtil.toDate;
import static java.time.LocalDateTime.now;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class TokenMapper {

    public static AccessTokenClaimsSet buildAccessTokenClaimsSet(Long accountId, Date expirationTime) {

        return AccessTokenClaimsSet.builder()
                .iss(ISSUER)
                .accountId(accountId)
                .createdTime(toDate(now()))
                .expirationTime(expirationTime)
                .build();
    }

    public static RefreshTokenClaimsSet buildRefreshTokenClaimsSet(Long accountId, int refreshTokenExpirationCount, Date expirationTime) {

        return RefreshTokenClaimsSet.builder()
                .iss(ISSUER)
                .accountId(accountId)
                .expirationTime(expirationTime)
                .count(refreshTokenExpirationCount)
                .build();
    }
}
