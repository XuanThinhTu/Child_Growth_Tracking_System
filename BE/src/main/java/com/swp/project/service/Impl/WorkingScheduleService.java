package com.swp.project.service.impl;

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
                        workingSchedule.setStatus(WorkingScheduleStatus.DRAFT);
                        workingSchedule.setDoctor(user);
                        workingScheduleRepository.save(workingSchedule);
                    }
                }
                if(request.getShifts().isAfternoon()){
                    List<SlotTime> slotTimes = slotTimeRepository.findByShifts(SlotTimeShift.AFTERNOON);
                    for(SlotTime slotTime : slotTimes){
                        WorkingSchedule workingSchedule = new WorkingSchedule();
                        workingSchedule.setSlotTime(slotTime);
                        workingSchedule.setDate(LocalDate.parse(request.getDate()));
                        workingSchedule.setStatus(WorkingScheduleStatus.DRAFT);
                        workingSchedule.setDoctor(user);
                        workingScheduleRepository.save(workingSchedule);
                    }
                }
                if(request.getShifts().isEvening()){
                    List<SlotTime> slotTimes = slotTimeRepository.findByShifts(SlotTimeShift.EVENING);
                    for(SlotTime slotTime : slotTimes){
                        WorkingSchedule workingSchedule = new WorkingSchedule();
                        workingSchedule.setSlotTime(slotTime);
                        workingSchedule.setDate(LocalDate.parse(request.getDate()));
                        workingSchedule.setStatus(WorkingScheduleStatus.DRAFT);
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
    public List<WorkingScheduleDTO> getSchedulesByStatus(WorkingScheduleStatus status) {
        List<WorkingSchedule> workingSchedules = workingScheduleRepository.findByStatus(status);
        return workingSchedules.stream().map(workingScheduleMapper::toWorkingScheduleDTO).toList();
    }

    @Override
    public List<WorkingScheduleDTO> getDoctorSchedulesByStatus(WorkingScheduleStatus status,int doctorId) {
        List<WorkingSchedule> doctorWorkingSchedules = workingScheduleRepository.findByDoctorIdAndStatus(doctorId, status);
        return doctorWorkingSchedules.stream().map(workingScheduleMapper::toWorkingScheduleDTO).toList();
    }

    @Override
    public void submitWorkingSchedule(List<Integer> scheduleIds) {
        for (Integer scheduleId : scheduleIds) {
            User user = userService.getAuthenticatedUser();
            WorkingSchedule workingSchedule = workingScheduleRepository.findById(scheduleId)
                    .orElseThrow(() -> new ResourceNotFoundException("Working schedule not found"));
            if (workingSchedule.getDoctor().getId() != user.getId()) {
                throw new RuntimeException("You are not allowed to submit this schedule");
            }else if(!workingSchedule.getStatus().equals(WorkingScheduleStatus.DRAFT)){
                throw new RuntimeException("Schedule " + scheduleId + " is not in draft status");
            }
            workingSchedule.setStatus(WorkingScheduleStatus.SUBMITTED);
            workingScheduleRepository.save(workingSchedule);
        }
    }

    @Override
    public void approveWorkingSchedule(List<Integer> scheduleIds) {
        for(Integer scheduleId : scheduleIds) {
            WorkingSchedule workingSchedule = workingScheduleRepository.findById(scheduleId)
                    .orElseThrow(() -> new ResourceNotFoundException("Working schedule " + scheduleId +" not found"));
            if(!workingSchedule.getStatus().equals(WorkingScheduleStatus.SUBMITTED)){
                throw new RuntimeException("Schedule " + scheduleId + " is not submitted yet");
            }
            workingSchedule.setStatus(WorkingScheduleStatus.APPROVED);
            workingScheduleRepository.save(workingSchedule);
        }
    }

    @Override
    public void rejectWorkingSchedule(List<Integer> scheduleIds) {
        for (Integer scheduleId : scheduleIds) {
            WorkingSchedule workingSchedule = workingScheduleRepository.findById(scheduleId)
                    .orElseThrow(() -> new ResourceNotFoundException("Working schedule " + scheduleId +" not found"));
            if(!workingSchedule.getStatus().equals(WorkingScheduleStatus.SUBMITTED)){
                throw new RuntimeException("Schedule " + scheduleId + " is not submitted yet");
            }
            workingSchedule.setStatus(WorkingScheduleStatus.REJECTED);
            workingScheduleRepository.save(workingSchedule);
        }
    }

    @Override
    public List<WorkingScheduleDTO> getAllApprovedSchedules() {
        List<WorkingSchedule> schedules = workingScheduleRepository.findByStatus(WorkingScheduleStatus.APPROVED);
        return schedules.stream().map(workingScheduleMapper::toWorkingScheduleDTO).toList();
    }

    @Override
    public WorkingScheduleDTO updateWorkingSchedule(int scheduleId, int slotTimeId) {
        User user = userService.getAuthenticatedUser();
        WorkingSchedule workingSchedule = workingScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Working schedule not found"));
        if(workingSchedule.getDoctor().getId() != user.getId()){
            throw new RuntimeException("You are not allowed to update this schedule");
        }
        if(!workingSchedule.getStatus().equals(WorkingScheduleStatus.DRAFT)){
            throw new RuntimeException("Schedule " + scheduleId + " is not in draft status");
        }
        SlotTime slotTime = slotTimeRepository.findById(slotTimeId)
                .orElseThrow(() -> new ResourceNotFoundException("Slot time not found"));
        if(workingScheduleRepository.findByDateAndSlotTimeIdAndDoctorId(workingSchedule.getDate(), slotTime.getId(), user.getId()).isPresent()){
            throw new RuntimeException("Slot time is already taken");
        }
        workingSchedule.setSlotTime(slotTime);
        workingSchedule = workingScheduleRepository.save(workingSchedule);
        return workingScheduleMapper.toWorkingScheduleDTO(workingSchedule);
    }

    @Override
    public void unsubmitWorkingSchedule(List<Integer> scheduleIds) {
        for (Integer scheduleId : scheduleIds) {
            User user = userService.getAuthenticatedUser();
            WorkingSchedule workingSchedule = workingScheduleRepository.findById(scheduleId)
                    .orElseThrow(() -> new ResourceNotFoundException("Working schedule " + scheduleId + " not found"));
            if (workingSchedule.getDoctor().getId() != user.getId()) {
                throw new RuntimeException("You are not allowed to un-submit this schedule");
            }
            if (!workingSchedule.getStatus().equals(WorkingScheduleStatus.SUBMITTED)) {
                throw new RuntimeException("Schedule " + scheduleId + " is not submitted yet");
            }
            workingSchedule.setStatus(WorkingScheduleStatus.DRAFT);
            workingScheduleRepository.save(workingSchedule);
        }
    }
}
