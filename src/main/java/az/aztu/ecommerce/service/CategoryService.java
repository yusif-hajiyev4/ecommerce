package az.aztu.ecommerce.service;

import az.aztu.ecommerce.dao.repository.CategoryRepository;
import az.aztu.ecommerce.dao.repository.SubCategoryRepository;
import az.aztu.ecommerce.mapper.CategoryMapper;
import az.aztu.ecommerce.model.enums.Language;
import az.aztu.ecommerce.model.response.CategoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    public List<CategoryResponse> getCategoryList(Language language) {

        var categoryList = categoryRepository.findAll();

        return categoryList.stream()
                .map(category -> CategoryMapper.buildCategoryResponse(category, language))
                .collect(Collectors.toList());
    }

    public List<CategoryResponse> getSubCategoryList(Long categoryId, Language language) {

        var subCategoryList = subCategoryRepository.findAllByCategory_Id(categoryId);

        return subCategoryList.stream()
                .map(category -> CategoryMapper.buildCategoryResponse(category, language))
                .collect(Collectors.toList());
    }
}
