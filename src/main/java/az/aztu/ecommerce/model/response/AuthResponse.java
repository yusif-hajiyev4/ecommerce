package az.aztu.ecommerce.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class AuthResponse {

    private String accessToken;
    private String refreshToken;
}
