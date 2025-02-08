package com.swp.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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
    private String status;    // active, off, ...

    // Bác sĩ nào
    @ManyToOne
    private User doctor;

    // Khung giờ làm việc
    @ManyToOne
    private SlotTime slotTime;

    // 1 lịch có thể có nhiều booking
    @OneToMany(mappedBy = "workingSchedule")
    private List<Booking> bookings;
}
