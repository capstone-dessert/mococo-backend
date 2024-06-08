package capstone.dessert.mococobackend.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class WeatherGeoRequest {

        private LocalDate date;
        private double latitude;  // 위도
        private double longitude;  // 경도
}
