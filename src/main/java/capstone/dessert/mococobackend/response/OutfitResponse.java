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

    public OutfitResponse(Outfit outfit) {
        this.id = outfit.getId();
        this.date = outfit.getDate();
        this.schedule = outfit.getSchedule().getDisplayName();
        this.clothingItems = outfit.getClothingItems()
                .stream()
                .map(ClothingResponse::new)
                .collect(Collectors.toSet());
    }
}
