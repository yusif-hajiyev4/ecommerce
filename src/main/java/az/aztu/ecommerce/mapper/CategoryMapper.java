package az.aztu.ecommerce.mapper;

import az.aztu.ecommerce.dao.entity.CategoryEntity;
import az.aztu.ecommerce.dao.entity.SubCategoryEntity;
import az.aztu.ecommerce.model.enums.Language;
import az.aztu.ecommerce.model.response.CategoryResponse;

public final class CategoryMapper {

    public static CategoryResponse buildCategoryResponse(CategoryEntity entity, Language language) {

        return CategoryResponse.builder()
                .id(entity.getId())
                .name(getName(entity, language))
                .imageUrl(entity.getImageUrl())
                .build();
    }

    public static CategoryResponse buildCategoryResponse(SubCategoryEntity entity, Language language) {

        return CategoryResponse.builder()
                .id(entity.getId())
                .name(getName(entity, language))
                .build();
    }

    private static String getName(CategoryEntity entity, Language language) {

        switch (language) {
            case EN:
                return entity.getNameEn();
            case RU:
                return entity.getNameRu();
            default:
                return entity.getNameAz();
        }
    }

    private static String getName(SubCategoryEntity entity, Language language) {

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
