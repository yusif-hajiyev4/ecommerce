package az.aztu.ecommerce.service;

import az.aztu.ecommerce.dao.entity.AccountEntity;
import az.aztu.ecommerce.dao.repository.AccountRepository;
import az.aztu.ecommerce.exception.BadRequestException;
import az.aztu.ecommerce.exception.NotFoundException;
import az.aztu.ecommerce.mapper.AccountMapper;
import az.aztu.ecommerce.model.enums.AccountStatus;
import az.aztu.ecommerce.model.request.AccountEditRequest;
import az.aztu.ecommerce.model.request.AccountRequest;
import az.aztu.ecommerce.model.request.ChangePasswordRequest;
import az.aztu.ecommerce.model.response.AccountDetailsResponse;
import az.aztu.ecommerce.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static az.aztu.ecommerce.model.constant.ExceptionConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository repository;
    private final AuthUtil authUtil;
    private final PasswordEncoder encoder;

    public void createAccount(AccountRequest request) {
        log.info(
                "ActionLog.createAccount.request: fullName: {}, email: {}, phoneNumber: {}",
                request.getFullName(), request.getEmail(), request.getPhoneNumber()
        );

        var account = repository.findByEmailAndStatus(request.getEmail(), AccountStatus.ACTIVE);

        if (account.isPresent()) {
            throw new BadRequestException(USER_IS_EXIST_MESSAGE, USER_IS_EXIST_CODE);
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException(PASSWORDS_DO_NOT_MATCH_MESSAGE, PASSWORDS_DO_NOT_MATCH_CODE);
        }

        var entity = AccountMapper.INSTANCE.toEntity(request);
        entity.setPassword(encoder.encode(entity.getPassword()));
        entity.setStatus(AccountStatus.ACTIVE);

        repository.save(entity);
    }

    public void checkAccount(String username) {
        log.info("ActionLog.start.checkAccount: username: {}", username);

        repository.findByEmailAndStatus(username, AccountStatus.ACTIVE)
                .orElseThrow(() -> new NotFoundException(USERNAME_NOT_FOUND_MESSAGE, USERNAME_NOT_FOUND_CODE));
    }

    public AccountDetailsResponse getAccountDetails(String accessToken) {
        var accountId = authUtil.getAccountId(accessToken);

        return AccountMapper.INSTANCE.toResponse(getAccount(accountId));
    }

    public void editAccountDetails(String accessToken, AccountEditRequest request) {
        var accountId = authUtil.getAccountId(accessToken);

        var account = getAccount(accountId);

        account.setFullName(request.getFullName());
        account.setEmail(request.getEmail());
        account.setPhoneNumber(request.getPhoneNumber());
        account.setAddress(request.getAddress());

        repository.save(account);
    }

    public void changePassword(String accessToken, ChangePasswordRequest request) {
        var accountId = authUtil.getAccountId(accessToken);

        var account = getAccount(accountId);

        if (!encoder.matches(request.getCurrentPassword(), account.getPassword())) {
            throw new BadRequestException(PASSWORD_IS_INCORRECT_MESSAGE, PASSWORD_IS_INCORRECT_CODE);
        }

        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            throw new BadRequestException(PASSWORDS_DO_NOT_MATCH_MESSAGE, PASSWORDS_DO_NOT_MATCH_CODE);
        }

        account.setPassword(encoder.encode(request.getNewPassword()));
        repository.save(account);
    }

    public AccountEntity getAccount(Long accountId) {
        return repository.findById(accountId)
                .orElseThrow(() -> new NotFoundException(ACCOUNT_NOT_FOUND_MESSAGE, ACCOUNT_NOT_FOUND_CODE));
    }
}
