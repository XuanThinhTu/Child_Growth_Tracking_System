package com.swp.project.controller;

import com.swp.project.dto.request.WorkScheduleRequest;
import com.swp.project.dto.response.ApiResponse;
import com.swp.project.dto.response.WorkingScheduleDTO;
import com.swp.project.enums.WorkingScheduleStatus;
import com.swp.project.service.IWorkingScheduleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/working-schedule")
@RequiredArgsConstructor
public class WorkingScheduleController {

    private final IWorkingScheduleService workingScheduleService;

    @PostMapping("/register")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<WorkingScheduleDTO> registerWorkingSchedule(@RequestBody List<WorkScheduleRequest> requests) {
        return ApiResponse.<WorkingScheduleDTO>builder()
                .message("Working schedule registered")
                .data(workingScheduleService.registerWorkingSchedule(requests))
                .build();
    }

    @GetMapping("/doctor/{doctorId}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_ADMIN')")
    public ApiResponse<List<WorkingScheduleDTO>> getDoctorSchedulesByStatus(@RequestParam(name = "status", defaultValue = "DRAFT") WorkingScheduleStatus status,
                                                                            @PathVariable int doctorId) {
        return ApiResponse.<List<WorkingScheduleDTO>>builder()
                .message("Working schedules retrieved")
                .data(workingScheduleService.getDoctorSchedulesByStatus(status,doctorId))
                .build();
    }

    @PostMapping("/submit")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ApiResponse<Void> submitWorkingSchedule(@RequestBody List<Integer> scheduleIds) {
        workingScheduleService.submitWorkingSchedule(scheduleIds);
        return ApiResponse.<Void>builder()
                .message("Working schedule submitted")
                .build();
    }

    @PutMapping("/update/{scheduleId}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ApiResponse<WorkingScheduleDTO> updateWorkingSchedule(@PathVariable int scheduleId, @RequestParam int slotTimeId) {
        return ApiResponse.<WorkingScheduleDTO>builder()
                .message("Working schedule updated")
                .data(workingScheduleService.updateWorkingSchedule(scheduleId, slotTimeId))
                .build();
    }

    @GetMapping("/approved-list")
    public ApiResponse<List<WorkingScheduleDTO>> getAllApprovedSchedules(){
        return ApiResponse.<List<WorkingScheduleDTO>>builder()
                .message("Approved schedules retrieved")
                .data(workingScheduleService.getAllApprovedSchedules())
                .build();
    }

    @PostMapping("/un-submit")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ApiResponse<Void> unsubmitWorkingSchedule(@RequestBody List<Integer> scheduleIds) {
        workingScheduleService.unsubmitWorkingSchedule(scheduleIds);
        return ApiResponse.<Void>builder()
                .message("Working schedule unsubmitted")
                .build();
    }
}
