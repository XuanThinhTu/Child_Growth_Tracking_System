package com.swp.project.repository;

import com.swp.project.entity.SlotTime;
import com.swp.project.enums.SlotTimeShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SlotTimeRepository extends JpaRepository<SlotTime, Integer> {
    Optional<SlotTime> findByStartTimeAndEndTime(LocalTime startTime, LocalTime endTime);

    List<SlotTime> findByShifts(SlotTimeShift shift);
}
