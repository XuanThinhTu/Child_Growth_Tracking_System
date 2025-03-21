package com.swp.project.controller;

import com.swp.project.dto.request.GrowTrackerRequest;
import com.swp.project.dto.response.ApiResponse;
import com.swp.project.dto.response.GrowthPredictionResponse;
import com.swp.project.service.impl.GrowthTrackerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/grow-tracker")
@RequiredArgsConstructor
public class GrowthTrackerController {

    private final GrowthTrackerService growTrackerService;

    @GetMapping("/{childId}")
    public ResponseEntity<ApiResponse<?>> getAllGrowTrackerData(@PathVariable int childId) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("List of id: " + childId + " tracking data")
                        .data(growTrackerService.showAllGrowthTracker(childId))
                        .build()
        );
    }

    @PostMapping("/{childId}")
    public ResponseEntity<ApiResponse<?>> AddGrowTrackerData(@PathVariable int childId, @RequestBody GrowTrackerRequest growTrackerRequest) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Tracking data added successfully")
                        .data(growTrackerService.addGrowthTracker(childId, growTrackerRequest))
                        .build()
        );
    }

    @PutMapping("/tracker/{id}")
    public ResponseEntity<ApiResponse<?>> updateGrowTrackerData(@PathVariable int id, @RequestBody GrowTrackerRequest growTrackerRequest) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Tracking data with id " + id + " added successfully")
                        .data(growTrackerService.updateGrowthTracker(id, growTrackerRequest))
                        .build()
        );
    }

    @DeleteMapping("/tracker/{id}")
    public ResponseEntity<ApiResponse<?>> deleteGrowTrackerData(@PathVariable int id) {
        growTrackerService.deleteGrowthTracker(id);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Tracking data with id: " + id + " deleted successfully")
                        .build()
        );
    }

    @GetMapping("/{childId}/predict-next")
    public ResponseEntity<ApiResponse<?>> predictNextN(
            @PathVariable int childId,
            @RequestParam(defaultValue = "2") int n // mặc định 2 mốc
    ) {
        List<GrowthPredictionResponse> result = growTrackerService.predictNextNPoints(childId, n);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Predicted " + n + " next points for child " + childId)
                        .data(result)
                        .build()
        );
    }

}
