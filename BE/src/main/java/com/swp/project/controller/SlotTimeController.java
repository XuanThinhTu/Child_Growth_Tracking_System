package com.swp.project.controller;

import com.swp.project.dto.request.SlotTimeCreationRequest;
import com.swp.project.dto.response.ApiResponse;
import com.swp.project.dto.response.SlotTimeDTO;
import com.swp.project.service.ISlotTimeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/slot-time")
@RequiredArgsConstructor
public class SlotTimeController {

    private final ISlotTimeService slotTimeService;

    @PostMapping("/add")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<SlotTimeDTO> createSlotTime(@RequestBody SlotTimeCreationRequest request){
        return ApiResponse.<SlotTimeDTO>builder()
                .message("Slot time created")
                .data(slotTimeService.addSlotTime(request))
                .build();
    }

    @GetMapping("/all")
    public ApiResponse<List<SlotTimeDTO>> getAllSlotTimes(){
        return ApiResponse.<List<SlotTimeDTO>>builder()
                .message("All slot times retrieved")
                .data(slotTimeService.getAllSlotTimes())
                .build();
    }

}
