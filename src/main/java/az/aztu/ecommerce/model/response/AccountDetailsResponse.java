package az.aztu.ecommerce.model.response;

import lombok.Data;

@Data
public class AccountDetailsResponse {

    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
}
