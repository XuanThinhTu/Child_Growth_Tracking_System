package com.swp.project.repository;

import com.swp.project.entity.WorkingSchedule;
import com.swp.project.enums.WorkingScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkingScheduleRepository extends JpaRepository<WorkingSchedule, Integer> {
    List<WorkingSchedule> findByDate(LocalDate date);
    List<WorkingSchedule> findByDateBetween(LocalDate start, LocalDate end);
    List<WorkingSchedule> findByDateAndSlotTimeId(LocalDate date, int slotTimeId);

    List<WorkingSchedule> findByStatus(WorkingScheduleStatus status);

    List<WorkingSchedule> findByDoctorIdAndStatus(int doctorId, WorkingScheduleStatus status);

    Optional<WorkingSchedule> findByDateAndSlotTimeIdAndDoctorId(LocalDate date, int slotTimeId, int doctorId);
}
