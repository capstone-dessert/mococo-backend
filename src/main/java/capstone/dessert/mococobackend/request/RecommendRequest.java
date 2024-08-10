package capstone.dessert.mococobackend.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendRequest {

    private int minTemperature;
    private int maxTemperature;
    private String schedule;
}
