package az.aztu.ecommerce.mapper;

import az.aztu.ecommerce.dao.entity.ShopEntity;
import az.aztu.ecommerce.model.enums.Language;
import az.aztu.ecommerce.model.response.ShopResponse;

public final class ShopMapper {

    public static ShopResponse buildShopResponse(ShopEntity entity, Language language) {

        return ShopResponse.builder()
                .id(entity.getId())
                .name(getName(entity, language))
                .imageUrl(entity.getImageUrl())
                .build();
    }

    private static String getName(ShopEntity entity, Language language) {

        switch (language) {
            case EN:
                return entity.getNameEn();
            case RU:
                return entity.getNameRu();
            default:
                return entity.getNameAz();
        }
    }
}
