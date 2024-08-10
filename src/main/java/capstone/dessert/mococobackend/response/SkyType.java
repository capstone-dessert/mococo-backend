package capstone.dessert.mococobackend.response;

import capstone.dessert.mococobackend.exception.SkyTypeNotFoundException;
import lombok.Getter;

@Getter
public enum SkyType {
    // 맑음(1), 구름많음(3), 흐림(4)
    CLEAR(1, "맑음"),
    CLOUDY(3, "구름많음"),
    OVERCAST(4, "흐림");

    private final int code;
    private final String displayName;

    SkyType(int code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public static SkyType fromCode(int code) {
        for (SkyType skyType : SkyType.values()) {
            if (skyType.getCode() == code) {
                return skyType;
            }
        }
        throw new SkyTypeNotFoundException("Unknown code: " + code);
    }

    public static SkyType fromDisplayName(String displayName) {
        for (SkyType skyType : SkyType.values()) {
            if (skyType.getDisplayName().equals(displayName)) {
                return skyType;
            }
        }
        throw new SkyTypeNotFoundException("Unknown display name: " + displayName);
    }
}
