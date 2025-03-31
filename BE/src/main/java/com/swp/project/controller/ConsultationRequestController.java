package com.swp.project.controller;

import com.swp.project.dto.request.ConsultationRequestCreation;
import com.swp.project.dto.response.ApiResponse;
import com.swp.project.dto.response.ConsultationRequestDTO;
import com.swp.project.enums.ConsultationStatus;
import com.swp.project.service.IConsultationRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultation")
@RequiredArgsConstructor
public class ConsultationRequestController {

    private final IConsultationRequestService iConsultationRequestService;

    @PostMapping("/request")
    @SecurityRequirement(name = "bearerAuth")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ConsultationRequestDTO> createConsultationRequest(@RequestBody ConsultationRequestCreation request) {
        return ApiResponse.<ConsultationRequestDTO>builder()
                .message("Consultation request created")
                .data(iConsultationRequestService.createConsultationRequest(request))
                .build();
    }

    @GetMapping("/all")
    public ApiResponse<Page<ConsultationRequestDTO>> getAllConsultations(@RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<Page<ConsultationRequestDTO>>builder()
                .message("All consultation request")
                .data(iConsultationRequestService.getAllConsultation(page, size))
                .build();
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<ConsultationRequestDTO> getConsultationRequestById(@PathVariable int id) {
        return ApiResponse.<ConsultationRequestDTO>builder()
                .message("Consultation request")
                .data(iConsultationRequestService.getConsultationRequestById(id))
                .build();
    }

    @GetMapping("/all-request")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<Page<ConsultationRequestDTO>> getAllConsultationRequest(@RequestParam(defaultValue = "0") int page,
                                                                               @RequestParam(defaultValue = "10") int size,
                                                                               @RequestParam("status")ConsultationStatus status) {
        return ApiResponse.<Page<ConsultationRequestDTO>>builder()
                .message("All consultation request")
                .data(iConsultationRequestService.getAllConsultationRequest(page, size, status))
                .build();
    }

    @GetMapping("/pending-request")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_ADMIN')")
    public ApiResponse<Page<ConsultationRequestDTO>> getPendingConsultation(@RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<Page<ConsultationRequestDTO>>builder()
                .message("Pending consultation request")
                .data(iConsultationRequestService.getPendingConsultationRequest(page, size))
                .build();
    }

    @Operation(summary = "Get all consultation request by authenticated user")
    @GetMapping("/my-request")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<List<ConsultationRequestDTO>> getAllConsultationRequestByUser() {
        return ApiResponse.<List<ConsultationRequestDTO>>builder()
                .message("All consultation request")
                .data(iConsultationRequestService.getAllConsultationRequestByUser())
                .build();
    }

    @Operation(summary = "Cancel consultation request by authenticated user")
    @PutMapping("/cancel/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<ConsultationRequestDTO> cancelConsultationRequestByUser(@PathVariable int id) {
        return ApiResponse.<ConsultationRequestDTO>builder()
                .message("Cancel consultation request")
                .data(iConsultationRequestService.cancelConsultationRequestByUser(id))
                .build();
    }

    @Operation(summary = "Close consultation request by authenticated user")
    @PutMapping("/close/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<ConsultationRequestDTO> closeConsultationRequestByUser(@PathVariable int id) {
        return ApiResponse.<ConsultationRequestDTO>builder()
                .message("Close consultation request")
                .data(iConsultationRequestService.closeConsultationRequestByUser(id))
                .build();
    }

}
