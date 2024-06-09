package capstone.dessert.mococobackend.service;

import capstone.dessert.mococobackend.exception.KakaoGeoAPIException;
import capstone.dessert.mococobackend.exception.WeatherAPIException;
import capstone.dessert.mococobackend.request.WeatherGeoRequest;
import capstone.dessert.mococobackend.response.KakaoGeoResponse;
import capstone.dessert.mococobackend.response.WeatherAPIResponse;
import capstone.dessert.mococobackend.response.WeatherResponse;
import capstone.dessert.mococobackend.util.CoordinateConvertor;
import capstone.dessert.mococobackend.util.CoordinateConvertor.LatXLngY;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final RestTemplate restTemplate;

    @Value("${weather.api.url}")
    private String weatherAPIUrl;

    @Value("${weather.api.key}")
    private String weatherAPIKey;

    @Value("${kakao.api.geo.url}")
    private String kakaoGeoAPIUrl;

    @Value("${kakao.api.key}")
    private String kakaoAPIKey;

    public WeatherResponse searchWeatherByGeo(WeatherGeoRequest weatherRequest) {
        LatXLngY latXLngY = CoordinateConvertor.convertGpsToGrid(weatherRequest.getLatitude(), weatherRequest.getLongitude());

        String requestUrl = String.format(
                "%s?serviceKey=%s&pageNo=%d&numOfRows=%d&dataType=%s&base_date=%s&base_time=%s&nx=%d&ny=%d",
                weatherAPIUrl,
                weatherAPIKey,
                1,
                1000,
                "JSON",
                localDateToString(LocalDate.now()),
                "0200",
                latXLngY.getX(),
                latXLngY.getY()
        );

        WeatherAPIResponse response = restTemplate.getForObject(requestUrl, WeatherAPIResponse.class);

        if (response == null || response.getResponse() == null) {
            throw new WeatherAPIException();
        }
        WeatherAPIResponse.Response.Body.Items items = response.getResponse().getBody().getItems();

        List<WeatherAPIResponse.Response.Body.Items.Item> filteredItems = items.getItemList().stream()
                .filter(item -> localDateToString(weatherRequest.getDate()).equals(item.getFcstDate()) && (
                                "TMX".equals(item.getCategory()) ||
                                        "TMN".equals(item.getCategory()) ||
                                        ("PTY".equals(item.getCategory()) && "1500".equals(item.getFcstTime())) ||
                                        ("SKY".equals(item.getCategory()) && "1500".equals(item.getFcstTime()))
                        )
                )
                .toList();

        WeatherResponse weatherResponse = getWeatherResponse(filteredItems);
        weatherResponse.setAddressName(coordToAddressName(weatherRequest.getLatitude(), weatherRequest.getLongitude()));

        return weatherResponse;
    }

    private String coordToAddressName(double latitude, double longitude) {
        String requestUrl = String.format(
                "%s?x=%f&y=%f",
                kakaoGeoAPIUrl,
                longitude,
                latitude
        );

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoAPIKey);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<KakaoGeoResponse> responseEntity = restTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                entity,
                KakaoGeoResponse.class
        );

        KakaoGeoResponse response = responseEntity.getBody();
        if (response == null || response.getDocuments() == null || response.getDocuments().isEmpty()) {
            throw new KakaoGeoAPIException();
        }

        return response.getDocuments().getFirst().getAddressName();
    }

    private WeatherResponse getWeatherResponse(List<WeatherAPIResponse.Response.Body.Items.Item> filteredItems) {
        WeatherResponse weatherResponse = new WeatherResponse();
        for (WeatherAPIResponse.Response.Body.Items.Item item : filteredItems) {
            switch (item.getCategory()) {
                case "TMX":
                    weatherResponse.setMaxTemperature(Double.parseDouble(item.getFcstValue()));
                    break;
                case "TMN":
                    weatherResponse.setMinTemperature(Double.parseDouble(item.getFcstValue()));
                    break;
                case "PTY":
                    weatherResponse.setPrecipitationType(item.getFcstValue());
                    break;
                case "SKY":
                    weatherResponse.setSky(item.getFcstValue());
                    break;
                default:
                    break;
            }
        }
        return weatherResponse;
    }

    public static String localDateToString(LocalDate date) {
        return String.join("", date.toString().split("-"));
    }
}
