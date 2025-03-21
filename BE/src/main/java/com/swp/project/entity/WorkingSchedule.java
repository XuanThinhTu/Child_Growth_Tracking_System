package com.swp.project.entity;

import com.swp.project.enums.WorkingScheduleStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "working_schedules")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WorkingSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate date;   // ngày làm việc
    @Enumerated(EnumType.STRING)
    private WorkingScheduleStatus status;    // active, off, ...

    // Bác sĩ nào
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;

    // Khung giờ làm việc
    @ManyToOne
    @JoinColumn(name = "slot_time_id")
    private SlotTime slotTime;

}
