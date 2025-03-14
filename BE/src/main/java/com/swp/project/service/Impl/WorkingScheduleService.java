package com.swp.project.service.Impl;

import com.swp.project.dto.request.WorkScheduleRequest;
import com.swp.project.dto.response.WorkingScheduleDTO;
import com.swp.project.entity.SlotTime;
import com.swp.project.entity.User;
import com.swp.project.entity.WorkingSchedule;
import com.swp.project.enums.SlotTimeShift;
import com.swp.project.enums.WorkingScheduleStatus;
import com.swp.project.exception.ResourceNotFoundException;
import com.swp.project.mapper.WorkingScheduleMapper;
import com.swp.project.repository.SlotTimeRepository;
import com.swp.project.repository.WorkingScheduleRepository;
import com.swp.project.service.IUserService;
import com.swp.project.service.IWorkingScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkingScheduleService implements IWorkingScheduleService {

    private final WorkingScheduleRepository workingScheduleRepository;
    private final WorkingScheduleMapper workingScheduleMapper;
    private final SlotTimeRepository slotTimeRepository;
    private final IUserService userService;

    @Override
    public WorkingScheduleDTO registerWorkingSchedule(List<WorkScheduleRequest> requests) {
        try {
            for(WorkScheduleRequest request : requests) {
                User user = userService.getAuthenticatedUser();
                if(request.getShifts().isMorning()){
                    List<SlotTime> slotTimes = slotTimeRepository.findByShifts(SlotTimeShift.MORNING);
                    for(SlotTime slotTime : slotTimes){
                        WorkingSchedule workingSchedule = new WorkingSchedule();
                        workingSchedule.setSlotTime(slotTime);
                        workingSchedule.setDate(LocalDate.parse(request.getDate()));
                        workingSchedule.setStatus(String.valueOf(WorkingScheduleStatus.DRAFT));
                        workingSchedule.setDoctor(user);
                        workingScheduleRepository.save(workingSchedule);
                    }
                }else if(request.getShifts().isAfternoon()){
                    List<SlotTime> slotTimes = slotTimeRepository.findByShifts(SlotTimeShift.AFTERNOON);
                    for(SlotTime slotTime : slotTimes){
                        WorkingSchedule workingSchedule = new WorkingSchedule();
                        workingSchedule.setSlotTime(slotTime);
                        workingSchedule.setDate(LocalDate.parse(request.getDate()));
                        workingSchedule.setStatus(String.valueOf(WorkingScheduleStatus.DRAFT));
                        workingSchedule.setDoctor(user);
                        workingScheduleRepository.save(workingSchedule);
                    }
                }else if(request.getShifts().isEvening()){
                    List<SlotTime> slotTimes = slotTimeRepository.findByShifts(SlotTimeShift.EVENING);
                    for(SlotTime slotTime : slotTimes){
                        WorkingSchedule workingSchedule = new WorkingSchedule();
                        workingSchedule.setSlotTime(slotTime);
                        workingSchedule.setDate(LocalDate.parse(request.getDate()));
                        workingSchedule.setStatus(String.valueOf(WorkingScheduleStatus.DRAFT));
                        workingSchedule.setDoctor(user);
                        workingScheduleRepository.save(workingSchedule);
                    }
                }
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<WorkingScheduleDTO> getSchedulesByStatus(WorkingScheduleStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<WorkingSchedule> workingSchedules = workingScheduleRepository.findByStatus(status, pageable);
        return workingSchedules.map(workingScheduleMapper::toWorkingScheduleDTO);
    }

    @Override
    public Page<WorkingScheduleDTO> getDoctorSchedulesByStatus(WorkingScheduleStatus status, int page, int size, int doctorId) {
        Pageable pageable = PageRequest.of(page, size);
        Page<WorkingSchedule> doctorWorkingSchedules = workingScheduleRepository.findByDoctorIdAndStatus(doctorId, status, pageable);
        return doctorWorkingSchedules.map(workingScheduleMapper::toWorkingScheduleDTO);
    }

    @Override
    public void submitWorkingSchedule(List<Integer> scheduleIds) {
        for (int i = 0; i < scheduleIds.size(); i++) {
            User user = userService.getAuthenticatedUser();
            WorkingSchedule workingSchedule = workingScheduleRepository.findById(scheduleIds.get(i))
                    .orElseThrow(() -> new ResourceNotFoundException("Working schedule not found"));
            if(workingSchedule.getDoctor().getId() != user.getId()){
                throw new RuntimeException("You are not allowed to submit this schedule");
            }
            workingSchedule.setStatus(String.valueOf(WorkingScheduleStatus.SUBMITTED));
            workingScheduleRepository.save(workingSchedule);
        }
    }
}
