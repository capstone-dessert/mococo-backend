package capstone.dessert.mococobackend.response;

import capstone.dessert.mococobackend.entity.Outfit;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class OutfitResponse {

    private final Long id;

    private final LocalDate date;

    private final String schedule;

    private final Set<ClothingResponse> clothingItems;

    private final Weather weather;

    public OutfitResponse(Outfit outfit) {
        this.id = outfit.getId();
        this.date = outfit.getDate();
        this.schedule = outfit.getSchedule().getDisplayName();
        this.clothingItems = outfit.getClothingItems()
                .stream()
                .map(ClothingResponse::new)
                .collect(Collectors.toSet());
        this.weather = new Weather(outfit);
    }

    @Getter
    private static class Weather {
        private final String addressName;
        private final double maxTemperature;
        private final double minTemperature;
        private final String precipitationType;
        private final String sky;

        public Weather(Outfit outfit) {
            this.addressName = outfit.getWeather().getAddressName();
            this.maxTemperature = outfit.getWeather().getMaxTemperature();
            this.minTemperature = outfit.getWeather().getMinTemperature();
            this.precipitationType = outfit.getWeather().getPrecipitationType();
            this.sky = outfit.getWeather().getSky();
        }
    }
}
