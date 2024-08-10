package capstone.dessert.mococobackend.repository;

import capstone.dessert.mococobackend.entity.Clothing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothingRepository extends JpaRepository<Clothing, Long>, JpaSpecificationExecutor<Clothing> {
}
