package com.swp.project.repository;

import com.swp.project.entity.WorkingSchedule;
import com.swp.project.enums.WorkingScheduleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkingScheduleRepository extends JpaRepository<WorkingSchedule, Integer> {
    List<WorkingSchedule> findByDate(LocalDate date);
    List<WorkingSchedule> findByDateBetween(LocalDate start, LocalDate end);
    List<WorkingSchedule> findByDateAndSlotTimeId(LocalDate date, int slotTimeId);

    Page<WorkingSchedule> findByStatus(WorkingScheduleStatus status, Pageable pageable);

    Page<WorkingSchedule> findByDoctorIdAndStatus(int doctorId, WorkingScheduleStatus status, Pageable pageable);
}
