package com.swp.project.controller;

import com.swp.project.dto.response.ApiResponse;
import com.swp.project.dto.response.ChildrenDTO;
import com.swp.project.dto.response.ConsultationRequestDTO;
import com.swp.project.service.IChildrenService;
import com.swp.project.service.IConsultationRequestService;
import com.swp.project.service.IFeedbackService;
import com.swp.project.service.IWorkingScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final IChildrenService childrenService;
    private final IConsultationRequestService consultationRequestService;
    private final IWorkingScheduleService workingScheduleService;
    private final IFeedbackService feedbackService;

    @GetMapping("/children/{parentId}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<List<ChildrenDTO>> getChildrenByParentId(@PathVariable int parentId){
        List<ChildrenDTO> childrenDTOList = childrenService.getChildrenByParentId(parentId);
        return ApiResponse.<List<ChildrenDTO>>builder()
                .message("Children list retrieved")
                .data(childrenDTOList)
                .build();
    }

    @Operation(summary = "This API used for ADMIN assign a doctor to a consultation request has status PENDING")

    @PostMapping("/consultation/assign")

    @SecurityRequirement(name = "bearerAuth")

    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public ApiResponse<ConsultationRequestDTO> assignForDoctor(@RequestParam int consultationRequestId, @RequestParam int doctorId){
        ConsultationRequestDTO consultationRequestDTO = consultationRequestService.assignDoctor(consultationRequestId, doctorId);

        return ApiResponse.<ConsultationRequestDTO>builder()

                .message("Consultation request assigned to doctor")

                .data(consultationRequestDTO)

                .build();

    }


}
