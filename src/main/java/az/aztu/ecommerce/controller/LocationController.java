package az.aztu.ecommerce.controller;

import az.aztu.ecommerce.model.response.LocationResponse;
import az.aztu.ecommerce.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService service;

    @GetMapping
    public List<LocationResponse> getLocationList() {
        return service.getLocationList();
    }
}
