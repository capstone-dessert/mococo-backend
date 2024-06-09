package capstone.dessert.mococobackend.service;

import capstone.dessert.mococobackend.exception.KakaoGeoAPIException;
import capstone.dessert.mococobackend.exception.KakaoSearchAPIException;
import capstone.dessert.mococobackend.exception.WeatherAPIException;
import capstone.dessert.mococobackend.request.WeatherAddressRequest;
import capstone.dessert.mococobackend.request.WeatherGeoRequest;
import capstone.dessert.mococobackend.response.KakaoGeoResponse;
import capstone.dessert.mococobackend.response.KakaoSearchResponse;
import capstone.dessert.mococobackend.response.WeatherAPIResponse;
import capstone.dessert.mococobackend.response.WeatherResponse;
import capstone.dessert.mococobackend.util.CoordinateConvertor;
import capstone.dessert.mococobackend.util.CoordinateConvertor.LatXLngY;
import lombok.Builder;
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

    private final HttpHeaders kakaoAPIHeader;

    @Value("${weather.api.url}")
    private String weatherAPIUrl;

    @Value("${weather.api.key}")
    private String weatherAPIKey;

    @Value("${kakao.api.geo.url}")
    private String kakaoGeoAPIUrl;

    @Value("${kakao.api.search.url}")
    private String kakaoSearchAPIUrl;

    public WeatherResponse searchWeatherByGeo(WeatherGeoRequest weatherRequest) {
        LatXLngY latXLngY = CoordinateConvertor.convertGpsToGrid(weatherRequest.getLatitude(), weatherRequest.getLongitude());

        WeatherAPIResponse.Response.Body.Items items = requestWeatherAPI(latXLngY);

        WeatherResponse weatherResponse = getWeatherResponse(weatherRequest.getDate(), items);
        weatherResponse.setAddressName(coordToAddressName(weatherRequest.getLatitude(), weatherRequest.getLongitude()));

        return weatherResponse;
    }

    private WeatherResponse getWeatherResponse(LocalDate date, WeatherAPIResponse.Response.Body.Items items) {
        List<WeatherAPIResponse.Response.Body.Items.Item> filteredItems = items.getItemList().stream()
                .filter(item -> localDateToString(date).equals(item.getFcstDate()) && (
                                "TMX".equals(item.getCategory()) ||
                                        "TMN".equals(item.getCategory()) ||
                                        ("PTY".equals(item.getCategory()) && "1500".equals(item.getFcstTime())) ||
                                        ("SKY".equals(item.getCategory()) && "1500".equals(item.getFcstTime()))
                        )
                )
                .toList();

        return getWeatherResponse(filteredItems);
    }

    private WeatherAPIResponse.Response.Body.Items requestWeatherAPI(LatXLngY latXLngY) {
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
        return response.getResponse().getBody().getItems();
    }

    private String coordToAddressName(double latitude, double longitude) {
        String requestUrl = String.format(
                "%s?x=%f&y=%f",
                kakaoGeoAPIUrl,
                longitude,
                latitude
        );

        HttpEntity<String> entity = new HttpEntity<>("parameters", kakaoAPIHeader);

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

    /**
     * 1. 주소를 위도 경도로 변환
     * 2. 위도 경도를 x, y좌표로 변환
     * 3. x, y좌표로 날씨 정보 요청
     * 4. 응답을 WeatherResponse로 변환
     * 5. 반환
     */
    public WeatherResponse searchWeatherAddress(WeatherAddressRequest weatherRequest) {
        Coordinate coord = addressToCoord(weatherRequest.getAddress());
        LatXLngY latXLngY = CoordinateConvertor.convertGpsToGrid(coord.latitude, coord.longitude);

        WeatherAPIResponse.Response.Body.Items items = requestWeatherAPI(latXLngY);
        WeatherResponse weatherResponse = getWeatherResponse(weatherRequest.getDate(), items);
        weatherResponse.setAddressName(weatherRequest.getAddress());

        return weatherResponse;
    }

    /**
     * 1. 주소를 위도 경도로 변환
     * 2. 반환
     */
    private Coordinate addressToCoord(String address) {
        String requestUrl = String.format(
                "%s?analyze_type=similar&query=%s",
                kakaoSearchAPIUrl,
                address
        );

        HttpEntity<String> entity = new HttpEntity<>("parameters", kakaoAPIHeader);

        ResponseEntity<KakaoSearchResponse> responseEntity = restTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                entity,
                KakaoSearchResponse.class
        );

        KakaoSearchResponse response = responseEntity.getBody();
        if (response == null || response.getDocuments() == null || response.getDocuments().isEmpty()) {
            throw new KakaoSearchAPIException();
        }
        return Coordinate.builder()
                .latitude(response.getDocuments().getFirst().getY())
                .longitude(response.getDocuments().getFirst().getX())
                .build();
    }

    @Builder
    private static class Coordinate {
        private double latitude;
        private double longitude;

        public Coordinate(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
}
