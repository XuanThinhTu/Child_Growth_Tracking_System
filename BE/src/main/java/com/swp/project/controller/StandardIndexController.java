package com.swp.project.controller;

import com.swp.project.dto.response.ApiResponse;
import com.swp.project.service.Impl.StandardIndexService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/standard-index")
@RequiredArgsConstructor
public class StandardIndexController {

    private final StandardIndexService standardIndexService;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllStandardIndexData() {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("List of standard tracking data")
                        .data(standardIndexService.getALLStandardIndex())
                        .build()
        );
    }

    @GetMapping("/pro")
    public ResponseEntity<ApiResponse<?>> getAllStandardIndexDataStoredProcedure() {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("List of standard tracking data")
                        .data(standardIndexService.getStandardIndexStoredProcedure())
                        .build()
        );
    }
}
