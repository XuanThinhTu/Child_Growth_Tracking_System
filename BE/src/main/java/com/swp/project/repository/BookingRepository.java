package com.swp.project.repository;

import com.swp.project.entity.Booking;
import com.swp.project.entity.SlotTime;
import com.swp.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    boolean existsByDateAndDoctorAndSlotTime(LocalDate date, User doctor, SlotTime slotTime);

    List<Booking> findByMemberOrDoctor(User member, User doctor);

    @Modifying
    @Transactional
    @Query("DELETE FROM Booking b WHERE b.children.id = :childrenId")
    void deleteAllByChildrenId(@Param("childrenId") int childrenId);
}
