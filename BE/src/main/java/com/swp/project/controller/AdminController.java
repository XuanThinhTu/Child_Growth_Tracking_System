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


}
