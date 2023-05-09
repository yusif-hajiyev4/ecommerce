package az.aztu.ecommerce.model.constant;

public interface AuthConstants {

    String RSA = "RSA";
    String AUTH_CACHE_DATA_PREFIX = "ECOMMERCE_APP_SESSION:";
    String ISSUER = "ECOMMERCE_APP";
    int KEY_SIZE = 2048;
    Long TOKEN_EXPIRE_DAY_COUNT = 30L;
    Long OTP_STATUS_TIME = 60L;
    Long CHECK_PHONE_NUMBER_LIMIT = 5L;
}
