package com.swp.project.mapper;

import com.swp.project.dto.response.WorkingScheduleDTO;
import com.swp.project.entity.WorkingSchedule;
import org.springframework.stereotype.Component;

@Component
public class WorkingScheduleMapper {
    public WorkingScheduleDTO toWorkingScheduleDTO(WorkingSchedule workingSchedule) {
        WorkingScheduleDTO workingScheduleDTO = new WorkingScheduleDTO();
        workingScheduleDTO.setId(workingSchedule.getId());
        workingScheduleDTO.setDate(workingSchedule.getDate().toString());
        workingScheduleDTO.setStatus(workingSchedule.getStatus().toString());
        workingScheduleDTO.setDoctorId(workingSchedule.getDoctor().getId());
        workingScheduleDTO.setDoctorName(workingSchedule.getDoctor().getFirstName() + " " + workingSchedule.getDoctor().getLastName());
        workingScheduleDTO.setStartTime(workingSchedule.getSlotTime().getStartTime());
        workingScheduleDTO.setEndTime(workingSchedule.getSlotTime().getEndTime());
        return workingScheduleDTO;
    }
}
