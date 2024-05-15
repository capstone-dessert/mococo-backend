package capstone.dessert.mococobackend.controller;

import capstone.dessert.mococobackend.entity.Clothing;
import capstone.dessert.mococobackend.entity.Color;
import capstone.dessert.mococobackend.entity.Tag;
import capstone.dessert.mococobackend.exception.ImageUploadException;
import capstone.dessert.mococobackend.request.ClothingRequest;
import capstone.dessert.mococobackend.request.ClothingUpdateRequest;
import capstone.dessert.mococobackend.response.ClothingResponse;
import capstone.dessert.mococobackend.service.ClothingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clothing")
public class ClothingController {

    private final ClothingService clothingService;

    @PostMapping(path = "/add", consumes = MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = CREATED)
    public void addClothing(@ModelAttribute ClothingRequest clothingRequest) {
        Clothing clothing = getClothing(clothingRequest);
        clothingService.save(clothing);
    }

    @GetMapping("/all")
    public List<ClothingResponse> getAllClothing() {
        List<Clothing> clothingList = clothingService.getAllClothing();
        return clothingList.stream()
                .map(ClothingResponse::new)
                .toList();
    }

    @GetMapping("/{id}")
    public ClothingResponse getClothingById(@PathVariable("id") Long id) {
        Clothing clothing = clothingService.getClothingById(id);
        return new ClothingResponse(clothing);
    }

    @GetMapping(path = "/image/{id}", produces = IMAGE_JPEG_VALUE)
    public byte[] getClothingImageById(@PathVariable("id") Long id) {
        return clothingService.getClothingImageById(id);
    }

    @PutMapping(path = "/{id}", consumes = MULTIPART_FORM_DATA_VALUE)
    public void updateClothing(@PathVariable("id") Long id, @ModelAttribute ClothingUpdateRequest request) throws IOException {
        clothingService.updateClothing(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteClothing(@PathVariable("id") Long id) {
        clothingService.deleteClothing(id);
    }

    private Clothing getClothing(ClothingRequest clothingRequest) {
        try {
            Clothing clothing = new Clothing();
            clothing.setCategory(clothingRequest.getCategory());
            clothing.setSubcategory(clothingRequest.getSubcategory());
            clothing.setImage(clothingRequest.getImage().getBytes());

            Set<Color> colors = new HashSet<>();
            for (String colorName : clothingRequest.getColors()) {
                Color color = new Color(colorName.toLowerCase());
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
        } catch (IOException e) {
            throw new ImageUploadException();
        }

    }

}
