package az.aztu.ecommerce.model.jwt;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RefreshTokenClaimsSet {

    private Long accountId;
    private Date expirationTime;
    private Integer count;
    private String iss;
}
