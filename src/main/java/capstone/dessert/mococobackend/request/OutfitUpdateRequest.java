package capstone.dessert.mococobackend.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class OutfitUpdateRequest {

    private Long id;

    private LocalDate date;

    private String schedule;

    private List<Long> clothingIds;
}
