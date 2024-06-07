package capstone.dessert.mococobackend.service;

import capstone.dessert.mococobackend.entity.Clothing;
import capstone.dessert.mococobackend.entity.Color;
import capstone.dessert.mococobackend.entity.Tag;
import capstone.dessert.mococobackend.exception.ClothingNotFoundException;
import capstone.dessert.mococobackend.repository.ClothingRepository;
import capstone.dessert.mococobackend.repository.ColorRepository;
import capstone.dessert.mococobackend.repository.TagRepository;
import capstone.dessert.mococobackend.request.ClothingRequest;
import capstone.dessert.mococobackend.request.ClothingSearchRequest;
import capstone.dessert.mococobackend.request.ClothingUpdateRequest;
import capstone.dessert.mococobackend.specifications.ClothingSpecifications;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ClothingService {

    private final ClothingRepository clothingRepository;
    private final TagRepository tagRepository;
    private final ColorRepository colorRepository;

    @Transactional
    public void save(ClothingRequest clothingRequest, byte[] image) {
        Clothing clothing = getClothing(clothingRequest, image);

        // Handle the tags: check if they exist, add new if not
        Set<Tag> managedTags = new HashSet<>();
        for (Tag tag : clothing.getTags()) {
            Tag existingTag = tagRepository.findByName(tag.getName())
                    .orElse(null);

            if (existingTag == null) {
                // Tag doesn't exist, save it
                existingTag = tagRepository.save(tag);
            }
            managedTags.add(existingTag);
        }
        clothing.setTags(managedTags);

        // Now save the clothing, which now includes tags
        clothingRepository.save(clothing);
    }

    public List<Clothing> getAllClothing() {
        return clothingRepository.findAll();
    }

    public Clothing getClothingById(Long id) {
        return clothingRepository.findById(id)
                .orElseThrow(ClothingNotFoundException::new);
    }

    public byte[] getClothingImageById(Long id) {
        Clothing clothing = clothingRepository.findById(id)
                .orElseThrow(ClothingNotFoundException::new);

        return clothing.getImage();
    }

    @Transactional
    public void updateClothing(Long id, ClothingUpdateRequest request, byte[] image) {
        Clothing clothing = clothingRepository.findById(id)
                .orElseThrow(ClothingNotFoundException::new);

        clothing.setCategory(request.getCategory());
        clothing.setSubcategory(request.getSubcategory());

        if (image != null) {
            clothing.setImage(image);
        }

        updateClothingColors(clothing, request.getColors());
        updateClothingTags(clothing, request.getTags());

        clothingRepository.save(clothing);
    }

    @Transactional
    public void deleteClothing(Long id) {

        Clothing clothing = clothingRepository.findById(id)
                .orElseThrow(ClothingNotFoundException::new);

        clothing.getColors().clear();

        clothingRepository.deleteById(id);
    }

    public List<Clothing> searchClothing(ClothingSearchRequest criteria) {
        Specification<Clothing> spec = Specification.where(null);

        if (criteria.getCategory() != null) {
            spec = spec.and(ClothingSpecifications.hasCategory(criteria.getCategory()));
        }
        if (criteria.getSubcategory() != null) {
            spec = spec.and(ClothingSpecifications.hasSubcategory(criteria.getSubcategory()));
        }
        if (criteria.getColors() != null && !criteria.getColors().isEmpty()) {
            spec = spec.and(ClothingSpecifications.hasColors(criteria.getColors()));
        }
        if (criteria.getTags() != null && !criteria.getTags().isEmpty()) {
            spec = spec.and(ClothingSpecifications.hasTags(criteria.getTags()));
        }

        return clothingRepository.findAll(spec);
    }

    private void updateClothingColors(Clothing clothing, Set<String> colorNames) {
        Set<Color> currentColors = clothing.getColors();
        Set<Color> newColors = colorNames.stream()
                .map(name -> colorRepository.findByName(name.toLowerCase())
                        .orElse(new Color(name.toLowerCase())))
                .collect(Collectors.toSet());

        // Remove old colors not present in the new set
        currentColors.removeIf(color -> !newColors.contains(color));

        // Add new colors that are not already present
        currentColors.addAll(newColors);
        clothing.setColors(currentColors);
    }

    private void updateClothingTags(Clothing clothing, Set<String> tagNames) {
        Set<Tag> managedTags = new HashSet<>();
        for (String tagName : tagNames) {
            Tag tag = tagRepository.findByName(tagName)
                    .orElse(null);
            if (tag == null) {
                tag = new Tag();
                tag.setName(tagName);
                tagRepository.save(tag);  // Save new tag to database
            }
            managedTags.add(tag);
        }

        clothing.setTags(managedTags);
    }

    private Clothing getClothing(ClothingRequest clothingRequest, byte[] image) {
        Clothing clothing = new Clothing();
        clothing.setCategory(clothingRequest.getCategory());
        clothing.setSubcategory(clothingRequest.getSubcategory());
        clothing.setImage(image);

        Set<Color> colors = new HashSet<>();
        for (String colorName : clothingRequest.getColors()) {
            Color color = colorRepository.findByName(colorName.toLowerCase()).orElse(new Color(colorName.toLowerCase()));
            colors.add(color);
        }
        clothing.setColors(colors);

        Set<Tag> tags = new HashSet<>();
        for (String tagName : clothingRequest.getTags()) {
            Tag tag = new Tag(tagName);
            tags.add(tag);
        }
        clothing.setTags(tags);
        return clothing;
    }
}
