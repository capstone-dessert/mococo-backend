package capstone.dessert.mococobackend.response;

import capstone.dessert.mococobackend.entity.Clothing;
import capstone.dessert.mococobackend.entity.Color;
import capstone.dessert.mococobackend.entity.Tag;
import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class ClothingResponse {

    private final Long id;

    private final String category;

    private final String subcategory;

    private final Set<String> colors;

    private final Set<String> tags;

    public ClothingResponse(Clothing clothing) {
        this.id = clothing.getId();
        this.category = clothing.getCategory();
        this.subcategory = clothing.getSubcategory();
        this.colors = clothing.getColors().stream().map(Color::getName).collect(Collectors.toSet());
        this.tags = clothing.getTags().stream().map(Tag::getName).collect(Collectors.toSet());
    }
}
