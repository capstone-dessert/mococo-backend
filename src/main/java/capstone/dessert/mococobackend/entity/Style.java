package capstone.dessert.mococobackend.entity;

import capstone.dessert.mococobackend.exception.StyleNotFoundException;
import lombok.Getter;

@Getter
public enum Style {
    FORMAL("포멀"),
    DANDY("댄디"),
    FEMININE("페미닌"),
    CASUAL("캐주얼"),
    STREET("스트릿"),
    SPORTY("스포티");

    private final String displayName;

    Style(String displayName) {
        this.displayName = displayName;
    }

    public static Style fromDisplayName(String displayName) {
        for (Style style : Style.values()) {
            if (style.getDisplayName().equals(displayName)) {
                return style;
            }
        }
        throw new StyleNotFoundException(displayName);
    }
}
