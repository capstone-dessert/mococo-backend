package capstone.dessert.mococobackend.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Getter
@Setter
public class ClothingRequest {

    private String category;
    private String subcategory;
    private String color;
    private MultipartFile image;
    private Set<String> tags; // Assuming tags are provided as a set of strings

}

