package com.swp.project.controller;

import com.swp.project.dto.response.*;
import com.swp.project.enums.WorkingScheduleStatus;
import com.swp.project.service.IChildrenService;
import com.swp.project.service.IConsultationRequestService;
import com.swp.project.service.IFeedbackService;
import com.swp.project.service.IWorkingScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @Operation(summary = "Get all working schedules by status. Only Admin can access")
    @GetMapping("/working-schedule/list")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<List<WorkingScheduleDTO>>
    getAllSchedulesByStatus(@RequestParam(name = "status", defaultValue = "DRAFT") WorkingScheduleStatus status) {
        return ApiResponse.<List<WorkingScheduleDTO>>builder()
                .message("List working schedule")
                .data(workingScheduleService.getSchedulesByStatus(status))
                .build();
    }

    @Operation(summary = "Approve working schedule. Only Admin can access")
    @PostMapping("/working-schedule/approve")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<Void> approveWorkingSchedule(@RequestBody List<Integer> scheduleIds) {
        workingScheduleService.approveWorkingSchedule(scheduleIds);
        return ApiResponse.<Void>builder()
                .message("Working schedule approved")
                .build();
    }

    @Operation(summary = "Reject working schedule. Only Admin can access")
    @PostMapping("/working-schedule/reject")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<Void> rejectWorkingSchedule(@RequestBody List<Integer> scheduleIds) {
        workingScheduleService.rejectWorkingSchedule(scheduleIds);
        return ApiResponse.<Void>builder()
                .message("Working schedule rejected")
                .build();
    }

    @Operation(summary = "Get all children. Only Admin can access")
    @GetMapping("/children")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<Page<ChildrenDTO>> getAllChildren(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<Page<ChildrenDTO>>builder()
                .message("List children")
                .data(childrenService.getAllChildren(page, size))
                .build();
    }

    @Operation(summary = "Get All Feedback for Admin")
    @GetMapping("/feedbacks")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<?> getAllFeedback(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        return ApiResponse.<Page<FeedbackDTO>>builder()
                .data(feedbackService.getAllFeedbacks(page, size))
                .message("List Feedback")
                .build();
    }

}
