package az.aztu.ecommerce.service;

import az.aztu.ecommerce.dao.repository.LocationRepository;
import az.aztu.ecommerce.model.response.LocationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationService {

    private final LocationRepository repository;

    public List<LocationResponse> getLocationList() {

        var locationList = repository.findAll();

        return locationList.stream()
                .map(location -> LocationResponse.of(location.getId(), location.getName()))
                .collect(Collectors.toList());
    }
}
