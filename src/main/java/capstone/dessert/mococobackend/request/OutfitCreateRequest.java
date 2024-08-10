package capstone.dessert.mococobackend.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class OutfitCreateRequest {

    private LocalDate date;

    private String schedule;

    private List<Long> clothingIds;

    private String addressName;

    private double maxTemperature;

    private double minTemperature;

    private String precipitationType;

    private String sky;

    private MultipartFile image;
}
