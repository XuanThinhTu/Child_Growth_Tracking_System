package com.swp.project.service;

import com.swp.project.dto.request.WorkScheduleRequest;
import com.swp.project.dto.response.WorkingScheduleDTO;
import com.swp.project.enums.WorkingScheduleStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IWorkingScheduleService {
    WorkingScheduleDTO registerWorkingSchedule(List<WorkScheduleRequest> requests);

    Page<WorkingScheduleDTO> getSchedulesByStatus(WorkingScheduleStatus status, int page, int size);

    Page<WorkingScheduleDTO> getDoctorSchedulesByStatus(WorkingScheduleStatus status, int page, int size, int doctorId);

    void submitWorkingSchedule(List<Integer> scheduleIds);
}
