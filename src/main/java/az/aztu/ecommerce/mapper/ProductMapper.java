package az.aztu.ecommerce.mapper;

import az.aztu.ecommerce.dao.entity.ProductColorEntity;
import az.aztu.ecommerce.dao.entity.ProductEntity;
import az.aztu.ecommerce.dao.entity.ProductSizeEntity;
import az.aztu.ecommerce.model.enums.Language;
import az.aztu.ecommerce.model.response.ProductColorResponse;
import az.aztu.ecommerce.model.response.ProductDetailResponse;
import az.aztu.ecommerce.model.response.ProductResponse;
import az.aztu.ecommerce.model.response.ProductSizeResponse;

import javax.persistence.Tuple;
import java.math.BigDecimal;

public final class ProductMapper {

    public static ProductResponse buildProductResponse(ProductEntity entity, Language language) {

        return ProductResponse.builder()
                .id(entity.getId())
                .name(getName(entity, language))
                .imageUrl(entity.getImageUrl())
                .price(entity.getPrice())
                .discountPercent(entity.getDiscountPercent())
                .starCount(entity.getStarCount())
                .build();
    }

    public static ProductResponse buildProductResponse(Tuple tuple, Language language) {

        return ProductResponse.builder()
                .id((Long) tuple.get("id"))
                .relationId((Long) tuple.get("relationId"))
                .name(getName(tuple, language))
                .imageUrl((String) tuple.get("imageUrl"))
                .price((BigDecimal) tuple.get("price"))
                .discountPercent((BigDecimal) tuple.get("discountPercent"))
                .starCount((Integer) tuple.get("starCount"))
                .build();
    }

    public static ProductDetailResponse buildProductDetailsResponse(ProductEntity entity, Language language) {

        return ProductDetailResponse.builder()
                .id(entity.getId())
                .name(getName(entity, language))
                .imageUrl(entity.getImageUrl())
                .price(entity.getPrice())
                .description(getDescription(entity, language))
                .build();
    }

    public static ProductSizeResponse buildProductSizeResponse(ProductSizeEntity entity) {

        return ProductSizeResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public static ProductColorResponse buildProductColorResponse(ProductColorEntity entity) {

        return ProductColorResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    private static String getName(Tuple tuple, Language language) {

        switch (language) {
            case EN:
                return (String) tuple.get("nameEn");
            case RU:
                return (String) tuple.get("nameRu");
            default:
                return (String) tuple.get("nameAz");
        }
    }

    private static String getName(ProductEntity entity, Language language) {

        switch (language) {
            case EN:
                return entity.getNameEn();
            case RU:
                return entity.getNameRu();
            default:
                return entity.getNameAz();
        }
    }

    private static String getDescription(ProductEntity entity, Language language) {

        switch (language) {
            case EN:
                return entity.getDescriptionEn();
            case RU:
                return entity.getDescriptionRu();
            default:
                return entity.getDescriptionAz();
        }
    }
}
