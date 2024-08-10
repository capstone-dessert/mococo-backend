package capstone.dessert.mococobackend.response;

import capstone.dessert.mococobackend.exception.PrecipitationTypeNotFoundException;
import lombok.Getter;

@Getter
public enum PrecipitationType {
    // 없음(0), 비(1), 비/눈(2), 눈(3), 소나기(4)
    NONE(0, "없음"),
    RAIN(1, "비"),
    RAIN_SNOW(2, "비/눈"),
    SNOW(3, "눈"),
    SHOWER(4, "소나기");

    private final int code;
    private final String displayName;

    PrecipitationType(int code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public static PrecipitationType fromCode(int code) {
        for (PrecipitationType precipitationType : PrecipitationType.values()) {
            if (precipitationType.getCode() == code) {
                return precipitationType;
            }
        }
        throw new PrecipitationTypeNotFoundException("Unknown code: " + code);
    }

    public static PrecipitationType fromDisplayName(String displayName) {
        for (PrecipitationType precipitationType : PrecipitationType.values()) {
            if (precipitationType.getDisplayName().equals(displayName)) {
                return precipitationType;
            }
        }
        throw new PrecipitationTypeNotFoundException("Unknown display name: " + displayName);
    }

}
