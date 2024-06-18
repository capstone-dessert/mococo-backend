package capstone.dessert.mococobackend.controller;

import capstone.dessert.mococobackend.request.WeatherAddressRequest;
import capstone.dessert.mococobackend.request.WeatherGeoRequest;
import capstone.dessert.mococobackend.response.WeatherResponse;
import capstone.dessert.mococobackend.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @PostMapping(path = "/address", consumes = "application/json", produces = "application/json")
    public WeatherResponse getWeatherByDistrict(@RequestBody WeatherAddressRequest weatherRequest) {
        return weatherService.searchWeatherAddress(weatherRequest);
    }

    @PostMapping(path = "/geo", consumes = "application/json", produces = "application/json")
    public WeatherResponse getWeatherByGeo(@RequestBody WeatherGeoRequest weatherRequest) {
        return weatherService.searchWeatherByGeo(weatherRequest);
    }
}
