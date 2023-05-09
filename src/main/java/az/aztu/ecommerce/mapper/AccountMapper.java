package az.aztu.ecommerce.mapper;

import az.aztu.ecommerce.dao.entity.AccountEntity;
import az.aztu.ecommerce.model.request.AccountRequest;
import az.aztu.ecommerce.model.response.AccountDetailsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class AccountMapper {

    public static final AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    public abstract AccountEntity toEntity(AccountRequest request);

    public abstract AccountDetailsResponse toResponse(AccountEntity entity);
}
