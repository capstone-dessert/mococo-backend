package capstone.dessert.mococobackend.service;

import capstone.dessert.mococobackend.exception.RecommendationAPIException;
import capstone.dessert.mococobackend.request.RecommendRequest;
import capstone.dessert.mococobackend.response.RecommendResponse;
import capstone.dessert.mococobackend.response.RecommendationAPIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RequiredArgsConstructor
@Service
public class RecommendService {

    private final RestTemplate restTemplate;

    @Value("${recommend.api.url}")
    private String apiUrl;

    public RecommendResponse getRecommendation(RecommendRequest recommendRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("min_temperature", recommendRequest.getMinTemperature());
        body.add("max_temperature", recommendRequest.getMaxTemperature());
        body.add("schedule", recommendRequest.getSchedule());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<RecommendationAPIResponse> response = restTemplate.exchange(
                apiUrl + "/recommend",
                POST,
                requestEntity,
                RecommendationAPIResponse.class
        );

        if (response.getStatusCode().value() % 100 != 2) {
            throw new RecommendationAPIException();
        }

        if (response.getBody() == null) {
            throw new RecommendationAPIException("response body is null");
        }

        return new RecommendResponse(response.getBody());
    }
}
