package capstone.dessert.mococobackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Clothing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "subcategory")
    private String subcategory;

    @Column(name = "color", nullable = false)
    private String color;

    @Lob
    @Column(name = "image", nullable = false, columnDefinition = "BLOB")
    private byte[] image;

    @ManyToMany
    @JoinTable(
            name = "clothing_tags",
            joinColumns = @JoinColumn(name = "clothing_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    public Set<Tag> tags = new HashSet<>();

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getClothingItems().add(this);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.getClothingItems().remove(this);
    }
}
