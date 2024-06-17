package capstone.dessert.mococobackend.response;

import lombok.Getter;

import java.util.List;

@Getter
public class RecommendResponse {
    private final List<Integer> ids;

    public RecommendResponse(RecommendationAPIResponse response) {
        this.ids = response.getIds();
    }
}
