package capstone.dessert.mococobackend.controller;

import capstone.dessert.mococobackend.request.RecommendRequest;
import capstone.dessert.mococobackend.response.RecommendResponse;
import capstone.dessert.mococobackend.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class RecommendController {

    private final RecommendService recommendService;

    @PostMapping(path = "/recommend", consumes = "application/json", produces = "application/json")
    public RecommendResponse recommend(@RequestBody RecommendRequest recommendRequest) {
        return recommendService.getRecommendation(recommendRequest);
    }
}
