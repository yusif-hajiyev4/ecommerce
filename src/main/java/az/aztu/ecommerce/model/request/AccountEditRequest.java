package az.aztu.ecommerce.model.request;

import lombok.Data;

@Data
public class AccountEditRequest {

    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
}
