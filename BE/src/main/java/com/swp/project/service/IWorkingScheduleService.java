package com.swp.project.service;

import com.swp.project.dto.request.WorkScheduleRequest;
import com.swp.project.dto.response.WorkingScheduleDTO;
import com.swp.project.enums.WorkingScheduleStatus;

import java.util.List;

public interface IWorkingScheduleService {

    WorkingScheduleDTO registerWorkingSchedule(List<WorkScheduleRequest> requests);

    List<WorkingScheduleDTO> getSchedulesByStatus(WorkingScheduleStatus status);

    List<WorkingScheduleDTO> getDoctorSchedulesByStatus(WorkingScheduleStatus status,int doctorId);

    void submitWorkingSchedule(List<Integer> scheduleIds);

    void approveWorkingSchedule(List<Integer> scheduleIds);

    void rejectWorkingSchedule(List<Integer> scheduleIds);

    List<WorkingScheduleDTO> getAllApprovedSchedules();

    WorkingScheduleDTO updateWorkingSchedule(int scheduleId, int slotTimeId);

    void unsubmitWorkingSchedule(List<Integer> scheduleIds);
}
