package capstone.dessert.mococobackend.controller;


import capstone.dessert.mococobackend.request.OutfitCreateRequest;
import capstone.dessert.mococobackend.request.OutfitDateRequest;
import capstone.dessert.mococobackend.request.OutfitUpdateRequest;
import capstone.dessert.mococobackend.response.OutfitResponse;
import capstone.dessert.mococobackend.service.OutfitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/outfit")
public class OutfitController {

    private final OutfitService outfitService;

    @PostMapping(path = "/add", consumes = MULTIPART_FORM_DATA_VALUE)
    public void addOutfit(@ModelAttribute OutfitCreateRequest outfitCreateRequest) {
        outfitService.save(outfitCreateRequest);
    }

    @GetMapping(path = "/image/{id}", produces = IMAGE_JPEG_VALUE)
    public byte[] getOutfitImageById(@PathVariable("id") Long id) {
        return outfitService.getOutfitImageById(id);
    }


    @GetMapping(path = "/all", produces = APPLICATION_JSON_VALUE)
    public List<OutfitResponse> getAllOutfit() {
        return outfitService.getAllOutfit();
    }

    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public OutfitResponse getOutfitById(@PathVariable("id") Long id) {
        return outfitService.getOutfitById(id);
    }

    @GetMapping(path = "/date", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public List<OutfitResponse> getOutfitByDate(@RequestBody OutfitDateRequest outfitDateRequest) {
        return outfitService.getOutfitByDate(outfitDateRequest);
    }

    @PutMapping(path = "/update", consumes = MULTIPART_FORM_DATA_VALUE)
    public void updateOutfit(@ModelAttribute OutfitUpdateRequest outfitUpdateRequest) {
        outfitService.updateOutfit(outfitUpdateRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteOutfit(@PathVariable("id") Long id) {
        outfitService.deleteOutfit(id);
    }

}
