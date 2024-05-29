package capstone.dessert.mococobackend.repository;

import capstone.dessert.mococobackend.entity.Outfit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OutfitRepository extends JpaRepository<Outfit, Long> {

    List<Outfit> findAllByDate(LocalDate date);
}
