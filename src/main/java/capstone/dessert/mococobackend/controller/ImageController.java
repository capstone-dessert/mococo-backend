package capstone.dessert.mococobackend.controller;

import capstone.dessert.mococobackend.response.ImageInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/image")
public class ImageController {

    @Value("${image.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    @PostMapping(path = "/classify", consumes = MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_VALUE)
    public ImageInfoResponse classifyImage(@RequestParam("file") MultipartFile file) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<ImageInfoResponse> response = restTemplate.exchange(
                apiUrl + "/predict",
                HttpMethod.POST,
                requestEntity,
                ImageInfoResponse.class
        );

        return response.getBody();
    }
}
