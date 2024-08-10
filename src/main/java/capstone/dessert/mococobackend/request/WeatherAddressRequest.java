package capstone.dessert.mococobackend.request;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class WeatherAddressRequest {
    private LocalDate date;
    private String address;
}
