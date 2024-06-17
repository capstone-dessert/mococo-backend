package capstone.dessert.mococobackend.response;

import java.util.List;

public class RecommendResponse {
    private final List<Integer> ids;

    public RecommendResponse(RecommendationAPIResponse response) {
        this.ids = response.getIds();
    }
}
