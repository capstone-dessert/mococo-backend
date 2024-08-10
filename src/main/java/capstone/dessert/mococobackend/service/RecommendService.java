package capstone.dessert.mococobackend.service;

import capstone.dessert.mococobackend.exception.RecommendationAPIException;
import capstone.dessert.mococobackend.request.RecommendRequest;
import capstone.dessert.mococobackend.request.RecommendationAPIRequest;
import capstone.dessert.mococobackend.response.RecommendResponse;
import capstone.dessert.mococobackend.response.RecommendationAPIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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

        RecommendationAPIRequest request = new RecommendationAPIRequest();
        request.setMin_temperature(recommendRequest.getMinTemperature());
        request.setMax_temperature(recommendRequest.getMaxTemperature());
        request.setSchedule(recommendRequest.getSchedule());

        HttpEntity<RecommendationAPIRequest> requestEntity = new HttpEntity<>(request, headers);

        ResponseEntity<RecommendationAPIResponse> response = restTemplate.exchange(
                apiUrl + "/recommend",
                POST,
                requestEntity,
                RecommendationAPIResponse.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RecommendationAPIException("response status code is not 2xx");
        }

        if (response.getBody() == null) {
            throw new RecommendationAPIException("response body is null");
        }

        if (response.getBody().getIds().isEmpty()) {
            throw new RecommendationAPIException("nothing to recommend");
        }

        return new RecommendResponse(response.getBody());
    }
}
