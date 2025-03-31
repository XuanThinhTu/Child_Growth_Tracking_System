package com.swp.project.controller;

import com.swp.project.dto.response.ApiResponse;
import com.swp.project.dto.request.ConsultationResponseCreation;
import com.swp.project.dto.response.ConsultationResponseDTO;
import com.swp.project.service.IConsultationResponseService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultation-response")
@RequiredArgsConstructor
public class ConsultationResponseController {

    private final IConsultationResponseService consultationResponseService;

    @PostMapping("/send/{consultationRequestId}")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<ConsultationResponseDTO> sendConsultationResponse(@PathVariable int consultationRequestId,
                                                                         @RequestBody ConsultationResponseCreation response){
        return ApiResponse.<ConsultationResponseDTO>builder()
                .message("Consultation response added")
                .data(consultationResponseService.addConsultationResponse(consultationRequestId, response))
                .build();
    }

    @GetMapping("/get/{consultationRequestId}")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<Page<ConsultationResponseDTO>> getResponsesByConsultationRequestId(@PathVariable int consultationRequestId,
                                                                               @RequestParam(defaultValue = "0") int page,
                                                                               @RequestParam(defaultValue = "10") int size){
        return ApiResponse.<Page<ConsultationResponseDTO>>builder()
                .message("Consultation response added")
                .data(consultationResponseService.getConsultationResponsesByConsultationId(consultationRequestId, page, size))
                .build();
    }
}
