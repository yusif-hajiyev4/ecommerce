package az.aztu.ecommerce.controller;

import az.aztu.ecommerce.model.enums.Language;
import az.aztu.ecommerce.model.response.CategoryResponse;
import az.aztu.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static az.aztu.ecommerce.model.constant.Headers.LANGUAGE;

@RestController
@RequestMapping("v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @GetMapping
    public List<CategoryResponse> getCategoryList(
            @RequestHeader(value = LANGUAGE, defaultValue = "AZ") Language language
    ) {
        return service.getCategoryList(language);
    }

    @GetMapping("/sub")
    public List<CategoryResponse> getSubCategoryList(
            @RequestHeader(value = LANGUAGE, defaultValue = "AZ") Language language,
            @RequestParam Long categoryId
    ) {
        return service.getSubCategoryList(categoryId, language);
    }
}
