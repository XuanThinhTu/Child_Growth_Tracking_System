package com.swp.project.controller;

import com.swp.project.dto.request.FeedbackRequest;
import com.swp.project.dto.response.ApiResponse;
import com.swp.project.dto.response.FeedbackDTO;
import com.swp.project.service.IFeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedback")
public class FeedbackController {

    private final IFeedbackService feedbackService;

    @Operation(summary = "Create feedback, feedbackType is CONSULTATION or APPOINTMENT, rating 0-5")
    @PostMapping("/send")
    @SecurityRequirement(name = "bearerAuth")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<FeedbackDTO> createFeedback(@RequestBody FeedbackRequest request) {
        return ApiResponse.<FeedbackDTO>builder()
                .message("Feedback created")
                .data(feedbackService.createFeedback(request))
                .build();
    }

    @GetMapping("/doctor/{doctorId}")
    public ApiResponse<?> getDoctorFeedback(@PathVariable int doctorId) {
        return ApiResponse.builder()
                .message("Doctor feedback")
                .data(feedbackService.getAllFeedbacksByDoctorID(doctorId))
                .build();
    }

}
