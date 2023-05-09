package az.aztu.ecommerce.controller;

import az.aztu.ecommerce.model.enums.Language;
import az.aztu.ecommerce.model.response.ProductColorResponse;
import az.aztu.ecommerce.model.response.ProductDetailResponse;
import az.aztu.ecommerce.model.response.ProductResponse;
import az.aztu.ecommerce.model.response.ProductSizeResponse;
import az.aztu.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static az.aztu.ecommerce.model.constant.Headers.LANGUAGE;

@RestController
@RequestMapping("v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping
    public List<ProductResponse> getProducts(
            @RequestHeader(value = LANGUAGE, defaultValue = "AZ") Language language,
            @RequestParam Long categoryId,
            @RequestParam(required = false) Long subCategoryId,
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Long shopId,
            @RequestParam(required = false) Long locationId
    ) {
        return service.getProducts(categoryId, subCategoryId, productId, shopId, locationId, language);
    }

    @GetMapping("/popular")
    public List<ProductResponse> getPopularProducts(
            @RequestHeader(value = LANGUAGE, defaultValue = "AZ") Language language
    ) {
        return service.getPopularProducts(language);
    }

    @GetMapping("/flash-sale")
    public List<ProductResponse> getFlashSaleProducts(
            @RequestHeader(value = LANGUAGE, defaultValue = "AZ") Language language
    ) {
        return service.getFlashSaleProducts(language);
    }

    @GetMapping("/just-for-you")
    public List<ProductResponse> getJustForYouProducts(
            @RequestHeader(value = LANGUAGE, defaultValue = "AZ") Language language
    ) {
        return service.getJustForYouProducts(language);
    }

    @GetMapping("/size")
    public List<ProductSizeResponse> getProductSizes() {
        return service.getProductSizes();
    }

    @GetMapping("/color")
    public List<ProductColorResponse> getProductColors() {
        return service.getProductColors();
    }

    @GetMapping("/{id}")
    public ProductDetailResponse getProductDetails(
            @RequestHeader(value = LANGUAGE, defaultValue = "AZ") Language language,
            @PathVariable Long id
    ) {
        return service.getProductDetails(id, language);
    }
}
