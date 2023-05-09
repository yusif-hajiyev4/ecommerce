package az.aztu.ecommerce.controller;

import az.aztu.ecommerce.model.enums.Language;
import az.aztu.ecommerce.model.response.ShopResponse;
import az.aztu.ecommerce.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static az.aztu.ecommerce.model.constant.Headers.LANGUAGE;

@RestController
@RequestMapping("v1/shops")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService service;

    @GetMapping
    public List<ShopResponse> getShopList(
            @RequestHeader(value = LANGUAGE, defaultValue = "AZ") Language language
    ) {
        return service.getShopList(language);
    }
}
