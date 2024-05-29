package capstone.dessert.mococobackend.service;

import capstone.dessert.mococobackend.entity.Clothing;
import capstone.dessert.mococobackend.entity.Outfit;
import capstone.dessert.mococobackend.exception.ClothingNotFoundException;
import capstone.dessert.mococobackend.exception.OutfitNotFoundException;
import capstone.dessert.mococobackend.repository.ClothingRepository;
import capstone.dessert.mococobackend.repository.OutfitRepository;
import capstone.dessert.mococobackend.request.OutfitCreateRequest;
import capstone.dessert.mococobackend.request.OutfitDateRequest;
import capstone.dessert.mococobackend.request.OutfitUpdateRequest;
import capstone.dessert.mococobackend.response.OutfitResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OutfitService {

    private final OutfitRepository outfitRepository;

    private final ClothingRepository clothingRepository;

    public void save(OutfitCreateRequest outfitCreateRequest) {

        Set<Clothing> clothingItems = outfitCreateRequest.getClothingIds()
                .stream()
                .map(id -> clothingRepository.findById(id)
                        .orElseThrow(ClothingNotFoundException::new))
                .collect(Collectors.toSet());

        Outfit outfit = new Outfit();
        outfit.setDate(outfitCreateRequest.getDate());
        outfit.setSchedule(outfitCreateRequest.getSchedule());
        outfit.setClothingItems(clothingItems);

        outfitRepository.save(outfit);
    }

    public List<OutfitResponse> getAllOutfit() {
        return outfitRepository.findAll()
                .stream()
                .map(OutfitResponse::new)
                .toList();
    }

    public OutfitResponse getOutfitById(Long id) {
        return new OutfitResponse(outfitRepository.findById(id)
                .orElseThrow(OutfitNotFoundException::new));
    }

    public List<OutfitResponse> getOutfitByDate(OutfitDateRequest outfitDateRequest) {
        return outfitRepository.findAllByDate(outfitDateRequest.getDate())
                .stream()
                .map(OutfitResponse::new)
                .toList();
    }

    @Transactional
    public void updateOutfit(OutfitUpdateRequest outfitUpdateRequest) {
        Outfit outfit = outfitRepository.findById(outfitUpdateRequest.getId())
                .orElseThrow(OutfitNotFoundException::new);

        outfit.setDate(outfitUpdateRequest.getDate());
        outfit.setSchedule(outfitUpdateRequest.getSchedule());

        Set<Clothing> clothingItems = outfitUpdateRequest.getClothingIds()
                .stream()
                .map(id -> clothingRepository.findById(id)
                        .orElseThrow(ClothingNotFoundException::new))
                .collect(Collectors.toSet());

        updateOutfitClothing(outfit, clothingItems);
    }

    private void updateOutfitClothing(Outfit outfit, Set<Clothing> clothingItems) {
        outfit.getClothingItems().clear();
        outfit.getClothingItems().addAll(clothingItems);
        outfit.setClothingItems(clothingItems);
    }

    public void deleteOutfit(Long id) {
        if (!outfitRepository.existsById(id)) {
            throw new OutfitNotFoundException();
        }
        outfitRepository.deleteById(id);
    }
}
