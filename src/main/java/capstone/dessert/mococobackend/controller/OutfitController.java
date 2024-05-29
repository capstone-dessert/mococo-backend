package capstone.dessert.mococobackend.controller;


import capstone.dessert.mococobackend.request.OutfitCreateRequest;
import capstone.dessert.mococobackend.request.OutfitDateRequest;
import capstone.dessert.mococobackend.request.OutfitUpdateRequest;
import capstone.dessert.mococobackend.response.OutfitResponse;
import capstone.dessert.mococobackend.service.OutfitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/outfit")
public class OutfitController {

    private final OutfitService outfitService;

    @PostMapping("/add")
    public void addOutfit(@RequestBody OutfitCreateRequest outfitCreateRequest) {
        outfitService.save(outfitCreateRequest);
    }

    @GetMapping("/all")
    public List<OutfitResponse> getAllOutfit() {
        return outfitService.getAllOutfit();
    }

    @GetMapping("/{id}")
    public OutfitResponse getOutfitById(@PathVariable("id") Long id) {
        return outfitService.getOutfitById(id);
    }

    @GetMapping("/date")
    public List<OutfitResponse> getOutfitByDate(@RequestBody OutfitDateRequest outfitDateRequest) {
        return outfitService.getOutfitByDate(outfitDateRequest);
    }

    @PutMapping("/update")
    public void updateOutfit(@RequestBody OutfitUpdateRequest outfitUpdateRequest) {
        outfitService.updateOutfit(outfitUpdateRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteOutfit(@PathVariable("id") Long id) {
        outfitService.deleteOutfit(id);
    }

}
