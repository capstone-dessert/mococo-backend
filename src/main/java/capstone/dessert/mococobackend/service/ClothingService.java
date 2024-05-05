package capstone.dessert.mococobackend.service;

import capstone.dessert.mococobackend.entity.Clothing;
import capstone.dessert.mococobackend.entity.Tag;
import capstone.dessert.mococobackend.repository.ClothingRepository;
import capstone.dessert.mococobackend.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ClothingService {

    private final ClothingRepository clothingRepository;
    private final TagRepository tagRepository;

    @Transactional
    public Clothing save(Clothing clothing) {
        // Handle the tags: check if they exist, add new if not
        Set<Tag> managedTags = new HashSet<>();
        for (Tag tag : clothing.getTags()) {
            Tag existingTag = tagRepository.findByName(tag.getName());
            if (existingTag == null) {
                // Tag doesn't exist, save it
                existingTag = tagRepository.save(tag);
            }
            managedTags.add(existingTag);
        }
        clothing.setTags(managedTags);

        // Now save the clothing, which now includes tags
        return clothingRepository.save(clothing);
    }
}
