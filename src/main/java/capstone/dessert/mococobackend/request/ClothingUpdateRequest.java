package capstone.dessert.mococobackend.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Getter
@Setter
public class ClothingUpdateRequest {
    private String category;
    private String subcategory;
    private Set<String> colors;
    private Set<String> tags;
    private Set<String> styles;
    private MultipartFile image;
}
