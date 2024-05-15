package capstone.dessert.mococobackend.specifications;

import capstone.dessert.mococobackend.entity.Clothing;
import capstone.dessert.mococobackend.entity.Color;
import capstone.dessert.mococobackend.entity.Tag;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

public class ClothingSpecifications {

    private ClothingSpecifications() {
    }

    public static Specification<Clothing> hasCategory(String category) {
        return (root, query, cb) -> cb.equal(root.get("category"), category);
    }

    public static Specification<Clothing> hasSubcategory(String subcategory) {
        return (root, query, cb) -> cb.equal(root.get("subcategory"), subcategory);
    }

    public static Specification<Clothing> hasColors(Set<String> colors) {
        return (root, query, cb) -> {
            Join<Clothing, Color> colorJoin = root.join("colors");
            return colorJoin.get("name").in(colors);
        };
    }

    public static Specification<Clothing> hasTags(Set<String> tags) {
        return (root, query, cb) -> {
            Join<Clothing, Tag> tagJoin = root.join("tags");
            return tagJoin.get("name").in(tags);
        };
    }
}
