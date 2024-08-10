package capstone.dessert.mococobackend.controller;

import capstone.dessert.mococobackend.entity.Clothing;
import capstone.dessert.mococobackend.request.ClothingRequest;
import capstone.dessert.mococobackend.request.ClothingSearchRequest;
import capstone.dessert.mococobackend.request.ClothingUpdateRequest;
import capstone.dessert.mococobackend.response.ClothingResponse;
import capstone.dessert.mococobackend.service.ClothingService;
import capstone.dessert.mococobackend.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clothing")
public class ClothingController {

    private final ClothingService clothingService;

    private final ImageService imageService;

    @PostMapping(path = "/add", consumes = MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = CREATED)
    public void addClothing(@ModelAttribute ClothingRequest clothingRequest) {
        byte[] image = imageService.removeBackground(clothingRequest.getImage());

        clothingService.save(clothingRequest, image);
    }

    @GetMapping(path = "/all", produces = APPLICATION_JSON_VALUE)
    public List<ClothingResponse> getAllClothing() {
        List<Clothing> clothingList = clothingService.getAllClothing();
        return clothingList.stream()
                .map(ClothingResponse::new)
                .toList();
    }

    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ClothingResponse getClothingById(@PathVariable("id") Long id) {
        Clothing clothing = clothingService.getClothingById(id);
        return new ClothingResponse(clothing);
    }

    @GetMapping(path = "/image/{id}", produces = IMAGE_PNG_VALUE)
    public byte[] getClothingImageById(@PathVariable("id") Long id) {
        return clothingService.getClothingImageById(id);
    }

    @PutMapping(path = "/{id}", consumes = MULTIPART_FORM_DATA_VALUE)
    public void updateClothing(@PathVariable("id") Long id, @ModelAttribute ClothingUpdateRequest request) {
        byte[] image;
        if (request.getImage() == null) {
            image = null;
        } else {
            image = imageService.removeBackground(request.getImage());
        }
        clothingService.updateClothing(id, request, image);
    }

    @DeleteMapping("/{id}")
    public void deleteClothing(@PathVariable("id") Long id) {
        clothingService.deleteClothing(id);
    }

    @PostMapping(path = "/search", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public List<ClothingResponse> searchClothing(@RequestBody ClothingSearchRequest criteria) {
        List<Clothing> clothing = clothingService.searchClothing(criteria);

        return clothing.stream()
                .map(ClothingResponse::new)
                .toList();
    }
}
