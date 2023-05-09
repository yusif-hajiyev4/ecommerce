package az.aztu.ecommerce.model.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AccountRequest {

    @NotEmpty
    private String fullName;

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String confirmPassword;

    @NotEmpty
    private String phoneNumber;

    @NotEmpty
    private String address;
}
