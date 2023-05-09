package az.aztu.ecommerce.mapper;

import az.aztu.ecommerce.dao.entity.*;
import az.aztu.ecommerce.model.enums.Language;
import az.aztu.ecommerce.model.enums.OrderStatus;
import az.aztu.ecommerce.model.request.OrderRequest;
import az.aztu.ecommerce.model.response.OrderResponse;

public final class OrderMapper {

    public static OrderEntity buildOrderEntity(OrderRequest request, AccountEntity account,
                                               ProductRelationsEntity productRelations, ProductSizeEntity size,
                                               ProductColorEntity color) {
        return OrderEntity.builder()
                .account(account)
                .productRelations(productRelations)
                .size(size)
                .color(color)
                .totalPrice(request.getTotalPrice())
                .currency(request.getCurrency())
                .isPay(false)
                .status(OrderStatus.PENDING)
                .build();
    }

    public static OrderResponse buildOrderResponse(OrderEntity entity, Language language) {
        return OrderResponse.builder()
                .id(entity.getId())
                .productName(getName(entity.getProductRelations().getProduct(), language))
                .imageUrl(entity.getProductRelations().getProduct().getImageUrl())
                .price(entity.getTotalPrice())
                .discountPercent(entity.getProductRelations().getProduct().getDiscountPercent())
                .description(getDescription(entity.getProductRelations().getProduct(), language))
                .status(entity.getStatus())
                .build();
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
