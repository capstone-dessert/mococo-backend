package capstone.dessert.mococobackend.repository;

import capstone.dessert.mococobackend.entity.Outfit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OutfitRepository extends JpaRepository<Outfit, Long> {

    List<Outfit> findAllByDate(LocalDate date);

    @Query("SELECT o FROM Outfit o JOIN o.clothingItems c WHERE c.id = :clothingId")
    List<Outfit> findByClothingId(@Param("clothingId") Long clothingId);
}
