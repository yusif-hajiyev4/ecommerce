package az.aztu.ecommerce.service;

import az.aztu.ecommerce.dao.repository.ProductColorRepository;
import az.aztu.ecommerce.dao.repository.ProductRelationsRepository;
import az.aztu.ecommerce.dao.repository.ProductRepository;
import az.aztu.ecommerce.dao.repository.ProductSizeRepository;
import az.aztu.ecommerce.exception.NotFoundException;
import az.aztu.ecommerce.mapper.ProductMapper;
import az.aztu.ecommerce.model.enums.Language;
import az.aztu.ecommerce.model.response.ProductColorResponse;
import az.aztu.ecommerce.model.response.ProductDetailResponse;
import az.aztu.ecommerce.model.response.ProductResponse;
import az.aztu.ecommerce.model.response.ProductSizeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static az.aztu.ecommerce.model.constant.ExceptionConstants.PRODUCT_NOT_FOUND_CODE;
import static az.aztu.ecommerce.model.constant.ExceptionConstants.PRODUCT_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository repository;
    private final ProductSizeRepository productSizeRepository;
    private final ProductColorRepository productColorRepository;
    private final ProductRelationsRepository productRelationsRepository;

    public List<ProductResponse> getPopularProducts(Language language) {
        var productList = repository.findAllByIsPopular(true);

        return productList.stream()
                .map(productEntity -> ProductMapper.buildProductResponse(productEntity, language))
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getFlashSaleProducts(Language language) {
        var productList = repository.findAllByIsFlashSale(true);

        return productList.stream()
                .map(productEntity -> ProductMapper.buildProductResponse(productEntity, language))
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getJustForYouProducts(Language language) {
        var productList = repository.findAllByIsJustForYou(true);

        return productList.stream()
                .map(productEntity -> ProductMapper.buildProductResponse(productEntity, language))
                .collect(Collectors.toList());
    }

    public List<ProductSizeResponse> getProductSizes() {
        var productSizeList = productSizeRepository.findAll();

        return productSizeList.stream()
                .map(ProductMapper::buildProductSizeResponse)
                .collect(Collectors.toList());
    }

    public List<ProductColorResponse> getProductColors() {
        var productColorList = productColorRepository.findAll();

        return productColorList.stream()
                .map(ProductMapper::buildProductColorResponse)
                .collect(Collectors.toList());
    }

    public ProductDetailResponse getProductDetails(Long id, Language language) {
        var productEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(PRODUCT_NOT_FOUND_MESSAGE, PRODUCT_NOT_FOUND_CODE));

        return ProductMapper.buildProductDetailsResponse(productEntity, language);
    }

    public List<ProductResponse> getProducts(Long categoryId, Long subCategoryId, Long productId, Long shopId, Long locationId, Language language) {
        var productList = productRelationsRepository.getProducts(categoryId, subCategoryId, productId, shopId, locationId);

        return productList.stream()
                .map(productEntity -> ProductMapper.buildProductResponse(productEntity, language))
                .collect(Collectors.toList());
    }
}
