package capstone.dessert.mococobackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "outfit")
public class Outfit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "schedule", nullable = true)
    private String schedule;

    @ManyToMany
    @JoinTable(
            name = "outfit_clothing",
            joinColumns = @JoinColumn(name = "outfit_id"),
            inverseJoinColumns = @JoinColumn(name = "clothing_id")
    )
    private Set<Clothing> clothingItems = new HashSet<>();

    public void addClothing(Clothing clothing) {
        clothingItems.add(clothing);
        clothing.getOutfits().add(this);
    }
}
