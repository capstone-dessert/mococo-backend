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
@Table(name = "clothing")
public class Clothing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "subcategory")
    private String subcategory;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "clothing_colors",
            joinColumns = @JoinColumn(name = "clothing_id"),
            inverseJoinColumns = @JoinColumn(name = "color_id")
    )
    private Set<Color> colors = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "clothing_tags",
            joinColumns = @JoinColumn(name = "clothing_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    public Set<Tag> tags = new HashSet<>();

    @Lob
    @Column(name = "image", nullable = false, columnDefinition = "BLOB")
    private byte[] image;

    @ManyToMany(mappedBy = "clothingItems")
    private Set<Outfit> outfits = new HashSet<>();

    @ElementCollection(targetClass = Style.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "clothing_style", joinColumns = @JoinColumn(name = "clothing_id"))
    @Column(name = "style", nullable = false)
    private Set<Style> styles = new HashSet<>();

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getClothingItems().add(this);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.getClothingItems().remove(this);
    }

    public void addColor(Color color) {
        colors.add(color);
        color.getClothingItems().add(this);
    }

    public void removeColor(Color color) {
        colors.remove(color);
        color.getClothingItems().remove(this);
    }
}
