package capstone.dessert.mococobackend.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ClothingSearchRequest {
    private String category;
    private String subcategory;
    private Set<String> colors;
    private Set<String> tags;
}
