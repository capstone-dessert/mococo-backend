package capstone.dessert.mococobackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Embeddable
@Getter
@Setter
public class Weather {

    @Column(name = "address_name", nullable = false)
    private String addressName;

    @Column(name = "max_temperature", nullable = false)
    private double maxTemperature;

    @Column(name = "min_temperature", nullable = false)
    private double minTemperature;

    @Column(name = "precipitation_type", nullable = false)
    private String precipitationType;

    @Column(name = "sky", nullable = false)
    private String sky;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weather weather = (Weather) o;
        return Double.compare(maxTemperature, weather.maxTemperature) == 0 && Double.compare(minTemperature, weather.minTemperature) == 0 && Objects.equals(addressName, weather.addressName) && Objects.equals(precipitationType, weather.precipitationType) && Objects.equals(sky, weather.sky);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressName, maxTemperature, minTemperature, precipitationType, sky);
    }
}
