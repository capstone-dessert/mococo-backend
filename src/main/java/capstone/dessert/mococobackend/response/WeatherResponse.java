package capstone.dessert.mococobackend.response;

import lombok.Getter;
import lombok.Setter;

@Getter
public class WeatherResponse {

    @Setter
    private String addressName;

    // 최고기온, 최저기온, 강수형태, 하늘상태
    @Setter
    private double maxTemperature;
    @Setter
    private double minTemperature;

    private String precipitationType;
    private String sky;

    public void setPrecipitationType(String precipitationType) {
        this.precipitationType = PrecipitationType.fromCode(Integer.parseInt(precipitationType)).getDisplayName();
    }

    public void setSky(String sky) {
        this.sky = SkyType.fromCode(Integer.parseInt(sky)).getDisplayName();
    }
}
