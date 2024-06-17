package capstone.dessert.mococobackend.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendationAPIRequest {

    private int min_temperature;

    private int max_temperature;

    private String schedule;
}
