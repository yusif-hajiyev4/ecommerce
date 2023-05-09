package az.aztu.ecommerce.service;

import az.aztu.ecommerce.dao.repository.ShopRepository;
import az.aztu.ecommerce.mapper.ShopMapper;
import az.aztu.ecommerce.model.enums.Language;
import az.aztu.ecommerce.model.response.ShopResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShopService {

    private final ShopRepository repository;

    public List<ShopResponse> getShopList(Language language) {

        var shopList = repository.findAll();

        return shopList.stream()
                .map(shop -> ShopMapper.buildShopResponse(shop, language))
                .collect(Collectors.toList());
    }
}
